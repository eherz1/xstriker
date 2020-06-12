package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import com.onemangrove.xstriker.ecs.playmode.components.OrbitComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun orbitParent(clockwise: Boolean): Operation {
  val op = OperationFactory.operation(OrbitParent::class.java)
  op.clockwise = clockwise
  return op
}

fun orbitParent(): Operation {
  return orbitParent(true)
}

class OrbitParent : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<OrbitParent>() {
    @Wire
    private lateinit var childMapper: ComponentMapper<ChildComponent>

    @Wire
    private lateinit var orbitMapper: ComponentMapper<OrbitComponent>

    override fun act(op: OrbitParent, node: OperationTree?) {
      val child = childMapper.get(op.entityId)
      val orbit = orbitMapper.get(op.entityId) ?: orbitMapper.create(op.entityId)
      orbit.orbitedEntityId = child.parentId!!
      orbit.clockwise = op.clockwise!!
    }
  }

  override fun executorType() = Executor::class.java

  var clockwise: Boolean? = null
}
