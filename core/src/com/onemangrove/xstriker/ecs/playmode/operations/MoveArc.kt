package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

/**
 * Move x degrees in an arc about the given center point.
 */
fun moveArc(centerX: Float, centerY: Float, degrees: Float): Operation {
  val op = OperationFactory.operation(MoveArc::class.java)
  op.centerX = centerX
  op.centerY = centerY
  op.degrees = degrees
  return op
}

class MoveArc : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<MoveArc>() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>

    override fun act(op: MoveArc, node: OperationTree) {
      val position = positionMapper.get(op.entityId)
      val x = position.x.toDouble() - op.centerX!!.toDouble()
      val y = position.y.toDouble() - op.centerY!!.toDouble()
      val radius = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0))
      val angleRad = Math.atan2(y, x)
      var angleDeg = Math.toDegrees(angleRad)
      var newAngleDeg = angleDeg + op.degrees!!
      val newAngleRad = Math.toRadians(newAngleDeg)
      position.x = (op.centerX!! + (radius * Math.cos(newAngleRad))).toFloat()
      position.y = (op.centerY!! + (radius * Math.sin(newAngleRad))).toFloat()
    }
  }

  override fun executorType() = Executor::class.java

  var centerX: Float? = null
  var centerY: Float? = null
  var degrees: Float? = null

  override fun toString() = StringBuilder()
      .append(javaClass.simpleName)
      .append("{centerX=")
      .append(centerX)
      .append(",centerY=")
      .append(centerY)
      .append("}")
      .toString()
}
