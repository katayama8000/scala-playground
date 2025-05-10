package examples

import model.Color

object EnumExample:
  def show(): Unit =
    println("\n=== Enum Demo ===")
    val color: Color = Color.Red
    println(s"Selected color: $color")

    color match
      case Color.Red   => println("Red color was selected")
      case Color.Green => println("Green color was selected")
      case Color.Blue  => println("Blue color was selected")
