package database

import java.sql.{Connection, DriverManager, ResultSet}
import scala.util.{Try, Success, Failure}

/** Example class for SQLite database operations
  */
class SQLiteExample {
  // Database connection URL
  private val dbUrl = "jdbc:sqlite:sample.db"
  private var connection: Option[Connection] = None

  /** Open database connection
    */
  def connect(): Try[Connection] = {
    Try {
      // Register SQLite driver
      Class.forName("org.sqlite.JDBC")
      // Create connection
      val conn = DriverManager.getConnection(dbUrl)
      connection = Some(conn)
      conn
    }
  }

  /** Close database connection
    */
  def disconnect(): Unit = {
    connection.foreach { conn =>
      if (!conn.isClosed) {
        conn.close()
      }
    }
    connection = None
  }

  /** Create table
    */
  def createTable(): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val statement = conn.createStatement()
          val result = statement.executeUpdate(SQLQueries.CREATE_USERS_TABLE)
          statement.close()
          result
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }

  /** Insert user data
    */
  def insertUser(name: String, age: Int, email: String): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val preparedStatement = conn.prepareStatement(SQLQueries.INSERT_USER)
          preparedStatement.setString(1, name)
          preparedStatement.setInt(2, age)
          preparedStatement.setString(3, email)
          val result = preparedStatement.executeUpdate()
          preparedStatement.close()
          result
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }

  /** Get all users
    */
  def getAllUsers(): Try[List[(Int, String, Int, String)]] = {
    connection
      .map { conn =>
        Try {
          val statement = conn.createStatement()
          val resultSet = statement.executeQuery(SQLQueries.SELECT_ALL_USERS)

          val users =
            scala.collection.mutable.ListBuffer[(Int, String, Int, String)]()
          while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val name = resultSet.getString("name")
            val age = resultSet.getInt("age")
            val email = resultSet.getString("email")
            users += ((id, name, age, email))
          }

          resultSet.close()
          statement.close()
          users.toList
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }

  /** Search users by name
    */
  def findUsersByName(name: String): Try[List[(Int, String, Int, String)]] = {
    connection
      .map { conn =>
        Try {
          val preparedStatement =
            conn.prepareStatement(SQLQueries.FIND_USERS_BY_NAME)
          preparedStatement.setString(1, s"%$name%")
          val resultSet = preparedStatement.executeQuery()

          val users =
            scala.collection.mutable.ListBuffer[(Int, String, Int, String)]()
          while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val userName = resultSet.getString("name")
            val age = resultSet.getInt("age")
            val email = resultSet.getString("email")
            users += ((id, userName, age, email))
          }

          resultSet.close()
          preparedStatement.close()
          users.toList
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }

  /** Delete all data from the table
    */
  def deleteAllUsers(): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val statement = conn.createStatement()
          val result = statement.executeUpdate(SQLQueries.DELETE_ALL_USERS)
          statement.close()
          result
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }

  /** Delete a user by ID
    */
  def deleteUserById(id: Int): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val preparedStatement =
            conn.prepareStatement(SQLQueries.DELETE_USER_BY_ID)
          preparedStatement.setInt(1, id)
          val result = preparedStatement.executeUpdate()
          preparedStatement.close()
          result
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }
}
