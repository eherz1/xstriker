package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun applyParent(parentOperation: Operation): Operation {
  val op = OperationFactory.operation(ApplyParent::class.java)
  op.parentOperation = parentOperation
  return op
}

class ApplyParent : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<ApplyParent>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    @Wire
    private lateinit var childMapper: ComponentMapper<ChildComponent>

    @Wire
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun act(op: ApplyParent, node: OperationTree) {
      val world = worldHolder.world
      val child = childMapper.get(op.entityId)
      val parentId = child.parentId!!
      val parentOp = op.parentOperation!!
      parentOp.register(world, parentId)
    }
  }

  override fun executorType() = Executor::class.java

  var parentOperation: Operation? = null
}
