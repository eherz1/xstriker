package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.math.Interpolation
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationFactory.configure
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.TemporalOperation

/**
 * Move x degrees in an arc about the given center point.
 */
fun moveCosine(duration: Float, interpolation: Interpolation, magnitude: Float): Operation {
  val op = OperationFactory.operation(MoveCosine::class.java)
  configure(op, duration, interpolation)
  op.magnitude = magnitude
  return op
}

class MoveCosine : TemporalOperation() {

  class Executor : TemporalOperation.TemporalExecutor<MoveCosine>() {

    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>

    override fun act(delta: Float, alpha: Float, op: MoveCosine, node: OperationTree) {
      val position = positionMapper.get(op.entityId)
      val magnitude = op.magnitude
      val dx = magnitude * Math.cos(op.acc.toDouble()).toFloat()
      val dy = magnitude
      val newX = position.x + dx
      val newY = position.y - dy
      position.x = newX
      position.y = newY
    }
  }

  override fun executorType() = Executor::class.java

  var magnitude = 1f
}
