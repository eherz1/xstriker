package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.RotationComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setVelocityTangentTo(otherEntityId: Int, magnitude: Float): Operation {
  val op = OperationFactory.operation(SetVelocityTangentTo::class.java)
  op.otherEntityId = otherEntityId
  op.magnitude = magnitude
  return op
}

class SetVelocityTangentTo : SingleUseOperation() {
  class SetVelocityTangentToExecutor : SingleUseOperation.SingleUseExecutor<SetVelocityTangentTo>() {
    @Wire
    private lateinit var rotationMapper: ComponentMapper<RotationComponent>

    @Wire
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun act(op: SetVelocityTangentTo, node: OperationTree) {
      val entityId = op.entityId!!
      val otherEntityId = op.otherEntityId!!
      val magnitude = op.magnitude!!
      val rotation = rotationMapper.get(otherEntityId)
      val angled = if (rotation != null) rotation.rotation else 0f
      val angler = Math.toRadians(angled.toDouble())
      val vx = magnitude * Math.cos(angler)
      val vy = magnitude * Math.sin(angler)
      val velocity = velocityMapper.get(entityId)
      velocity.vx = vx.toFloat()
      velocity.vy = vy.toFloat()
    }
  }

  override fun executorType() = SetVelocityTangentToExecutor::class.java

  var otherEntityId: Int? = null

  var magnitude: Float? = null
}
