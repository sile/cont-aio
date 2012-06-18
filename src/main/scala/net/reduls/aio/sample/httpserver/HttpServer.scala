package net.reduls.aio.sample.httpserver

import java.net.{ServerSocket,Socket}

class HttpServer {
  def startSync(port:Int) {
    val serverSocket = new ServerSocket(port)
    val clientSocket = serverSocket.accept()
    // TODO:
  }

  def startAsync(port:Int) {
    val serverSocket = new ServerSocket(port)
    val clientSocket = serverSocket.accept()
    // TODO:    
  }
  
  def registerAction(mappingPathRegex:String, action:HttpAction) {
  }
}
