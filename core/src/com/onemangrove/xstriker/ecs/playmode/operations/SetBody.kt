package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.CollidableComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setBody(width: Float, height: Float): Operation {
  val op = operation(SetBody::class.java)
  op.width = width
  op.height = height
  return op
}

class SetBody : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetBody>() {
    @Wire
    private lateinit var collidableMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.CollidableComponent>

    override fun act(op: SetBody, node: OperationTree) {
      var collidable = collidableMapper.get(op.entityId)
      collidable.bodyWidth = op.width!!
      collidable.bodyHeight = op.height!!
    }
  }

  override fun executorType() = Executor::class.java

  var width: Float? = null
  var height: Float? = null
}
