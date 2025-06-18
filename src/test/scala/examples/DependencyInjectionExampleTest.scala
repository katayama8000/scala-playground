package examples

import munit.FunSuite

class DependencyInjectionExampleTest extends FunSuite {

  // テスト用のモックサービス
  class MockService(greeting: String) extends Service {
    override def greet(name: String): String = s"$greeting, $name!"
  }

  test("ServiceImpl should greet with 'Hello' prefix") {
    val service = new ServiceImpl
    val result = service.greet("World")
    assertEquals(result, "Hello, World!")
  }

  test("ServiceImpl should greet with different names") {
    val service = new ServiceImpl
    assertEquals(service.greet("Alice"), "Hello, Alice!")
    assertEquals(service.greet("Bob"), "Hello, Bob!")
    assertEquals(service.greet("Scala"), "Hello, Scala!")
  }

  test("ServiceImpl should handle empty name") {
    val service = new ServiceImpl
    val result = service.greet("")
    assertEquals(result, "Hello, !")
  }

  test("Client should use injected service") {
    val mockService = new MockService("Hi")
    val client = new Client(mockService)
    val result = client.sayHello("Test")
    assertEquals(result, "Hi, Test!")
  }

  test("Client should work with ServiceImpl") {
    val service = new ServiceImpl
    val client = new Client(service)
    val result = client.sayHello("User")
    assertEquals(result, "Hello, User!")
  }

  test("Client should delegate to service correctly") {
    val customService = new MockService("Greetings")
    val client = new Client(customService)
    assertEquals(client.sayHello("Developer"), "Greetings, Developer!")
  }

  test("Dependency injection pattern works") {
    // 異なるサービス実装をテスト
    val politeService = new MockService("Good morning")
    val casualService = new MockService("Hey")

    val politeClient = new Client(politeService)
    val casualClient = new Client(casualService)

    assertEquals(politeClient.sayHello("Sir"), "Good morning, Sir!")
    assertEquals(casualClient.sayHello("Buddy"), "Hey, Buddy!")
  }

  test("Service trait abstraction allows multiple implementations") {
    // 複数の実装が同じインターフェースを満たすことをテスト
    val implementations: List[Service] = List(
      new ServiceImpl,
      new MockService("Hello"),
      new MockService("Hi")
    )

    implementations.foreach { service =>
      val greeting = service.greet("Test")
      assert(greeting.contains("Test"))
      assert(greeting.endsWith("!"))
    }
  }

  test("Client works with any Service implementation") {
    // Clientが任意のService実装で動作することをテスト
    val services = List(
      new ServiceImpl,
      new MockService("Welcome"),
      new MockService("Salutations")
    )

    services.foreach { service =>
      val client = new Client(service)
      val result = client.sayHello("TestUser")
      assert(result.contains("TestUser"))
      assert(result.endsWith("!"))
    }
  }

  test("DI pattern supports easy testing with mocks") {
    // DIパターンではモックを使ったテストが簡単
    val mockService = new MockService("Test")
    val client = new Client(mockService)
    val result = client.sayHello("DI")

    assertEquals(result, "Test, DI!")
    assert(result.contains("DI"))
  }

  test("DI pattern enables loose coupling") {
    // DIパターンは疎結合を実現
    val service1 = new MockService("Morning")
    val service2 = new MockService("Evening")

    val client1 = new Client(service1)
    val client2 = new Client(service2)

    assertEquals(client1.sayHello("User"), "Morning, User!")
    assertEquals(client2.sayHello("User"), "Evening, User!")

    // 同じクライアントクラスでも異なるサービス実装で異なる結果
    assert(client1.sayHello("Test") != client2.sayHello("Test"))
  }
}
