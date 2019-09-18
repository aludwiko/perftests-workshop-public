package info.ludwikowski.base

object ScalaImplicitConversion extends App {

  def doSomething(value: String) = {
    s"transformed $value"
  }

  println(doSomething("test"))

  def doSomething2(expression: Int => String) = {
    s"transformed2 ${expression(133)}"
  }

  type Expression=  Int => String

  def doSomething3(expression: Expression) = {
    s"transformed3 ${expression(123)}"
  }

  implicit def stringToExpression(value: String): Expression = {
    Int => value
  }

  println(doSomething2("test")) //int param is ignored
  println(doSomething3("test")) //int param is ignored
  println(doSomething3((i: Int) => "test" + i)) //int is used
}
