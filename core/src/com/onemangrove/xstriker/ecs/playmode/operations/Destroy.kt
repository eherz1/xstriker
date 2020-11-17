package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun destroy(): Operation {
  val op = operation(Destroy::class.java)
  return op
}

class Destroy : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<Destroy>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    override fun act(op: Destroy, node: OperationTree) {
      val world = worldHolder.world
      val entityId = op.entityId
      world.delete(entityId)
    }
  }

  override fun executorType(): Class<Executor> = Executor::class.java
}
