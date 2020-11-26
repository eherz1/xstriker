package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.utils.isOffscreen
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun move(dx: Float, dy: Float): Operation {
  val op = operation(Move::class.java)
  op.dx = dx
  op.dy = dy
  return op
}

class Move : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<Move>() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.PositionComponent>

    override fun act(op: Move, node: OperationTree) {
      val position = positionMapper.get(op.entityId)
      if (!isOffscreen(position.x + op.dx, position.y + op.dy)) {
        position.x += op.dx
        position.y += op.dy
      }
    }
  }

  override fun executorType() = Executor::class.java

  var dx = 0f
  var dy = 0f
}
