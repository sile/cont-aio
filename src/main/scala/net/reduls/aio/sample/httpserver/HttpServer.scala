package net.reduls.aio.sample.httpserver

import java.net.{ServerSocket,Socket}

class HttpServer {
  def startSync(port:Int) {
    val serverSocket = new ServerSocket(port)
    println("start: " + serverSocket)
    
//    while(true) {
      val clientSocket = serverSocket.accept()
      println("accept: " + clientSocket)

      val request = HttpRequest(clientSocket.getInputStream)
    println(request)
      // TODO:
//    }
  }

  def startAsync(port:Int) {
    val serverSocket = new ServerSocket(port)
    val clientSocket = serverSocket.accept()
    // TODO:    
  }
  
  def registerAction(mappingPathRegex:String, action:HttpAction) {
  }
}
