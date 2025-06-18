package examples

import munit.FunSuite

class DIvsNoDIComparisonTest extends FunSuite {

  // テスト用のモックサービス
  class MockService(greeting: String) extends Service {
    override def greet(name: String): String = s"$greeting, $name!"
  }

  test("Both approaches produce same output with default implementation") {
    // Both DI and No-DI approaches should produce the same output
    // when using equivalent service implementations
    val diService = new ServiceImpl
    val diClient = new Client(diService)
    val diResult = diClient.sayHello("Comparison")

    val noDiClient = new NoDIClient
    val noDiResult = noDiClient.sayHello("Comparison")

    // Both should produce the same result with their default implementations
    assertEquals(diResult, noDiResult)
    assertEquals(diResult, "Hello, Comparison!")
  }

  test("Comparison: DI vs No-DI approach flexibility") {
    // DI approach - flexible, can inject different implementations
    val mockService = new MockService("Custom")
    val diClient = new Client(mockService)
    val diResult = diClient.sayHello("Test")
    assertEquals(diResult, "Custom, Test!")

    // No-DI approach - inflexible, always uses the same implementation
    val noDiClient = new NoDIClient
    val noDiResult = noDiClient.sayHello("Test")
    assertEquals(noDiResult, "Hello, Test!")

    // This demonstrates that DI allows for more flexible testing and configuration
    assert(diResult != noDiResult) // Different results due to different implementations
  }

  test("DI approach allows for easy mocking") {
    // DI approach supports easy mocking for testing
    val mockService = new MockService("Mock")
    val client = new Client(mockService)
    val result = client.sayHello("TestUser")
    assertEquals(result, "Mock, TestUser!")

    // We can easily test different scenarios
    val errorService = new MockService("Error")
    val errorClient = new Client(errorService)
    val errorResult = errorClient.sayHello("ErrorTest")
    assertEquals(errorResult, "Error, ErrorTest!")
  }

  test("No-DI approach lacks testability") {
    // No-DI approach cannot be easily configured for different test scenarios
    val client = new NoDIClient
    // because we cannot replace the internal service dependency
    // We cannot test error conditions, different behaviors, etc.

    val result = client.sayHello("TestUser")
    assertEquals(result, "Hello, TestUser!")
    // Limited testing capabilities - always the same behavior
  }

  test("DI approach supports polymorphism") {
    // DI approach leverages polymorphism effectively
    val services: List[Service] = List(
      new ServiceImpl,
      new MockService("Polite"),
      new MockService("Casual")
    )
    val results = services.map { service =>
      val client = new Client(service)
      client.sayHello("Polymorphism")
    }
    // Different implementations produce different results
    assertEquals(results(0), "Hello, Polymorphism!")
    assertEquals(results(1), "Polite, Polymorphism!")
    assertEquals(results(2), "Casual, Polymorphism!")
    // All different, demonstrating flexibility
    assert(results.distinct.length == 3)
  }

  test("No-DI approach cannot leverage polymorphism") {
    // No-DI approach is locked into a single implementation
    val clients = List(
      new NoDIClient,
      new NoDIClient,
      new NoDIClient
    )
    val results = clients.map(_.sayHello("NoPolymorphism"))
    // All clients produce identical results
    results.foreach(result => assertEquals(result, "Hello, NoPolymorphism!"))
    // No variety - all the same
    assert(results.distinct.length == 1)
  }

  test("DI approach enables configuration at runtime") {
    // DI allows for runtime configuration
    def createClient(serviceType: String): Client = {
      val service = serviceType match {
        case "formal" => new MockService("Good day")
        case "casual" => new MockService("Hey")
        case "default" => new ServiceImpl
        case _ => new ServiceImpl
      }
      new Client(service)
    }

    val formalClient = createClient("formal")
    val casualClient = createClient("casual")
    val defaultClient = createClient("default")

    assertEquals(formalClient.sayHello("User"), "Good day, User!")
    assertEquals(casualClient.sayHello("User"), "Hey, User!")
    assertEquals(defaultClient.sayHello("User"), "Hello, User!")
  }

  test("No-DI approach lacks runtime configurability") {
    // No-DI approach cannot be configured at runtime
    def createClient(serviceType: String): NoDIClient = {
      // We cannot configure the service type - it's hardcoded
      new NoDIClient // Always creates the same type of client
    }

    val client1 = createClient("formal")   // Ignored
    val client2 = createClient("casual")   // Ignored
    val client3 = createClient("default")  // Ignored

    // All produce the same result regardless of intended configuration
    assertEquals(client1.sayHello("User"), "Hello, User!")
    assertEquals(client2.sayHello("User"), "Hello, User!")
    assertEquals(client3.sayHello("User"), "Hello, User!")
  }

  test("DI approach enables easy unit testing with different scenarios") {
    // Test normal scenario
    val normalService = new MockService("Hello")
    val normalClient = new Client(normalService)
    assertEquals(normalClient.sayHello("User"), "Hello, User!")

    // Test error scenario (simulating an error-prone service)
    val errorService = new MockService("ERROR")
    val errorClient = new Client(errorService)
    assertEquals(errorClient.sayHello("User"), "ERROR, User!")

    // Test empty service response
    val emptyService = new MockService("")
    val emptyClient = new Client(emptyService)
    assertEquals(emptyClient.sayHello("User"), ", User!")
  }

  test("No-DI approach limits testing scenarios") {
    // With No-DI, we can only test the one hardcoded scenario
    val client = new NoDIClient

    // We cannot test:
    // - Error conditions
    // - Different service behaviors
    // - Edge cases that depend on service implementation
    // - Performance with different service implementations

    assertEquals(client.sayHello("User"), "Hello, User!")
    // That's all we can test - the same behavior every time
  }

  test("DI approach demonstrates loose coupling") {
    // Client doesn't know the concrete implementation
    val service: Service = new MockService("Flexible") // Polymorphic assignment
    val client = new Client(service)
    assertEquals(client.sayHello("Design"), "Flexible, Design!")

    // Client works with any Service implementation
    val anotherService: Service = new ServiceImpl
    val anotherClient = new Client(anotherService)
    assertEquals(anotherClient.sayHello("Design"), "Hello, Design!")
  }

  test("No-DI approach demonstrates tight coupling") {
    // NoDIClient is tightly coupled to NoDIService
    val client = new NoDIClient
    // No way to change the service implementation
    // Client is bound to a specific concrete implementation
    assertEquals(client.sayHello("Design"), "Hello, Design!")
  }

  test("DI approach enables service composition") {
    // We can create complex service combinations with DI
    class LoggingService(underlying: Service) extends Service {
      override def greet(name: String): String = {
        val result = underlying.greet(name)
        // In real scenarios, this might log to a file or database
        s"[LOG] $result"
      }
    }

    val baseService = new MockService("Hi")
    val loggingService = new LoggingService(baseService)
    val client = new Client(loggingService)

    assertEquals(client.sayHello("User"), "[LOG] Hi, User!")
  }

  test("No-DI approach prevents service composition") {
    // With No-DI, we cannot compose or wrap services
    // The service is hardcoded inside NoDIClient
    val client = new NoDIClient
    
    // No way to add logging, caching, retry logic, etc.
    // without modifying the NoDIClient class itself
    assertEquals(client.sayHello("User"), "Hello, User!")
  }

  test("DI approach supports multiple service instances") {
    // With DI, we can have multiple different clients with different services
    val services = List(
      new MockService("Morning"),
      new MockService("Afternoon"), 
      new MockService("Evening")
    )

    val clients = services.map(new Client(_))
    val results = clients.zip(services).map { case (client, _) =>
      client.sayHello("Time")
    }

    assertEquals(results, List("Morning, Time!", "Afternoon, Time!", "Evening, Time!"))
    assert(results.distinct.length == 3) // All different
  }

  test("No-DI approach limited to single service type") {
    // No-DI only supports one type of service behavior
    val clients = (1 to 3).map(_ => new NoDIClient).toList
    val results = clients.map(_.sayHello("Time"))

    // All results are identical
    results.foreach(result => assertEquals(result, "Hello, Time!"))
    assert(results.distinct.length == 1) // All the same
  }
}
