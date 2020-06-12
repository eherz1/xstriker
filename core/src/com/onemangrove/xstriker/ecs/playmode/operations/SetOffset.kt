package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.OffsetComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setOffset(offsetX: Float, offsetY: Float): Operation {
  val op = OperationFactory.operation(SetOffset::class.java)
  op.offsetX = offsetX
  op.offsetY = offsetY
  return op
}

class SetOffset : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetOffset>() {
    @Wire
    private lateinit var offsetMapper: ComponentMapper<OffsetComponent>

    override fun act(op: SetOffset, node: OperationTree) {
      val offset = offsetMapper.get(op.entityId) ?: offsetMapper.create(op.entityId)
      offset.offsetX = op.offsetX
      offset.offsetY = op.offsetY
    }
  }

  override fun executorType() = Executor::class.java

  var offsetX = 0f
  var offsetY = 0f
}
