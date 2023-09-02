package examples.derivation

import cats.Order
import cats.syntax.all._
import io.estatico.newtype.macros.newtype
import derevo.cats._
import derevo.derive
import derevo.circe.magnolia.{ decoder, encoder }

@annotation.nowarn("msg=unused value")
object DerevoCodecDerivation extends App {

  @derive(decoder, encoder, eqv, order, show)
  case class Person(age: Person.Age, name: Person.Name)

  object Person {
    @derive(decoder, encoder, eqv, order, show)
    @newtype case class Age(value: Int)

    @derive(decoder, encoder, eqv, order, show)
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

  import io.circe.parser.decode
  import io.circe.syntax._

  val p3                                  = Person(Person.Age(50), Person.Name("Gustavo"))
  val enc: String                         = p3.asJson.noSpaces
  val dec: Either[io.circe.Error, Person] = decode[Person](enc)

  dec === Right(p3)

  // Map codecs

  import java.util.UUID
  import derevo.circe.magnolia._

  @derive(decoder, encoder, eqv, show)
  @newtype
  case class Cart(items: Map[ItemId, Quantity])

  object Cart {
    @derive(keyDecoder, keyEncoder, eqv, show)
    @newtype
    case class ItemId(value: UUID)

    @derive(decoder, encoder, eqv, show)
    @newtype
    case class Quantity(value: Int)
  }
}
