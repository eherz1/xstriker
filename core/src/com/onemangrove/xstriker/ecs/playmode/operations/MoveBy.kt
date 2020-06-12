package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.configure
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.TemporalOperation

fun moveBy(destination: Vector2,
           duration: Float,
           interpolation: Interpolation): Operation {
  val op = operation(MoveBy::class.java)
  configure(op, duration, interpolation)
  op.dest.set(destination)
  return op
}

class MoveBy : TemporalOperation() {
  class Executor : TemporalOperation.TemporalExecutor<MoveBy>() {
    @Wire
    private lateinit var positionMapper: ComponentMapper<PositionComponent>

    override fun begin(op: MoveBy, node: OperationTree) {
      super.begin(op, node)
      val position = positionMapper.get(op.entityId)
      op.src.set(position.x, position.y)
      op.dest.add(op.src)
    }

    override fun act(delta: Float, alpha: Float, op: MoveBy, node: OperationTree) {
      val position = positionMapper.get(op.entityId)
      val newPos = op.src.interpolate(op.dest, alpha, op.interpolation)
      println("Moving from ${position.x},${position.y} to ${newPos.x},${newPos.y}")
      position.x = newPos.x
      position.y = newPos.y
    }
  }

  override fun reset() {
    super.reset()
    src.setZero()
    dest.setZero()
  }

  override fun executorType() = Executor::class.java

  val src = Vector2()
  val dest = Vector2()
}
