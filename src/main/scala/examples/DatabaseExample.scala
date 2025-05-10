package examples

import scala.util.{Success, Failure}
import database.SQLiteExample

object DatabaseExample:
  def show(): Unit =
    println("\n=== SQLite Database Demo ===")
    val db = new SQLiteExample()

    // Connect to the database
    println("Connecting to SQLite database...")
    db.connect() match
      case Success(conn) => println("Connection established successfully!")
      case Failure(e) =>
        println(s"Failed to connect to database: ${e.getMessage}")
        return

    // Create the users table
    println("\nCreating users table...")
    db.createTable() match
      case Success(_) => println("Table created successfully!")
      case Failure(e) => println(s"Failed to create table: ${e.getMessage}")

    // Insert some sample users
    println("\nInserting sample users...")
    val users = List(
      ("Alice", 28, "alice@example.com"),
      ("Bob", 35, "bob@example.com"),
      ("Charlie", 22, "charlie@example.com"),
      ("Diana", 31, "diana@example.com")
    )

    users.foreach { case (name, age, email) =>
      db.insertUser(name, age, email) match
        case Success(_) => println(s"Inserted user: $name")
        case Failure(e) => println(s"Failed to insert $name: ${e.getMessage}")
    }

    // Query all users
    println("\nQuerying all users...")
    db.getAllUsers() match
      case Success(users) =>
        println("Users in database:")
        users.foreach { case (id, name, age, email) =>
          println(s"ID: $id, Name: $name, Age: $age, Email: $email")
        }
      case Failure(e) => println(s"Failed to query users: ${e.getMessage}")

    // Search for users
    val searchName = "a"
    println(s"\nSearching for users with name containing '$searchName'...")
    db.findUsersByName(searchName) match
      case Success(users) =>
        println(s"Users with name containing '$searchName':")
        users.foreach { case (id, name, age, email) =>
          println(s"ID: $id, Name: $name, Age: $age, Email: $email")
        }
      case Failure(e) => println(s"Failed to search users: ${e.getMessage}")

    // Clean up: Disconnect from the database
    println("\nDisconnecting from database...")
    db.disconnect()
    println("Database connection closed.")
