package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setPosition(x: Float, y: Float): Operation {
  val op = OperationFactory.operation(SetPosition::class.java)
  op.x = x
  op.y = y
  return op
}

class SetPosition : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetPosition>() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.PositionComponent>

    override fun act(op: SetPosition, node: OperationTree) {
      val position = positionMapper.get(op.entityId)
      position.x = op.x
      position.y = op.y
    }
  }

  override fun executorType() = Executor::class.java

  var x = 0f
  var y = 0f
}
