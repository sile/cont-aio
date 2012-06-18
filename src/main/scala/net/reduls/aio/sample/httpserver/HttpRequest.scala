package net.reduls.aio.sample.httpserver

import java.io.InputStream

class HttpRequest(val method:String) {
//  val path
//  val params
//  val headers
}

object HttpRequest {
  def apply(in:InputStream):HttpRequest = {
    val (method,url,version) = parseRequestLine(in)
    new HttpRequest(method)
  }

  def parseRequestLine(in:InputStream) = {
    val line = readLine(in)
    val regexp = "^([^ ]+) +([^ ]+) +([^ ]+)$".r;
    line match { 
      case regexp(method, path, version)  => (method, path, version)
      case _ => throw new Exception("Invalid http request line: " + line)
    }
  }

  def readLine(in:InputStream):String = {
    val sb = new StringBuilder
    def recur (pred:Int):String = {
      val c = in.read
      if(pred == -1 || (pred == '\r' && c == '\n')) {
        sb.toString
      } else {
        sb.append(pred.toChar)
        recur(c)
      } 
    }
    recur(in.read)
  }
}
