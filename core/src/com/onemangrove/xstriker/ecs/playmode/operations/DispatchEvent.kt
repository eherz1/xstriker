package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun dispatchEvent(event: Event): Operation {
    val op = OperationFactory.operation(DispatchEvent::class.java)
    op.event = event
    return op
}

class DispatchEvent : SingleUseOperation() {

    class Executor : SingleUseOperation.SingleUseExecutor<DispatchEvent>() {

        @Wire private lateinit var eventSystem: EventSystem

        override fun act(op: DispatchEvent, node: OperationTree) {
            eventSystem.dispatch(op.event!!)
        }

    }

    override fun executorType() = Executor::class.java

    var event: Event? = null

}
