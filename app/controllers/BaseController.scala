package controllers

import java.net.URI
import net.mtgto.garoon.GaroonClient
import org.sisioh.dddbase.core.lifecycle.sync.SyncEntityIOContext
import play.api.Play
import play.api.mvc.Controller

trait BaseController {
  self: Controller =>

  protected[this] def getConfiguration(configName: String, envName: String): String =
    Option(System.getenv(envName)).getOrElse(Play.current.configuration.getString(configName).get)

  protected[this] implicit val context = SyncEntityIOContext

  protected[this] val garoonURI: URI = new URI(getConfiguration("garoon.uri", "GAROON_URI"))

  protected[this] def garoonClient: GaroonClient = {
    try {
      new GaroonClient(garoonURI)
    } catch {
      case e: Exception => e.printStackTrace(); throw e
    }
  }
}
