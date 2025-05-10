package examples

import model.{Person, Singleton}

object ClassesAndObjectsExample:
  def show(): Unit =
    println("\n=== Classes and Objects Demo ===")
    val person = Person("John", 30)
    person.greet()

    println("\n- Singleton Object -")
    Singleton.greet()
