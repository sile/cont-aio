package net.reduls.aio.sample.httpserver

import java.io.InputStream
import java.net.URI

class HttpRequest(val method:String, 
                  val path:String, 
                  val query:String,
                  val headers:Map[String,String]) {
}

object HttpRequest {
  def apply(in:InputStream):HttpRequest = {
    val (method,path,version) = parseRequestLine(in)
    val uri = new URI(path)

    val headers = parseRequestHeader(in)
    new HttpRequest(method, uri.getPath, uri.getQuery, headers)
  }

  private def parseRequestLine(in:InputStream) = {
    val line = readLine(in)
    val regexp = "^([^ ]+) +([^ ]+) +([^ ]+)$".r;
    line match { 
      case regexp(method, path, version) => (method, path, version)
      case _ => throw new Exception("Invalid http request line: " + line)
    }
  }

  private def parseRequestHeader(in:InputStream) = {
    def recur(headers:Map[String,String]):Map[String,String] = {
      var line = readLine(in)
      if(line.isEmpty) {
        headers
      } else {
        val regexp = "^([^:]+):(.*)$".r
        line match {
          case regexp(name, value) => recur(headers + (name -> value.trim))
          case _ => throw new Exception("Invalid http header line: " + line)
        }
      }
    }
    recur(Map[String,String]())
  }

  private def readLine(in:InputStream):String = {
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
