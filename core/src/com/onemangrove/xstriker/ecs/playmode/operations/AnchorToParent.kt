package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.AnchoredComponent
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun anchorToParent(): Operation = OperationFactory.operation(AnchorToParent::class.java)

class AnchorToParent : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<AnchorToParent>() {
    @Wire
    private lateinit var childMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.ChildComponent>

    @Wire
    private lateinit var attachedMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.AnchoredComponent>

    override fun act(op: AnchorToParent, node: OperationTree) {
      val childComponent = childMapper.get(op.entityId)
      val attachedComponent = attachedMapper.create(op.entityId) ?: attachedMapper.create(op.entityId)
      attachedComponent.anchoredToId = childComponent.parentId!!
    }
  }

  override fun executorType() = Executor::class.java
}
