package examples.derivation

import cats.Order
import cats.syntax.all._
import io.estatico.newtype.macros.newtype
import derevo.cats._
import derevo.derive

@annotation.nowarn("msg=unused value")
object DerevoDerivation extends App {

  @derive(eqv, order, show)
  case class Person(age: Person.Age, name: Person.Name)

  object Person {
    @derive(eqv, order, show)
    @newtype case class Age(value: Int)

    @derive(eqv, order, show)
    @newtype case class Name(value: String)
  }

  Order[Person]

  val p1 = Person(Person.Age(33), Person.Name("Gabriel"))
  val p2 = Person(Person.Age(29), Person.Name("Alicja"))

  println(p1 === p1)
  println(p1 === p2)
  println(p1 < p2)
  println(p1.show)
  println(p2.show)
}
