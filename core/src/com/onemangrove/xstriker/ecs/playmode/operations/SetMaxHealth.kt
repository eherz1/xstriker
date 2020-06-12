package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.HealthComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setMaxHealth(maxHealth: Float): Operation {
  val op = OperationFactory.operation(SetMaxHealth::class.java)
  op.maxHealth = maxHealth
  return op
}

class SetMaxHealth : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetMaxHealth>() {

    @Wire
    private lateinit var healthMapper: ComponentMapper<HealthComponent>

    override fun act(op: SetMaxHealth, node: OperationTree) {
      val health = healthMapper.get(op.entityId!!)
      health.maximumHealth = op.maxHealth
    }
  }

  override fun executorType() = Executor::class.java

  var maxHealth = 0f
}
