package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.AIComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setAI(aiType: String): Operation {
  val op = OperationFactory.operation(SetAI::class.java)
  op.aiType = aiType
  return op
}

class SetAI : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetAI>() {
    @Wire
    private lateinit var aiMapper: ComponentMapper<AIComponent>

    override fun act(op: SetAI, node: OperationTree) {
      val entityId = op.entityId!!
      val aiType = op.aiType!!
      val ai = aiMapper.get(entityId) ?: aiMapper.create(entityId)
      ai.aiType = aiType
    }
  }

  override fun executorType() = Executor::class.java

  var aiType: String? = null
}
