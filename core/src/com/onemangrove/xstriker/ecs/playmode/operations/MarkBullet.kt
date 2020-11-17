package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun markBullet(): Operation {
  val op = operation(MarkBullet::class.java)
  return op
}

class MarkBullet : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<MarkBullet>() {
    @Wire
    private lateinit var collidableMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.CollidableComponent>

    override fun act(op: MarkBullet, node: OperationTree) {
      val collidable = collidableMapper.get(op.entityId)
      collidable.isBullet = true
    }
  }

  override fun executorType() = Executor::class.java
}
