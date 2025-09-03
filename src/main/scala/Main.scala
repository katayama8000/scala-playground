import examples.{
  ControlStructuresExample,
  ClassesAndObjectsExample,
  TraitsExample,
  EnumExample,
  AsyncExample,
  FunctionsExample
}

@main def runExample(): Unit =
  println("Hello world!")
  runAllExamples()

def msg = "I was compiled by Scala 3. :)"

// Function to run all examples to keep main method clean
def runAllExamples(): Unit =
  println("\n--- Running Scala Examples ---")

  // // Examples of basic control structures
  // ControlStructuresExample.show()

  // // Examples of classes and objects
  // ClassesAndObjectsExample.show()

  // // Examples of traits (interfaces)
  // TraitsExample.show()

  // // Examples of enums
  // EnumExample.show()

  // // Examples of SQLite database operations
  // DatabaseExample.show()

  // // Examples of asynchronous processing
  // AsyncExample.show()

  // Examples of functions
  //  FunctionsExample.show()
  //  FunctionsExample.composedSafeFunction(1, 1);

  // Basic Scala examples
  //  examples.BasicScala.run()

  // sample DecimalFormat
  examples.SampleDecimalFormat.show()

  println("\n--- All examples completed ---")
