package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun accelerate(dvx: Float, dvy: Float): Operation {
  val op = operation(Accelerate::class.java)
  op.dvx = dvx
  op.dvy = dvy
  return op
}

fun accelerateX(dvx: Float) = accelerate(dvx, 0f)

fun accelerateY(dvy: Float) = accelerate(0f, dvy)

class Accelerate : SingleUseOperation() {
  class AccelerateOperationExecutor : SingleUseOperation.SingleUseExecutor<Accelerate>() {
    @Wire
    private lateinit var velocityMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent>

    override fun act(op: Accelerate, node: OperationTree) {
      val velocity = velocityMapper.get(op.entityId)
      val dvx = op.dvx!!
      val dvy = op.dvy!!
      velocity.vx += dvx
      velocity.vy += dvy
    }
  }

  override fun executorType() = AccelerateOperationExecutor::class.java

  var dvx: Float? = null
  var dvy: Float? = null
}
