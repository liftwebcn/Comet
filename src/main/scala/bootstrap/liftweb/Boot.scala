package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._



class Boot {
  def boot {
    LiftRules.addToPackages("code")

    val entries = List(
      Menu.i("Home") / "index",
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")))
    LiftRules.setSiteMap(SiteMap(entries:_*))

    LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

  }
}
