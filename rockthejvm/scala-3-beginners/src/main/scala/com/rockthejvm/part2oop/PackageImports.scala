package com.rockthejvm.part2oop


// top level variables and methods
// Not recommended
val meaningOfLife = 42
def computeMyLife: String = "Scala"


object PackageImports { // top level definitions

  // packages = "folders"
  //fully qualified name
  val aList: com.rockthejvm.practice.LList[Int] = ???

  import com.rockthejvm.practice.LList
  val anotherList: LList[Int] = ???

  // importing under an alias
  import java.util.{List => JList} // Scala 2 style
  import java.util.{List as JList} // Scala 3

  // import everything
  import com.rockthejvm.practice.*
  val aPredicate: Predicate[Int] = ???

  // import multiple symbols
  import PhysicsConstants.{SOMETHING, SPEED_OF_LIGHT}
  val a = SOMETHING
  val b = SPEED_OF_LIGHT

  // import everything but
  object PlayingPhysics {
    import PhysicsConstants.{SPEED_OF_LIGHT as _, *}
    val p = SPEED_OF_LIGHT
  }

  import com.rockthejvm.part2oop.*
  val test = meaningOfLife

  // default imports (automatically imported)
  // scala.*, scala.Predef.*, default java packages such as java.lang.*

  // exports
  class PhysicsCalculator {
    import PhysicsConstants.*
    def computePhotonEnergy(wavelength: Double): Double =
      SPEED_OF_LIGHT / wavelength
  }

  object ScienceApp {
    val physicsCalculator = PhysicsCalculator()
    export physicsCalculator.computePhotonEnergy //

    def computeEquivalentMass(wavelength: Double): Double =
      computePhotonEnergy(wavelength) / (1 + 2)
  }

  def main(args: Array[String]): Unit = {

  }

}

object PhysicsConstants {
  // constants
  val SPEED_OF_LIGHT = 12341234
  val SOMETHING = 12341234
  val ANOTHER_THING = true
}