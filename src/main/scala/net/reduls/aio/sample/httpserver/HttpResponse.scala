package net.reduls.aio.sample.httpserver

import java.io.OutputStream
import java.util.ArrayList
import java.io.OutputStreamWriter
import java.io.BufferedWriter
import scala.collection.JavaConversions._

class HttpResponse {
  var status = 200
  var message = "OK"
  
  val headers = scala.collection.mutable.Map[String,String]()
  val contents = new ArrayList[String]()
  
  def setStatus(status:Int, message:String) {
    this.status = status
    this.message = message
  }

  def addHeader(name:String, value:String) {
    headers += (name -> value)
  }

  def addContent(content:String) {
    contents.add(content)
  }

  def output(out:OutputStream) {
    val writer = new BufferedWriter(new OutputStreamWriter(out))
    writer.write("HTTP/1.0 " + status + " " + message + "\r\n");
    headers.foreach{
      case (name,value) =>
        writer.write(name+": "+value+"\r\n")
    }
    writer.write("\r\n")

    contents.foreach(writer.write)
    writer.flush
  }
}
