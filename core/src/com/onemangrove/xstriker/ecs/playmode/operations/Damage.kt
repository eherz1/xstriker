package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.onemangrove.xstriker.ecs.playmode.components.HealthComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun damage(damage: Float): Operation {
  val op = operation(Damage::class.java)
  op.damage = damage
  return op
}

class Damage : SingleUseOperation() {
  class DamageExecutor : SingleUseOperation.SingleUseExecutor<Damage>() {
    @Wire
    private lateinit var healthMapper: ComponentMapper<HealthComponent>

    override fun act(op: Damage, node: OperationTree) {
      var healthComponent = healthMapper!!.get(op.entityId)
      healthComponent.currentHealth -= op.damage!!
      if (healthComponent.currentHealth < 0f) {
        healthComponent.currentHealth = 0f
      }
    }
  }

  override fun executorType(): Class<DamageExecutor> = DamageExecutor::class.java

  var damage: Float? = null
}
