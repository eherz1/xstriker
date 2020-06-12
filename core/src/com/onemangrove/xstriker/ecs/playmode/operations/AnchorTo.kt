package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.AnchoredComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun anchorTo(anchoredToId: Int): Operation {
    val op = OperationFactory.operation(AnchorTo::class.java)
    op.anchoredToId = anchoredToId
    return op
}

class AnchorTo : SingleUseOperation() {

    class Executor : SingleUseOperation.SingleUseExecutor<AnchorTo>() {

        @Wire private lateinit var anchoredMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.AnchoredComponent>

        override fun act(op: AnchorTo, node: OperationTree) {
            val entityId = op.entityId!!
            val anchoredToId = op.anchoredToId!!
            val anchoredComponent = anchoredMapper.get(entityId) ?: anchoredMapper.create(entityId)
            anchoredComponent.anchoredToId = anchoredToId
        }

    }

    override fun executorType() = Executor::class.java

    var anchoredToId: Int? = null

}
