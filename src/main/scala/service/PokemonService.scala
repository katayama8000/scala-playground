package service

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source
import io.circe.parser.parse

class PokemonService:
  def fetchPokemonData(num: Int): Future[String] =
    Future {
      val url = s"https://pokeapi.co/api/v2/pokemon/$num"
      val response = Source.fromURL(url).mkString
      response
    }

  def fetchPokemonName(num: Int): Future[String] =
    Future {
      val url = s"https://pokeapi.co/api/v2/pokemon/$num"
      val response = Source.fromURL(url).mkString
      extractPokemonName(response).getOrElse(s"Unknown Pokemon #$num")
    }

  private def extractPokemonName(jsonString: String): Option[String] = {
    parse(jsonString).toOption.flatMap { json =>
      json.hcursor.downField("name").as[String].toOption
    }
  }
