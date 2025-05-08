package utils

object MathUtils:
  // function
  def add1(x: Int, y: Int): Int = x + y

  // function with default parameter
  def add2(x: Int, y: Int = 1): Int = x + y

  // function with variable number of parameters
  def add3(x: Int, y: Int*): Int = x + y.sum

  // function with named parameters
  def add4(x: Int, y: Int = 1, z: Int = 2): Int = x + y + z
