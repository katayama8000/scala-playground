package examples

object FunctionsExample {
  // Score function that counts length after removing 'a' characters
  def score(word: String): Int =
    word.replaceAll("a", "").length

  // Sample list of words
  val words = List("apple", "banana", "cherry", "date", "fig", "grape")

  // Sorting words using the score function
  val sortedWords = words.sortBy(score)

  def show(): Unit = {
    println("\n=== Functions Demo ===")
    println("Original words: " + words.mkString(", "))
    println("Sorted words: " + sortedWords.mkString(", "))
    println("Scores: " + words.map(score).mkString(", "))
  }

  // 失敗の可能性がある関数
  // 戻り値をOptionでラップして、失敗を表現
  def safeDivide(x: Int, y: Int): Option[Int] = {
    if (y == 0) None else Some(x / y)
  }

  // Eitherを使った失敗の表現
  def eitherFunction(x: Int, y: Int): Either[String, Int] = {
    if (y == 0) Left("Division by zero error") else Right(x / y)
  }

  // Either と matchを使ったエラーハンドリング
  def handleEitherResult(result: Either[String, Int]): Unit = {
    result match {
      case Left(error)  => println(s"Error: $error")
      case Right(value) => println(s"Result: $value")
    }
  }

  // Tryを使った失敗の表現
  import scala.util.{Try, Success, Failure}
  def tryFunction(x: Int, y: Int): Try[Int] = {
    Try(x / y)
  }

  // successとfailureのパターンマッチング
  def handleTryResult(result: Try[Int]): Unit = {
    result match {
      case Success(value)     => println(s"Success: $value")
      case Failure(exception) => println(s"Failure: ${exception.getMessage}")
    }
  }

  // 戻り値に success と failure を使った関数
  def successFailureFunction(x: Int, y: Int): Try[Int] = {
    if (y == 0) Failure(new ArithmeticException("Division by zero"))
    else Success(x / y)
  }

  // success と failure のパターンマッチング
  def handleSuccessFailureResult(result: Try[Int]): Unit = {
    result match {
      case Success(value)     => println(s"Success: $value")
      case Failure(exception) => println(s"Failure: ${exception.getMessage}")
    }
  }

  // 関数の合成
  def add(x: Int): Int = x + 1
  def multiply(x: Int): Int = x * 2
  def composedFunction(x: Int): Int = {
    multiply(add(x)) // add -> multiply
  }

  // Try を返却する関数を合成する例
  def safeAdd(x: Int, y: Int): Try[Int] = {
    Try(x + y)
  }
  def safeSubtract(x: Int, y: Int): Try[Int] = {
    Try(x - y)
  }
  def safeMultiply(x: Int, y: Int): Try[Int] = {
    print(s"Multiplying $x and $y... ")
    Try(x * y)
  }
  def safeDivideTry(x: Int, y: Int): Try[Int] = {
    if (y == 0) Failure(new ArithmeticException("Division by zero"))
    else Success(x / y)
  }

  def composedSafeFunction(x: Int, y: Int): Try[Int] = {
    for {
      sum <- safeAdd(x, y)
      diff <- safeSubtract(sum, 1)
      product <- safeMultiply(diff, 2)
      result <- safeDivideTry(product, 3)
    } yield result
  }

  type ComposedResult = Try[Int]
  // piplelineを使った関数の合成
  def pipelineFunction(x: Int, y: Int): ComposedResult = {
    safeAdd(x, y)
      .flatMap(sum => safeSubtract(sum, 1))
      .flatMap(diff => safeMultiply(diff, 2))
      .flatMap(product => safeDivideTry(product, 3))
  }

  type UnpaidInvoice = String
  type Payment = Double
  type PaidInvoice = String
  type PaymentError = String
  type PayInvoice =
    UnpaidInvoice => Payment => Either[PaymentError, PaidInvoice]

  def payInvoice(
      invoice: UnpaidInvoice,
      payment: Payment
  ): Either[PaymentError, PaidInvoice] = {
    if (payment > 0) Right(s"Paid $invoice with $payment")
    else Left("Payment must be greater than zero")
  }
  def payInvoiceFunction: PayInvoice = { invoice => payment =>
    payInvoice(invoice, payment)
  }
  def showPayInvoiceExample(): Unit = {
    val unpaidInvoice: UnpaidInvoice = "INV-12345"
    val payment: Payment = 100.0

    val result: Either[PaymentError, PaidInvoice] =
      payInvoiceFunction(unpaidInvoice)(payment)

    result match {
      case Right(paidInvoice) => println(s"Success: $paidInvoice")
      case Left(error)        => println(s"Error: $error")
    }
  }

}
