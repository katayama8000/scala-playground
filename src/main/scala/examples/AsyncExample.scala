package examples

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._
import scala.concurrent.Await

import service.PokemonService

object AsyncExample:
  def show(): Unit =
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
