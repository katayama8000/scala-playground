package database

import java.sql.{Connection, DriverManager, ResultSet}
import scala.util.{Try, Success, Failure}

/** SQLiteデータベース操作のサンプルクラス
  */
class SQLiteExample {
  // データベースへの接続URL
  private val dbUrl = "jdbc:sqlite:sample.db"
  private var connection: Option[Connection] = None

  /** データベース接続を開く
    */
  def connect(): Try[Connection] = {
    Try {
      // SQLiteドライバを登録
      Class.forName("org.sqlite.JDBC")
      // 接続を作成
      val conn = DriverManager.getConnection(dbUrl)
      connection = Some(conn)
      conn
    }
  }

  /** データベース接続を閉じる
    */
  def disconnect(): Unit = {
    connection.foreach { conn =>
      if (!conn.isClosed) {
        conn.close()
      }
    }
    connection = None
  }

  /** テーブルを作成する
    */
  def createTable(): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val statement = conn.createStatement()
          val sql = """
          CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            age INTEGER,
            email TEXT
          )
        """
          val result = statement.executeUpdate(sql)
          statement.close()
          result
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }

  /** ユーザーデータを挿入する
    */
  def insertUser(name: String, age: Int, email: String): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val sql = "INSERT INTO users (name, age, email) VALUES (?, ?, ?)"
          val preparedStatement = conn.prepareStatement(sql)
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

  /** 全ユーザーデータを取得する
    */
  def getAllUsers(): Try[List[(Int, String, Int, String)]] = {
    connection
      .map { conn =>
        Try {
          val statement = conn.createStatement()
          val sql = "SELECT id, name, age, email FROM users"
          val resultSet = statement.executeQuery(sql)

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

  /** 名前でユーザーを検索する
    */
  def findUsersByName(name: String): Try[List[(Int, String, Int, String)]] = {
    connection
      .map { conn =>
        Try {
          val sql = "SELECT id, name, age, email FROM users WHERE name LIKE ?"
          val preparedStatement = conn.prepareStatement(sql)
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

  /** テーブルのデータを削除する
    */
  def deleteAllUsers(): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val statement = conn.createStatement()
          val result = statement.executeUpdate("DELETE FROM users")
          statement.close()
          result
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }

  /** IDでユーザーを削除する
    */
  def deleteUserById(id: Int): Try[Int] = {
    connection
      .map { conn =>
        Try {
          val sql = "DELETE FROM users WHERE id = ?"
          val preparedStatement = conn.prepareStatement(sql)
          preparedStatement.setInt(1, id)
          val result = preparedStatement.executeUpdate()
          preparedStatement.close()
          result
        }
      }
      .getOrElse(Failure(new Exception("Database connection not established")))
  }
}
