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

  // Examples of basic control structures
  showControlStructures()

  // Examples of classes and objects
  showClassesAndObjects()

  // Examples of traits (interfaces)
  showTraitsExample()

  // Examples of enums
  showEnumExample()

  // Examples of asynchronous processing
  fetchPokemonExample()

def msg = "I was compiled by Scala 3. :)"

// Examples of basic control structures
def showControlStructures(): Unit =
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

// Examples of classes and objects
def showClassesAndObjects(): Unit =
  println("\n=== Classes and Objects Demo ===")
  val person = Person("John", 30)
  person.greet()

  println("\n- Singleton Object -")
  Singleton.greet()

// Examples of traits (interfaces)
def showTraitsExample(): Unit =
  println("\n=== Traits Demo ===")
  val dog = Dog()
  val cat = Cat()
  println(s"Dog says: ${dog.sound()}")
  println(s"Cat says: ${cat.sound()}")

// Examples of enums
def showEnumExample(): Unit =
  println("\n=== Enum Demo ===")
  val color: Color = Color.Red
  println(s"Selected color: $color")

  color match
    case Color.Red   => println("Red color was selected")
    case Color.Green => println("Green color was selected")
    case Color.Blue  => println("Blue color was selected")

// Examples of asynchronous processing
def fetchPokemonExample(): Unit =
  println("\n=== Asynchronous Processing Demo ===")
  println("Fetching data from Pokemon API...")

  val pokemonService = PokemonService()
  val pokemonId = 25 // Using Pikachu (ID: 25) as an example

  // Fetch full Pokemon data
  val futureData = pokemonService.fetchPokemonData(pokemonId)
  println("Future object for full data: " + futureData)

  // Fetch just the Pokemon name
  val futureName = pokemonService.fetchPokemonName(pokemonId)
  println("Future object for name: " + futureName)

  println("Waiting for Pokemon name...")
  try {
    val pokemonName = Await.result(futureName, 5.seconds)
    println(s"Pokemon #$pokemonId name is: $pokemonName")
  } catch {
    case e: Exception =>
      println(s"Error fetching Pokemon name: ${e.getMessage}")
  }

  println("Waiting for full Pokemon data...")
  try {
    val result = Await.result(futureData, 5.seconds)
    println("--- Retrieved Data (truncated) ---")
    // Only showing part of the result as it's too long
    println(result.take(500) + "...")
  } catch {
    case e: Exception =>
      println(s"An error occurred: ${e.getMessage}")
  }
  println("Processing complete!")
