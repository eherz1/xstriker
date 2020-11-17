package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.SourceComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setPositionEqualToSource(): Operation {
  val op = operation(SetPositionEqualToSource::class.java)
  return op
}

class SetPositionEqualToSource : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetPositionEqualToSource>() {
    @Wire
    private lateinit var sourceMapper: ComponentMapper<SourceComponent>

    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>

    override fun act(op: SetPositionEqualToSource, node: OperationTree) {
      val entityId = op.entityId
      val source = sourceMapper.get(entityId) ?: return
      val otherEntityId = source.id!!
      val otherPosition = positionMapper.get(otherEntityId) ?: return
      val position = positionMapper.get(entityId) ?: return
      position.x = otherPosition.x
      position.y = otherPosition.y
    }
  }

  override fun executorType() = Executor::class.java
}
