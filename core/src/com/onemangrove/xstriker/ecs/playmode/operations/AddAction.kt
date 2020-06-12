package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ActionsComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun addAction(name: String, action: com.onemangrove.xstriker.ecs.playmode.components.ActionsComponent.Action): Operation {
    val op = operation(AddAction::class.java)
    op.name = name
    op.action = action
    return op
}

class AddAction : SingleUseOperation() {

    class Executor : SingleUseOperation.SingleUseExecutor<AddAction>() {

        @Wire private lateinit var actionsMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.ActionsComponent>

        override fun act(op: AddAction, node: OperationTree) {
            var actions = actionsMapper.get(op.entityId) ?: actionsMapper.create(op.entityId)
            actions.actions.put(op.name!!, op.action!!)
        }
    }

    override fun executorType() = Executor::class.java

    var name: String? = null

    var action: com.onemangrove.xstriker.ecs.playmode.components.ActionsComponent.Action? = null

}
