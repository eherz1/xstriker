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

    @Wire
    private lateinit var parentMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.ParentComponent>

    override fun act(op: Destroy, node: OperationTree) {
      var parent = parentMapper.get(op.entityId)
      parent?.children?.forEach { destroy().register(worldHolder.world, it) }
      worldHolder.world.delete(op.entityId)
    }
  }

  override fun executorType(): Class<Executor> = Executor::class.java
}
