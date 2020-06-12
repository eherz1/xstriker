package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ActionsComponent
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun invokeAction(name: String): Operation {
  val op = OperationFactory.operation(InvokeAction::class.java)
  op.name = name
  return op
}

class InvokeAction : SingleUseOperation() {
  class Executor : SingleUseExecutor<InvokeAction>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    @Wire
    private lateinit var actionsMapper: ComponentMapper<ActionsComponent>

    override fun act(operation: InvokeAction, node: OperationTree?) {
      val actions = actionsMapper.get(operation.entityId)
      val action = actions.actions.get(operation.name)
      val now = System.currentTimeMillis()
      if (now - action!!.last < action!!.frequency) return
      val actionOperation = action.operationFactory()
      actionOperation.register(worldHolder.world, operation.entityId)
      action.last = now
    }
  }

  override fun executorType() = Executor::class.java

  var name: String? = null
}
