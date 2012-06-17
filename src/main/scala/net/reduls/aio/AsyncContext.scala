package net.reduls.aio

import scala.util.continuations.reset

object AsyncContext {
  def begin[A, C](ctx: => A @scala.util.continuations.cpsParam[A,C]): C = {
    reset(ctx)
  }
}
