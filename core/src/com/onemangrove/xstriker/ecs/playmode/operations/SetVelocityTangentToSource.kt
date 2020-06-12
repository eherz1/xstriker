package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.RotationComponent
import com.onemangrove.xstriker.ecs.playmode.components.SourceComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setVelocityTangentToSource(magnitude: Float): Operation {
  val op = OperationFactory.operation(SetVelocityTangentToSource::class.java)
  op.magnitude = magnitude
  return op
}

class SetVelocityTangentToSource : SingleUseOperation() {
  class SetVelocityTangentToExecutor : SingleUseOperation.SingleUseExecutor<SetVelocityTangentToSource>() {
    @Wire
    private lateinit var sourceMapper: ComponentMapper<SourceComponent>

    @Wire
    private lateinit var rotationMapper: ComponentMapper<RotationComponent>

    @Wire
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun act(op: SetVelocityTangentToSource, node: OperationTree) {
      val entityId = op.entityId!!
      val source = sourceMapper.get(op.entityId)
      val otherEntityId = source.id!!
      val magnitude = op.magnitude!!
      val rotation = rotationMapper.get(otherEntityId)
      val angled = rotation?.rotation ?: 0f
      val angler = Math.toRadians(angled.toDouble())
      val vx = magnitude * Math.cos(angler)
      val vy = magnitude * Math.sin(angler)
      val velocity = velocityMapper.get(entityId)
      velocity.vx = vx.toFloat()
      velocity.vy = vy.toFloat()
    }
  }

  override fun executorType() = SetVelocityTangentToExecutor::class.java

  var magnitude: Float? = null
}
