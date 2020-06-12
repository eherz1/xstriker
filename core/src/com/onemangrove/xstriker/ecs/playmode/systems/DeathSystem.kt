package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.HealthComponent
import com.onemangrove.xstriker.ecs.playmode.operations.destroy
import com.onemangrove.xstriker.ecs.playmode.operations.setAnimation
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper
import se.feomedia.orion.OperationFactory.delay
import se.feomedia.orion.OperationFactory.sequence

class DeathSystem : IteratingSystem(Aspect.one(HealthComponent::class.java)) {
  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var healthMapper: ComponentMapper<HealthComponent>

  override fun process(entityId: Int) {
    val health = healthMapper.get(entityId)
    if (health.currentHealth <= 0f) {
      operations.execute(entityId, sequence(
          setAnimation(Animations.DEATH),
          delay(.25f),
          destroy()
      ))
    }
  }
}
