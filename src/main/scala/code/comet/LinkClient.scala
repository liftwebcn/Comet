
package code
package comet

import scala.xml._

import net.liftweb._
import http._
import util._
import Helpers._

class LinkClient extends CometActor with CometListener {
	private var links: List[Link] = List[Link]()

	def registerWith = LinkServer

	override def lowPriority = {
		case data: UpdateLinks => try {
			links = data.topLinks
			reRender()
		}
	}

	def render: RenderOut = {
		def linkView(link: Link): NodeSeq = {
      <ul type="none">
			<li>
          {link.entry.url} say:
          {link.entry.title}
			{SHtml.a(() => { LinkServer ! VoteUp(link.id) }, Text("Ding"))}
			</li>
      </ul>
		}

		"#view" #> <h5>{links.flatMap(linkView _)}</h5>
	}
}

