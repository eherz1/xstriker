package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import com.onemangrove.xstriker.ecs.playmode.components.ParentComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun childOf(parentId: Int, childName: String): Operation {
    val op = operation(ChildOf::class.java)
    op.parentId = parentId
    op.childName = childName
    return op
}

class ChildOf : SingleUseOperation() {

    class Executor : SingleUseOperation.SingleUseExecutor<ChildOf>() {

        @Wire private lateinit var childMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.ChildComponent>

        @Wire private lateinit var parentMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.ParentComponent>

        override fun act(op: ChildOf, node: OperationTree) {
            val childComponent = childMapper.get(op.entityId) ?: childMapper.create(op.entityId)
            val parentComponent = parentMapper.get(op.parentId!!) ?: parentMapper.create(op.parentId!!)
            childComponent.parentId = op.parentId!!
            childComponent.childName = op.childName!!
            parentComponent.children.add(op.entityId)
        }

    }

    override fun executorType() = Executor::class.java

    var parentId: Int? = null

    var childName: String? = null

}
