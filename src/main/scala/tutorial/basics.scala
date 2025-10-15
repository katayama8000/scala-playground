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

  // sample of mixing in traits
  trait Logger {
    def log(message: String): Unit = println(s"LOG: $message")
  }

  trait databaseService {
    def save(data: String): Unit = println(s"Saving data: $data")
  }

  class UserService extends databaseService with Logger {
    def createUser(name: String): Unit = {
      log(s"Creating user: $name")
      save(name)
    }
  }

  val service = new UserService()
  service.createUser("Alice")

  // sample of implicits
  implicit class IntOps(x: Int) {
    def isEven: Boolean = x % 2 == 0
    def isOdd: Boolean = x % 2 != 0
  }

  private def checkEvenOdd(n: Int): Unit = {
    if (n.isEven) println(s"$n is even")
    else println(s"$n is odd")
  }

  checkEvenOdd(3)

  // sample of implicit with functions
  implicit val defaultMultiplier: Int = 2
  def multiplyWithDefault(x: Int)(implicit multiplier: Int): Int =
    x * multiplier
  println(multiplyWithDefault(5)) // Uses implicit multiplier

}
