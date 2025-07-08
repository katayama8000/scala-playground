package examples

object BasicScala:
  def run(): Unit =
    println("\n=== Basic Scala Features Demo ===")
    // Either
    val either: Either[String, Int] = Right(42)
    either match
      case Right(value) => println(s"Right value: $value")
      case Left(error)  => println(s"Left error: $error")
