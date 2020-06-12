package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setPositionRelativeToCamera(x: Float = 0f, y: Float = 0f): Operation {
  val op = OperationFactory.operation(SetPositionRelativeToCamera::class.java)
  op.x = x
  op.y = y
  return op
}

class SetPositionRelativeToCamera : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetPositionRelativeToCamera>() {
    @Wire
    private lateinit var tagManager: TagManager

    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>

    override fun act(op: SetPositionRelativeToCamera, node: OperationTree) {
      val entityPosition = positionMapper.get(op.entityId)
      val cameraId = tagManager.getEntityId("camera")
      val cameraPosition = positionMapper.get(cameraId)
      entityPosition.x = cameraPosition.x + op.x
      entityPosition.y = cameraPosition.y + op.y
      println("Set entity position relative ${op.x}, ${op.y} to camera => ${entityPosition.x}, ${entityPosition.y}")
    }
  }

  override fun executorType() = Executor::class.java

  var x = 0f
  var y = 0f
}
