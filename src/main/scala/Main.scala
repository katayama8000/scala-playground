import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._
import scala.concurrent.Await

import model.{Person, Dog, Cat, Color, Singleton}
import utils.MathUtils
import service.PokemonService

@main def hello(): Unit =
  println("Hello world!")

  // 基本制御構造の例
  showControlStructures()

  // クラスとオブジェクトの例
  showClassesAndObjects()

  // トレイト（インターフェース）の例
  showTraitsExample()

  // 列挙型の例
  showEnumExample()

  // 非同期処理の例
  fetchPokemonExample()

def msg = "I was compiled by Scala 3. :)"

// 基本制御構造の例
def showControlStructures(): Unit =
  println("\n=== 制御構造のデモ ===")

  println("- for loop -")
  for i <- 1 to 5 do println(s"Counter: $i")

  println("\n- if statement -")
  if (1 > 0) then println("1 is greater than 0")
  else println("1 is not greater than 0")

  println("\n- match statement -")
  val x = 1
  x match
    case 1 => println("x is 1")
    case 2 => println("x is 2")
    case _ => println("x is something else")

  println("\n- while loop -")
  var i = 0
  while (i < 5) do
    println(s"i = $i")
    i += 1

  println("\n- 関数呼び出し -")
  println(s"add1(2, 3) = ${MathUtils.add1(2, 3)}")
  println(s"add2(2) = ${MathUtils.add2(2)}")
  println(s"add3(2, 3, 4, 5) = ${MathUtils.add3(2, 3, 4, 5)}")
  println(s"add4(2, z=10) = ${MathUtils.add4(2, z = 10)}")

// クラスとオブジェクトの例
def showClassesAndObjects(): Unit =
  println("\n=== クラスとオブジェクトのデモ ===")
  val person = Person("John", 30)
  person.greet()

  println("\n- Singleton object -")
  Singleton.greet()

// トレイト（インターフェース）の例
def showTraitsExample(): Unit =
  println("\n=== トレイトのデモ ===")
  val dog = Dog()
  val cat = Cat()
  println(s"Dog says: ${dog.sound()}")
  println(s"Cat says: ${cat.sound()}")

// 列挙型の例
def showEnumExample(): Unit =
  println("\n=== 列挙型のデモ ===")
  val color: Color = Color.Red
  println(s"Selected color: $color")

  color match
    case Color.Red   => println("赤色が選択されました")
    case Color.Green => println("緑色が選択されました")
    case Color.Blue  => println("青色が選択されました")

// 非同期処理の例
def fetchPokemonExample(): Unit =
  println("\n=== 非同期処理のデモ ===")
  println("ポケモンAPIからデータをフェッチします...")

  val pokemonService = PokemonService()
  val futureData = pokemonService.fetchPokemonData(1)

  println("Future オブジェクト: " + futureData)

  println("Await.result を使用してデータを待機します...")
  try {
    val result = Await.result(futureData, 5.seconds)
    println("--- 取得したデータ (短縮表示) ---")
    // 結果が長すぎるため、一部だけ表示
    println(result.take(500) + "...")
  } catch {
    case e: Exception =>
      println(s"エラーが発生しました: ${e.getMessage}")
  }
  println("処理完了!")
