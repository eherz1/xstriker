package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setVelocity(vx: Float?, vy: Float?): Operation {
  val op = OperationFactory.operation(SetVelocity::class.java)
  op.vx = vx
  op.vy = vy
  return op
}

fun setXVelocity(vx: Float) = setVelocity(vx, null)

fun setYVelocity(vy: Float) = setVelocity(null, vy)

class SetVelocity : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetVelocity>() {
    @Wire
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun act(op: SetVelocity, node: OperationTree) {
      val velocity = velocityMapper.get(op.entityId)
      velocity.vx = op.vx ?: velocity.vx
      velocity.vy = op.vy ?: velocity.vy
    }
  }

  override fun executorType() = Executor::class.java

  var vx: Float? = null
  var vy: Float? = null
}
