package controllers

import com.github.nscala_time.time.Imports._
import models.Events.eventWrites
import net.mtgto.garoon.Id
import net.mtgto.garoon.schedule.{EventId, EventRepository}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import scala.util.{Failure, Success}

object ScheduleController extends Controller with BaseController {
  private[this] val eventRepository: EventRepository = new EventRepository(garoonClient)

  def getEvent(eventId: Int) = Action {
    eventRepository.resolve(EventId(eventId.toString)) match {
      case Success(event) => Ok(Json.toJson(event))
      case Failure(e) => NotFound(Json.toJson(""))
    }
  }

  def getEventsByUserId(userId: Int, start: Long, end: Long) = Action {
    val interval = new DateTime(start * 1000) to new DateTime(end * 1000)
    eventRepository.findByUserId(Id(userId.toString), interval) match {
      case Success(event) => Ok(Json.toJson(event))
      case Failure(e) => NotFound(Json.toJson(""))
    }
  }
}
