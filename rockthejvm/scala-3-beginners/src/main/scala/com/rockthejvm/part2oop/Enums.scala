package com.rockthejvm.part2oop

object Enums {

  enum Permissions {
    case READ, WRITE, EXECUTE, NONE

    def openDocument(): Unit = println(s"$ordinal")
  }

  val somePermissions: Permissions = Permissions.READ

  // constructor args
  enum PermissionsWithBits(bits: Int){
    case READ extends PermissionsWithBits(3)
    case WRITE extends PermissionsWithBits(4)
    case EXECUTE extends PermissionsWithBits(5)
  }

  object PermissionsWithBits {
    def fromBits(bits: Int) = PermissionsWithBits.EXECUTE
  }

  // standard API
  val theOrdinal = somePermissions.ordinal
  val theValues = PermissionsWithBits.values
  val readPermision: Permissions = Permissions.valueOf("READ")

  def main(args: Array[String]): Unit = {
    somePermissions.openDocument()
  }

}
