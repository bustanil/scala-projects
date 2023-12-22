package com.rockthejvm.part3fp

object TuplesMapsExercises {

  /**
   * Social network = Map[String, Set[String]]
   * Friend relationships are MUTUAL.
   *
   * - add a person to the network
   * - remove a person from the network
   * - add friend relationship
   * - unfriend
   *
   * - number of friends of a person
   * - person who has the most friends
   * - how many friend has no friends
   * - if there is a social connection between 2 people (direct or not)
   */

  def addPerson(network: Map[String, Set[String]], newPerson: String): Map[String, Set[String]] = {
    network + (newPerson -> Set())
  }

  def removePerson(network: Map[String, Set[String]], toRemove: String): Map[String, Set[String]] = {
    val friendsRemoved = network.view.mapValues(relationship => relationship - toRemove).toMap
    friendsRemoved - toRemove
  }

  def unfriend(network: Map[String, Set[String]], person: String, friend: String): Map[String, Set[String]] = {
    network
      .updated(person, network.getOrElse(person, Set()) - friend)
      .updated(friend, network.getOrElse(friend, Set()) - person)
  }

  def addFriend(network: Map[String, Set[String]], person: String, friend: String): Map[String, Set[String]] = {
    network
      .updated(person, network.getOrElse(person, Set()) + friend)
      .updated(friend, network.getOrElse(friend, Set()) + person)
  }

  def numOfFriends(network: Map[String, Set[String]], person: String): Int = {
    network.getOrElse(person, Set()).size
  }

  def hasMostFriends(network: Map[String, Set[String]]): String = {
    network.iterator.maxBy((_, relationship) => relationship.size)._1
  }

  def hasNoFriends(network: Map[String, Set[String]]): Int = {
    network.view.count((_, relationship) => relationship.isEmpty)
  }

  def main(args: Array[String]): Unit = {
    val network: Map[String, Set[String]] = Map()

    val updated = addPerson(network, "John")
    val updated2 = addPerson(updated, "Mary")
    val updated3 = addFriend(updated2, "Mary", "John")
    val updated4 = addPerson(updated3, "Bob")
    val updated5 = addFriend(updated4, "Bob", "Mary")
    val updated6 = unfriend(updated5, "John", "Mary")

    println(updated6)

    println(numOfFriends(updated5, "Mary"))
    println(hasMostFriends(updated5))
    println(hasNoFriends(updated5))
    println(hasNoFriends(updated6))

  }

}
