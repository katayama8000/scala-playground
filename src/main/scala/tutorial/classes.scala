package tutorial

object Classes {
  def main(args: Array[String]): Unit = {
    val point = new Point(1, 2)
    println(s"Original Point: $point")
    val movedPoint = point.move(3, 4)
    println(s"Moved Point: $movedPoint")
    // toString method is automatically called when printing
    val point2 = new Point(5, 6)
    val str = point2.toString
    println(s"Point2 as String: $str")

    // Using getter and setter
    val person = new Person("Alice", 30)
    println(s"Original Person: $person")
    person.name = "Bob"
    person.age = 25
    println(s"Updated Person: $person")
    try {
      person.age = -5 // This will throw an exception
    } catch {
      case e: IllegalArgumentException => println(s"Error: ${e.getMessage}")
    }
  }

  class Point(val x: Int, val y: Int) {
    def move(dx: Int, dy: Int): Point = new Point(x + dx, y + dy)
    override def toString: String = s"Point($x, $y)"
  }

  // getter and setter
  class Person(private var _name: String, private var _age: Int) {
    def name: String = _name
    def name_=(newName: String): Unit = {
      _name = newName
    }

    def age: Int = _age
    def age_=(newAge: Int): Unit = {
      if (newAge >= 0) _age = newAge
      else throw new IllegalArgumentException("Age cannot be negative")
    }

    override def toString: String = s"Person(name=$name, age=$age)"
  }

}
