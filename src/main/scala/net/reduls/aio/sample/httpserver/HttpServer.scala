package net.reduls.aio.sample.httpserver

import java.net.{ServerSocket,Socket}
import scala.util.matching.Regex

class HttpServer {
  val actionMapping = scala.collection.mutable.Map[Regex, HttpAction]()

  def startSync(port:Int) {
    val serverSocket = new ServerSocket(port)
    println("start: " + serverSocket)
    
    try {
//    while(true) {
      val clientSocket = serverSocket.accept()
      try {
        println("accept: " + clientSocket)
        val request = HttpRequest(clientSocket.getInputStream)
        selectAction(request) match {
          case None => 
            println("no action can handle: " + request.path)
          case Some(action) =>
            val response = new HttpResponse
            action.execute(request, response)
            response.output(clientSocket.getOutputStream)
        }
      } finally {
        clientSocket.close
      }
//    }
    } finally {
      serverSocket.close
    }
  }

  def startAsync(port:Int) {
    val serverSocket = new ServerSocket(port)
    val clientSocket = serverSocket.accept()
    // TODO:    
  }
  
  def registerAction(mappingPathRegexp:String, action:HttpAction) {
    actionMapping += (mappingPathRegexp.r -> action)
  }

  private def selectAction(request:HttpRequest):Option[HttpAction] = {
    actionMapping.foreach{
      case (regexp, action) =>
        if(regexp.findFirstIn(request.path) != None) {
          return Some(action)
        }
    }
    None
  }
}
