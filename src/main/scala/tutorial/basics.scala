package tutorial

object Basics {

  def main(args: Array[String]): Unit = {
    println("Hello from Basics!")
    println(addThenMultiply(2, 3)(4)) // Example usage of addThenMultiply
    val greeter = new Greeter("Hello", "!")
    println(greeter.greet("World")) // Example usage of Greeter
    val point = Point(1, 3)
    println(s"Point coordinates: (${point.x}, ${point.y})") // Example usage

    // sample of for comprehension with async operations
    import scala.concurrent.{Future, Await}
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._
    def asyncAdd(x: Int, y: Int): Future[Int] = Future {
      x + y
    }
    val futureResult: Future[Int] = for {
      a <- asyncAdd(1, 2)
      b <- asyncAdd(3, 4)
    } yield a + b
    val result: Int = Await.result(futureResult, 1.second)
    println(s"Async result: $result") // Outputs "Async result: 10"

    def asyncAddReturnList(x: Int, y: Int): Future[List[Int]] = Future {
      List(x + y)
    }
    val futureListResult: Future[List[Int]] = for {
      a <- asyncAddReturnList(1, 2)
      b <- asyncAddReturnList(3, 4)
    } yield a ++ b

    val listResult: List[Int] = Await.result(futureListResult, 1.second)
    println(s"Async list result: $listResult") // Outputs "Async list result:
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

  // sample of fold method
  def sumList(numbers: List[Int]): Int = numbers.sum
  println(sumList(List(1, 2, 3, 4, 5))) // Outputs 15

  // another sample of fold method
  def concatenateStrings(strings: List[String]): String =
    strings.fold("")(_ + _)
  println(
    concatenateStrings(List("Hello, ", "world", "!"))
  ) // Outputs "Hello, world!"

  // sample of for yield
  def squareList(numbers: List[Int]): List[Int] =
    for (n <- numbers) yield n * n
  println(squareList(List(1, 2, 3, 4, 5))) // Outputs List(1, 4, 9, 16, 25

  def add(x: Int, y: Int): Int = {
    x + y
  }

  private val list1 = List(1, 2, 3)
  private val list2 = List(4, 5, 6)

  private val combined = for {
    a <- list1
    b <- list2
  } yield (a, b)

  println(
    combined
  ) // Outputs List((1,4), (1,5), (1,6), (2,4), (2,5), (2,6), (3,4), (3,5), (3,6))

  val sum: Seq[Int] = for {
    a <- list1
    b <- list2
  } yield add(a, b)

  println(sum) // Outputs List(5, 6, 7, 6, 7, 8, 7, 8, 9)

  // another sample of for yield with filtering
  def filterAndSquare(numbers: List[Int]): List[Int] = {
    for { n <- numbers if n % 2 == 0 } yield n * n
  }

  private val filteredSquared: List[Int] = filterAndSquare(List(1, 2, 3, 4, 5))
  println(filteredSquared) // Outputs List(4, 16)

}
