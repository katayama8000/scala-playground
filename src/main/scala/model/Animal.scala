package model

trait Animal:
  def sound(): String

class Dog extends Animal:
  def sound(): String = "Woof!"

class Cat extends Animal:
  def sound(): String = "Meow!"
