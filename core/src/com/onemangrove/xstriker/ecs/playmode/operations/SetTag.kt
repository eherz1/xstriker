package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setTag(tag: String): Operation {
  val op = OperationFactory.operation(SetTag::class.java)
  op.tag = tag
  return op
}

class SetTag : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetTag>() {
    @Wire
    private lateinit var tagManager: TagManager

    override fun act(op: SetTag, node: OperationTree) {
      val entityId = op.entityId!!
      val tag = op.tag!!
      tagManager.register(tag, entityId)
    }
  }

  override fun executorType() = Executor::class.java

  var tag: String? = null
}
