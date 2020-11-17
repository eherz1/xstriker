package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.DyingComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun markDying(): Operation {
  val op = OperationFactory.operation(MarkDying::class.java)
  return op
}

class MarkDying : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<MarkDying>() {
    @Wire
    private lateinit var dyingMapper: ComponentMapper<DyingComponent>

    override fun act(op: MarkDying, node: OperationTree) {
      dyingMapper.create(op.entityId)
    }
  }

  override fun executorType() = Executor::class.java
}
