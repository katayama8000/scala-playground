package examples

import model.{Dog, Cat}

object TraitsExample:
  def show(): Unit =
    println("\n=== Traits Demo ===")
    val dog = Dog()
    val cat = Cat()
    println(s"Dog says: ${dog.sound()}")
    println(s"Cat says: ${cat.sound()}")
