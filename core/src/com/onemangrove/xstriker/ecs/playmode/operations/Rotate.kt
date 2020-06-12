package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.RotationComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun rotate(degrees: Float): Operation {
  val op = OperationFactory.operation(Rotate::class.java)
  op.degrees = degrees
  return op
}

class Rotate : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<Rotate>() {
    @Wire
    private lateinit var rotationMapper: ComponentMapper<RotationComponent>

    override fun act(op: Rotate, node: OperationTree) {
      val entityId = op.entityId!!
      val degrees = op.degrees!!
      val rotationComponent = rotationMapper.get(entityId)
      var rotation = rotationComponent.rotation + degrees
      if (rotation > 360)
        rotation -= 360
      if (rotation < 0)
        rotation += 360
      rotationComponent.rotation = rotation
    }
  }

  override fun executorType() = Executor::class.java

  var degrees: Float? = null
}

