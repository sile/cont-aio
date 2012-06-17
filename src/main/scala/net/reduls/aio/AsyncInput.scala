package net.reduls.aio

import scala.util.continuations.shift
import scala.actors.Actor.actor
import java.io.InputStream

trait AsyncInput extends InputStream {
  def asyncRead() = {
    if(available != 0) {
      read()
    } else {
      shift {ctx: (Int => Unit) =>
        actor{
          ctx(read())
        }:Unit
      }   
    }
  }

  def asyncRead(bytes:Array[Byte]) = {
    if(available != 0) {
      read(bytes)
    } else {
      shift {ctx: (Int => Unit) =>
        actor{
          ctx(read(bytes))
        }:Unit
      }
    }
  }
  
  def asyncRead(bytes:Array[Byte], offset:Int, length:Int) = {
    if(available != 0) {
      read(bytes)
    } else {
      shift {ctx: (Int => Unit) =>
        actor{
          ctx(read(bytes, offset, length))
        }:Unit
      }
    }
  }
}

/*
 [sample code]
 import java.io._
 import net.reduls.aio._
 import scala.util.continuations._

 val buf = new Array[Byte](10)
 val in = new BufferedInputStream(System.in) with AsyncInput

 println("before reset")
 AsyncContext.begin {
   val len = in.asyncRead(buf)
   println("len: "+len)
   println("bytes: " + buf)
 }
 println("after reset")
 */
