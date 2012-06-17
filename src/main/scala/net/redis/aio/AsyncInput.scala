package net.reduls.aio

import scala.util.continuations._
import scala.actors._
import scala.actors.Actor._
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
}
