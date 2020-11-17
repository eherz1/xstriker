package com.onemangrove.xstriker.ecs.playmode.operations

import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun callback(function: () -> Unit): Operation {
  val op = OperationFactory.operation(Callback::class.java)
  op.function = function
  return op
}

class Callback : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<Callback>() {
    override fun act(op: Callback, node: OperationTree) {
      val function = op.function!!
      function.invoke()
    }
  }

  override fun executorType() = Executor::class.java

  var function: (() -> Unit)? = null
}
