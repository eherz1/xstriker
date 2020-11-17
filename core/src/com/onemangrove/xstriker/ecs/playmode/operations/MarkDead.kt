package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.DeadComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun markDead(): Operation {
  val op = OperationFactory.operation(MarkDead::class.java)
  return op
}

class MarkDead : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<MarkDead>() {
    @Wire
    private lateinit var deadMapper: ComponentMapper<DeadComponent>

    override fun act(op: MarkDead, node: OperationTree) {
      deadMapper.create(op.entityId)
    }
  }

  override fun executorType() = Executor::class.java
}
