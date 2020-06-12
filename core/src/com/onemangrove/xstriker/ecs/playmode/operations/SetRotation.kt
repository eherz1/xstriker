package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.RotationComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setRotation(rotation: Float): Operation {
  val op = OperationFactory.operation(SetRotation::class.java)
  op.rotation = rotation
  return op
}

class SetRotation : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetRotation>() {
    @Wire
    private lateinit var rotationMapper: ComponentMapper<RotationComponent>

    override fun act(op: SetRotation, node: OperationTree) {
      val rotationComponent = rotationMapper.get(op.entityId) ?: rotationMapper.create(op.entityId)
      rotationComponent.rotation = op.rotation!!
    }
  }

  override fun executorType() = Executor::class.java

  var rotation: Float? = null
}
