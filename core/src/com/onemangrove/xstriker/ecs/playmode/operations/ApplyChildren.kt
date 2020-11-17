package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ParentComponent
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun applyChildren(childOperation: Operation): Operation {
  val op = OperationFactory.operation(ApplyChildren::class.java)
  op.childOperation = childOperation
  return op
}

class ApplyChildren : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<ApplyChildren>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    @Wire
    private lateinit var parentMapper: ComponentMapper<ParentComponent>

    override fun act(op: ApplyChildren, node: OperationTree) {
      val world = worldHolder.world
      val parent = parentMapper.get(op.entityId) ?: return
      val childOperation = op.childOperation!!
      for (childId in parent.children) {
        // Temporary hack
        try {
          childOperation.register(world, childId)
        } catch (re: RuntimeException) {
        }
      }
    }
  }

  override fun executorType() = Executor::class.java

  var childOperation: Operation? = null
}
