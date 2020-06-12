package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import com.onemangrove.xstriker.ecs.playmode.components.ParentComponent
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun applyChild(childName: String, childOperation: Operation): Operation {
  val op = OperationFactory.operation(ApplyChild::class.java)
  op.childName = childName
  op.childOperation = childOperation
  return op
}

class ApplyChild : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<ApplyChild>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    @Wire
    private lateinit var parentMapper: ComponentMapper<ParentComponent>

    @Wire
    private lateinit var childMapper: ComponentMapper<ChildComponent>

    override fun act(op: ApplyChild, node: OperationTree) {
      val world = worldHolder.world
      val parent = parentMapper.get(op.entityId)
      val childId = parent.findChild(childMapper, op.childName!!)!!
      op.childOperation!!.register(world, childId)
    }
  }

  override fun executorType() = Executor::class.java

  var childName: String? = null
  var childOperation: Operation? = null
}
