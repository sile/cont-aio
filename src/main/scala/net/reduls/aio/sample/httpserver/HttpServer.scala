package net.reduls.aio.sample.httpserver

import java.net.{ServerSocket,Socket}

class HttpServer {
  def startSync(port:Int) {
    val serverSocket = new ServerSocket(port)
    println("start: " + serverSocket)
    
    try {
//    while(true) {
      val clientSocket = serverSocket.accept()
      try {
      println("accept: " + clientSocket)

      val request = HttpRequest(clientSocket.getInputStream)
      } finally {
        clientSocket.close
      }
      // TODO:
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
  
  def registerAction(mappingPathRegex:String, action:HttpAction) {
  }
}
