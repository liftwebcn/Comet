
package code
package comet

import scala.collection.mutable.{Map, HashMap}
import java.util.Date
import net.liftweb._
import http._
import actor._
import util.StringHelpers.randomString

case class UpdateLinks(topLinks: List[Link])
case class AddLink(url: String, title: String)
case class VoteUp(linkId: String)
case class VoteDown(linkId: String)


case class Link(id: String, entry: LinkEntry)
case class LinkEntry(url: String, title: String, var score: Int, var num: Long)

object LinkServer extends LiftActor with ListenerManager {
	private var links: Map[String, LinkEntry] = HashMap[String, LinkEntry]()
	private var topLinks: List[Link] = Nil
  private var t:Long = 1;

	def createUpdate: UpdateLinks = {
		UpdateLinks(topLinks)
	}

	def updateTopLinks= {
		topLinks = links.toList.map(p => Link(p._1, p._2)).
		    sort((a,b) => if(a.entry.score == b.entry.score) a.entry.num < b.entry.num else a.entry.score > b.entry.score )
    updateListeners(UpdateLinks(topLinks))
	}

	override def lowPriority = {

		case l: AddLink => try {
      links += randomString (12) -> LinkEntry(l.url, l.title, 1, t)
      t += 1
			updateTopLinks
		}

		case l: VoteUp => try {
			links(l.linkId).score += 1
			updateTopLinks
		}

		case l: VoteDown => try {
			links(l.linkId).score -= 1
			updateTopLinks
		}
	}
}

