package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._

class HelloWorld {
  lazy val date: Box[Date] = DependencyFactory.inject[Date]

  def howdy = "#hour" #> date.map(_.getHours.toString) &  "#mins" #> date.map(_.getMinutes.toString)

}

