package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setPositionEqualTo(otherEntityId: Int): Operation {
  val op = OperationFactory.operation(SetPositionEqualTo::class.java)
  op.otherEntityId = otherEntityId
  return op
}

class SetPositionEqualTo : SingleUseOperation() {
  class SetPositionEqualToExecutor : SingleUseOperation.SingleUseExecutor<SetPositionEqualTo>() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.PositionComponent>

    override fun act(op: SetPositionEqualTo, node: OperationTree) {
      val entityId = op.entityId!!
      val otherEntityId = op.otherEntityId!!
      val otherPosition = positionMapper.get(otherEntityId)
      val position = positionMapper.get(entityId)
      position.x = otherPosition.x
      position.y = otherPosition.y
    }
  }

  override fun executorType() = SetPositionEqualToExecutor::class.java

  var otherEntityId: Int? = null
}
