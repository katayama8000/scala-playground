package examples

import utils.MathUtils

object ControlStructuresExample:
  def show(): Unit =
    println("\n=== Control Structures Demo ===")

    println("- For Loop -")
    for i <- 1 to 5 do println(s"Counter: $i")

    println("\n- If Statement -")
    if (1 > 0) then println("1 is greater than 0")
    else println("1 is not greater than 0")

    println("\n- Match Statement -")
    val x = 1
    x match
      case 1 => println("x is 1")
      case 2 => println("x is 2")
      case _ => println("x is something else")

    println("\n- While Loop -")
    var i = 0
    while (i < 5) do
      println(s"i = $i")
      i += 1

    println("\n- Function Calls -")
    println(s"add1(2, 3) = ${MathUtils.add1(2, 3)}")
    println(s"add2(2) = ${MathUtils.add2(2)}")
    println(s"add3(2, 3, 4, 5) = ${MathUtils.add3(2, 3, 4, 5)}")
    println(s"add4(2, z=10) = ${MathUtils.add4(2, z = 10)}")
