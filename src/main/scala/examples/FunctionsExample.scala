package examples

object FunctionsExample {
  // Score function that counts length after removing 'a' characters
  def score(word: String): Int =
    word.replaceAll("a", "").length

  // Sample list of words
  val words = List("apple", "banana", "cherry", "date", "fig", "grape")

  // Sorting words using the score function
  val sortedWords = words.sortBy(score)

  def show(): Unit = {
    println("\n=== Functions Demo ===")
    println("Original words: " + words.mkString(", "))
    println("Sorted words: " + sortedWords.mkString(", "))
    println("Scores: " + words.map(score).mkString(", "))
  }
}
