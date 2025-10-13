package tutorial

object Basics {

  def main(args: Array[String]): Unit = {
    println("Hello from Basics!")
    println(addThenMultiply(2, 3)(4)) // Example usage of addThenMultiply
    val greeter = new Greeter("Hello", "!")
    println(greeter.greet("World")) // Example usage of Greeter
    val point = Point(1, 3)
    println(s"Point coordinates: (${point.x}, ${point.y})") // Example usage
  }

  def addThenMultiply(x: Int, y: Int)(multiplier: Int): Int = {
    val sum = x + y
    sum * multiplier
  }

  class Greeter(prefix: String, suffix: String) {
    def greet(name: String): String = s"$prefix, $name$suffix"
  }

  case class Point(x: Int, y: Int)

  object IdFactory {
    private var currentId = 0
    def nextId(): Int = {
      currentId += 1
      currentId
    }
  }

  trait GreeterTrait {
    def greet(name: String): String
  }

  class SimpleGreeter extends GreeterTrait {
    def greet(name: String): String = s"Hello, $name!"
  }
}
