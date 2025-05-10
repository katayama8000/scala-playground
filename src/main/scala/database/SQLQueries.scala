package database

/** Contains all SQL queries used in the application
  */
object SQLQueries {
  // Table creation
  val CREATE_USERS_TABLE = """
    CREATE TABLE IF NOT EXISTS users (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT NOT NULL,
      age INTEGER,
      email TEXT
    )
  """

  // User operations
  val INSERT_USER = "INSERT INTO users (name, age, email) VALUES (?, ?, ?)"
  val SELECT_ALL_USERS = "SELECT id, name, age, email FROM users"
  val FIND_USERS_BY_NAME =
    "SELECT id, name, age, email FROM users WHERE name LIKE ?"
  val DELETE_ALL_USERS = "DELETE FROM users"
  val DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?"
}
