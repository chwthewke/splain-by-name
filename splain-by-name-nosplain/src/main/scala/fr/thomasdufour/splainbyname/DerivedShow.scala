package fr.thomasdufour.splainbyname

import cats.Show
import cats.derived.semi
import cats.instances.string._

// WITHOUT splain
object DerivedShow {

  case class Inner( x: Option[Int] )

  object Inner {
    implicit val innerShow: Show[Inner] =
      Show.show( _.x.fold( "*" )( _.toString ) )
  }

  case class Outer( name: String, inner: Inner )

  object Outer {
    // This works
    implicit val outerShow: Show[Outer] = semi.show[Outer]

    // This succeeds too
    val ok: Show[Outer] = {
      import cats.derived.MkShow
      import cats.derived.util.VersionSpecific.Lazy
      import shapeless.LabelledGeneric
      import shapeless.Typeable
      import shapeless.the

      val genOuter = the[LabelledGeneric[Outer]]

      MkShow
        .mkShowGenericProduct[Outer, genOuter.Repr]( genOuter, Typeable[Outer], Lazy.instance( MkShow[genOuter.Repr] ) )
    }
  }

}
