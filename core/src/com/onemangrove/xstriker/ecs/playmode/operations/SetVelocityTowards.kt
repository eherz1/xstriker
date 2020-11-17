package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setVelocityTowards(otherEntityId: Int, magnitude: Float): Operation {
  val op = OperationFactory.operation(SetVelocityTowards::class.java)
  op.otherEntityId = otherEntityId
  op.magnitude = magnitude
  return op
}

class SetVelocityTowards : SingleUseOperation() {
  class SetVelocityTangentToExecutor : SingleUseOperation.SingleUseExecutor<SetVelocityTowards>() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>

    @Wire
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun act(op: SetVelocityTowards, node: OperationTree) {
      val e1 = op.entityId!!
      val e2 = op.otherEntityId!!
      val p1 = positionMapper.get(e1) ?: return
      val p2 = positionMapper.get(e2) ?: return
      val magnitude = op.magnitude!!
      val angler = Math.atan2((p2.y - p1.y).toDouble(), (p2.x - p1.x).toDouble())
      val vx = magnitude * Math.cos(angler)
      val vy = magnitude * Math.sin(angler)
      val velocity = velocityMapper.get(e1)
      velocity.vx = vx.toFloat()
      velocity.vy = vy.toFloat()
    }
  }

  override fun executorType() = SetVelocityTangentToExecutor::class.java

  var otherEntityId: Int? = null
  var magnitude: Float? = null
}
