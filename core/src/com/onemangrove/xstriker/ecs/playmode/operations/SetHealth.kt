package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.HealthComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setHealth(health: Float): Operation {
  val op = OperationFactory.operation(SetHealth::class.java)
  op.health = health
  return op
}

class SetHealth : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetHealth>() {
    @Wire
    private lateinit var healthMapper: ComponentMapper<HealthComponent>

    override fun act(op: SetHealth, node: OperationTree) {
      val health = healthMapper.get(op.entityId!!)
      health.currentHealth = op.health
    }
  }

  override fun executorType() = Executor::class.java

  var health = 0f
}
