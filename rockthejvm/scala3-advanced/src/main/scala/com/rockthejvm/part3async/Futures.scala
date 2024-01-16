package com.rockthejvm.part3async

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Random, Success, Try}

object Futures {

  def calculateMeaningOfLife(): Int = {
    // simulate long computation
    Thread.sleep(1000)
    42
  }

  // thread pool (Java-specific)
  val executor = Executors.newFixedThreadPool(4)
  // thread pool (Scala-specific)
  given executionContext: ExecutionContext = ExecutionContext.fromExecutorService(executor)

  // a future = an async computation that will finish at some point in the future
  val aFuture: Future[Int] = Future.apply(calculateMeaningOfLife()) // given executionContext will be passed here

  // Option[Try[Int]], because
  // - we don't know if we have a value
  // - if we do, that can be a failed computation
  val futureInstantResult: Option[Try[Int]] = aFuture.value

  // callbacks
  aFuture.onComplete {
    case Success(value) => println(s"I have completed with the meaning of life: $value")
    case Failure(e) => println(s"My async computation failed: $e")
  } // on some other threads

  /*
    Function composition
   */
  case class Profile(id: String, name: String) {
    def sendMessage(anotherProfile: Profile, message: String): Unit =
      println(s"${this.name} sending message to ${anotherProfile.name}: $message")
  }

  object SocialNetwork {
    // "database"
    val names = Map(
      "rtjvm.id.1-daniel" -> "Daniel",
      "rtjvm.id.2-jane" -> "Jane",
      "rtjvm.id.3-mark" -> "Mark",
    )

    val friends = Map(
      "rtjvm.id.2-jane" -> "rtjvm.id.3-mark"
    )

    val random = new Random()

    // "API"
    def fetchProfile(id: String): Future[Profile] = Future {
      // fetch something from the database
      Thread.sleep(random.nextInt(300)) //simulate the time delay
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bestFriendId = friends(profile.id)
      Profile(bestFriendId, names(bestFriendId))
    }

  }

  // problem: sending a message to my best friend
  def sendMessageToBestFriend(accountId: String, message: String): Unit = {
    // call fetchProfile
    // call fetchBestFriend
    // call profile.sendMessage(bestFriend)

    val profileFuture = SocialNetwork.fetchProfile(accountId)
    profileFuture.onComplete {
      case Success(profile) =>
        val friendProfileFuture = SocialNetwork.fetchBestFriend(profile)
        friendProfileFuture.onComplete {
          case Success(friendProfile) => profile.sendMessage(friendProfile, message)
          case Failure(ex) => ex.printStackTrace()
        }
      case Failure(ex) => ex.printStackTrace()
    }
  }

  // onComplete is a hassle.
  // solution: function composition
  def sendMessageToBestFriend_v2(accountId: String, message: String): Unit = {
    val profileFuture = SocialNetwork.fetchProfile(accountId)
    profileFuture.flatMap { profile =>
      SocialNetwork.fetchBestFriend(profile).map { bestFriend =>
        profile.sendMessage(bestFriend, message)
      }
    }
  }

  def sendMessageToBestFriend_v3(accountId: String, message: String): Unit = {
    for {
      profile <- SocialNetwork.fetchProfile(accountId)
      bestFriend <- SocialNetwork.fetchBestFriend(profile)
    } yield profile.sendMessage(bestFriend, message) // identical to v2
  }

  val janeProfileFuture = SocialNetwork.fetchProfile("rtjvm.id.2-jane")
  val janeFuture = janeProfileFuture.map(profile => profile.name) // map transforms value contained inside, asynchronously
  val janesBestFriend: Future[Profile] = janeProfileFuture.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
  val janesBestFriendFilter: Future[Profile] = janesBestFriend.filter(profile => profile.name.startsWith("Z"))

  // fallbacks
  val profileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("rtjvm.id.0-dummy", "Forever alone")
  }

  // exception from second fetchProfile
  val aFetchedProfileNoMatterWhat: Future[Profile] = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("rtjvm.id.0-dummy")
  }

  // exception from first fetchProfile
  val fallbackProfile: Future[Profile] = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("rtjvm.id.0-dummy"))

  def main(args: Array[String]): Unit = {
//    println(aFuture.value) // inspect the value of the future RIGHT NOW! you may or may not get the value
    sendMessageToBestFriend_v3("rtjvm.id.2-jane", "Hello")
    Thread.sleep(2000)
    executor.shutdown()
  }

}
