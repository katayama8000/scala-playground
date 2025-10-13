package examples

object BasicScala:
  def run(): Unit =
    println("\n=== Basic Scala Features Demo ===")
    // Either
    val either: Either[String, Int] = Right(42)
    either match
      case Right(value) => println(s"Right value: $value")
      case Left(error)  => println(s"Left error: $error")

    // implicit
    implicit val multiplier: Int = 2
    def multiply(x: Int)(implicit factor: Int): Int = x * factor
    val result = multiply(5)
    println(result)

    // use implicit in class
    class User(val name: String):
      def greet(implicit greeting: String): String = s"$greeting, $name!"

    implicit val v: String = "Hello"
    val user = new User("Scala")
    println(user.greet)

    // useful way to use implicit
    def greetUser(user: User)(implicit greeting: String): String = user.greet

    // mapping
    val numbers = List(1, 2, 3, 4, 5)
    val doubled = numbers.map(_ * 2)

    // for-yield
    val names = List("Alice", "Bob", "Charlie")
    val greetings = for name <- names yield s"Hello, $name!"

    // ↑をmapで書くと
    val greetingsMap = names.map(name => s"Hello, $name!")

    // for-yield with multiple generators
    val pairs = for {
      x <- List(1, 2)
      y <- List("a", "b")
    } yield (x, y)

    // ↑をflatMapで書くと
    val pairsFlatMap = List(1, 2).flatMap(x => List(("a", x), ("b", x)))

    val colors = Map("red" -> 1, "green" -> 2, "blue" -> 3)

    // new DecimalFormat
    import java.text.DecimalFormat
    val format = new DecimalFormat("#.##############")
    val formattedNumber = format.format(0.9149999999999999)
    println(s"Formatted number: $formattedNumber")

    val format2 = new DecimalFormat("#.##")
    val formattedNumber2 = format2.format(0.9149)
    println(s"Formatted number 2: $formattedNumber2")

    val format3 = new DecimalFormat("#.##############")
    val formattedNumber3 = format3.format(0.914999999999)
    println(s"Formatted number 3: $formattedNumber3")

    // 1. データを格納するための箱（ケースクラス）を定義
    case class LoginInfo(username: String, password: String)

    /** Map形式のデータからLoginInfoオブジェクトを作成する関数
      *
      * @param data
      *   ユーザー名とパスワードを含むMap
      * @return
      *   キーが存在すればSome(LoginInfo), 存在しなければNone
      */
    def createLoginInfoFromMap(data: Map[String, String]): Option[LoginInfo] = {

      // 2. Mapからキーを指定して値を取得する
      // .getは値があればSome(値), なければNoneを返す安全な方法
      val usernameOpt = data.get("username")
      val passwordOpt = data.get("password")

      // 3. for-yieldを使って、全ての値が揃っている場合のみLoginInfoを作成する
      // usernameOptとpasswordOptの両方がSomeの場合だけ、yieldブロックが実行される
      for {
        username <- usernameOpt
        password <- passwordOpt
      } yield LoginInfo(username, password)
    }

    // --- 関数の使い方 ---
    val successData = Map("username" -> "suzuki", "password" -> "pass1234")
    val failureData = Map("username" -> "sato") // passwordキーが欠けている

    val successResult = createLoginInfoFromMap(successData)
    val failureResult = createLoginInfoFromMap(failureData)

    println(s"成功例: $successResult") // 出力: 成功例: Some(LoginInfo(suzuki,pass1234))
    println(s"失敗例: $failureResult") // 出力: 失敗例: None

    // private class
    class PrivateClass private (val value: String)

    // class with companion object to create instance
    object PrivateClass:
      def create(value: String): PrivateClass = new PrivateClass(value)
      def apply(value: String): PrivateClass = new PrivateClass(value)

    val privateInstance = PrivateClass.create("Hello, Private!")
    println(privateInstance.value)
    val privateInstance2 = PrivateClass("Hello, Apply!")

    // not private class
    class PublicClass(val value: String)
    val publicInstance = new PublicClass("Hello, Public!")

    val book = Book("Scala Programming")
    val book2 = Book.create("Advanced Scala")

case class BookId(value: Long)
object BookId {
  def generate(): BookId = BookId(scala.util.Random.nextLong().abs % 10000)
}

case class Book(id: BookId, title: String)

object Book {
  def apply(title: String): Book = new Book(BookId.generate(), title)
  def create(title: String): Book = new Book(BookId.generate(), title)
}
