package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun get(tagName: String, operation: Operation): Operation {
  val op = OperationFactory.operation(Get::class.java)
  op.tagName = tagName
  op.operation = operation
  return op
}

class Get : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<Get>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    @Wire
    private lateinit var tagManager: TagManager

    override fun act(op: Get, node: OperationTree) {
      val world = worldHolder.world
      val entityId = tagManager.getEntityId(op.tagName)
      op.operation!!.register(world, entityId)
    }
  }

  override fun executorType() = Executor::class.java

  var tagName: String? = null
  var operation: Operation? = null
}
