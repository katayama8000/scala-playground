package examples

trait Service {
  def greet(name: String): String
}

class ServiceImpl extends Service {
  override def greet(name: String): String = s"Hello, $name!"
}

class Client(service: Service) {
  def sayHello(name: String): String = service.greet(name)
}

// Usage example
object DependencyInjectionExample extends App {
  val service = new ServiceImpl
  val client = new Client(service)
  println(client.sayHello("Scala"))
}

// DI しない場合の例
class NoDIService {
  def greet(name: String): String = s"Hello, $name!"
}

class NoDIClient {
  private val service = new NoDIService
  def sayHello(name: String): String = service.greet(name)
}

object NoDependencyInjectionExample extends App {
  val client = new NoDIClient
  println(client.sayHello("Scala"))
}
