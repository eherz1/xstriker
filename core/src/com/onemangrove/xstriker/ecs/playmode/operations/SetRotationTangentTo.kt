package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.RotationComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setRotationTangentTo(trackedEntityId: Int): Operation {
  val op = OperationFactory.operation(SetRotationTangentTo::class.java)
  op.trackedEntityId = trackedEntityId
  return op
}

class SetRotationTangentTo : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetRotationTangentTo>() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>

    @Wire
    private lateinit var rotationMapper: ComponentMapper<RotationComponent>

    @Wire
    private lateinit var tagManager: TagManager

    override fun act(op: SetRotationTangentTo, node: OperationTree) {
      val entityId = op.entityId!!
      // TODO
      val trackedEntityId = op.trackedEntityId!!
      if (trackedEntityId < 0) return
//      val player = tagManager.getEntity("player") ?: return
//      val trackedEntityId = player.id
      val entityPosition = positionMapper.get(entityId) ?: return
      val entityX = entityPosition.x
      val entityY = entityPosition.y
      val trackedEntityPosition = positionMapper.get(trackedEntityId) ?: return
      val trackedX = trackedEntityPosition.x
      val trackedY = trackedEntityPosition.y
      val rotation = Math.toDegrees(Math.atan2((trackedY - entityY).toDouble(), (trackedX - entityX).toDouble())).toFloat()
      var entityRotation = rotationMapper.get(entityId) ?: rotationMapper.create(entityId)
      entityRotation.rotation = rotation
    }
  }

  override fun executorType() = Executor::class.java

  var trackedEntityId: Int? = null
}
