package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.Entity
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import com.onemangrove.xstriker.ecs.playmode.components.ParentComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun addChild(childName: String, child: Entity): Operation {
    val op = OperationFactory.operation(AddChild::class.java)
    op.childName = childName
    op.childId = child.id
    return op
}

fun addChild(childName: String, childId: Int): Operation {
    val op = OperationFactory.operation(AddChild::class.java)
    op.childName = childName
    op.childId = childId
    return op
}

class AddChild : SingleUseOperation() {

    class Executor : SingleUseOperation.SingleUseExecutor<AddChild>() {

        @Wire private lateinit var childMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.ChildComponent>

        @Wire private lateinit var parentMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.ParentComponent>

        override fun act(op: AddChild, node: OperationTree) {
            val entityId = op.entityId
            val childId = op.childId!!
            val childName = op.childName!!
            val parentComponent = parentMapper.get(entityId) ?: parentMapper.create(entityId)
            parentComponent.children.add(childId)
            val childComponent = childMapper.get(childId!!) ?: childMapper.create(op.childId!!)
            childComponent.childName = op.childName
            childComponent.parentId = op.entityId
        }
    }

    override fun executorType() = Executor::class.java

    var childId: Int? = null

    var childName: String? = null

}
