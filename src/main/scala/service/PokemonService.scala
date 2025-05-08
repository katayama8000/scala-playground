package service

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

class PokemonService:
  def fetchPokemonData(num: Int): Future[String] =
    Future {
      val url = s"https://pokeapi.co/api/v2/pokemon/$num"
      val response = Source.fromURL(url).mkString
      response
    }
