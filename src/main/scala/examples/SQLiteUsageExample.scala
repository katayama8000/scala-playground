package examples

import database.SQLiteExample
import scala.util.{Success, Failure}

/** This example demonstrates the usage of the SQLiteExample class with SQL
  * queries extracted to a separate file
  */
object SQLiteUsageExample {
  def main(args: Array[String]): Unit = {
    val dbExample = new SQLiteExample()

    println("Connecting to database...")
    dbExample.connect() match {
      case Success(_) => println("Connection successful")
      case Failure(e) =>
        println(s"Failed to connect: ${e.getMessage}")
        return
    }

    try {
      // Create users table
      dbExample.createTable() match {
        case Success(_) => println("Table created successfully")
        case Failure(e) => println(s"Failed to create table: ${e.getMessage}")
      }

      // Clear existing data
      dbExample.deleteAllUsers()

      // Insert sample users
      println("\nInserting users...")
      dbExample.insertUser("John Doe", 30, "john@example.com")
      dbExample.insertUser("Jane Smith", 25, "jane@example.com")
      dbExample.insertUser("Bob Johnson", 40, "bob@example.com")

      // Get all users
      println("\nAll users:")
      dbExample.getAllUsers() match {
        case Success(users) =>
          users.foreach { case (id, name, age, email) =>
            println(s"ID: $id, Name: $name, Age: $age, Email: $email")
          }
        case Failure(e) => println(s"Failed to get users: ${e.getMessage}")
      }

      // Search for users by name
      println("\nSearching for users with 'Jo' in name:")
      dbExample.findUsersByName("Jo") match {
        case Success(users) =>
          users.foreach { case (id, name, age, email) =>
            println(s"ID: $id, Name: $name, Age: $age, Email: $email")
          }
        case Failure(e) => println(s"Search failed: ${e.getMessage}")
      }

      // Delete a user
      println("\nDeleting user with ID 1...")
      dbExample.deleteUserById(1) match {
        case Success(count) => println(s"Deleted $count users")
        case Failure(e)     => println(s"Delete failed: ${e.getMessage}")
      }

      // Show remaining users
      println("\nRemaining users:")
      dbExample.getAllUsers() match {
        case Success(users) =>
          users.foreach { case (id, name, age, email) =>
            println(s"ID: $id, Name: $name, Age: $age, Email: $email")
          }
        case Failure(e) => println(s"Failed to get users: ${e.getMessage}")
      }

    } finally {
      // Always close the connection
      dbExample.disconnect()
      println("\nDatabase connection closed")
    }
  }
}
