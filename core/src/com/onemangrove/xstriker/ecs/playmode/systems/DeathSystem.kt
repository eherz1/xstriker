package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import com.onemangrove.xstriker.ecs.playmode.components.DyingComponent
import com.onemangrove.xstriker.ecs.playmode.components.HealthComponent
import com.onemangrove.xstriker.ecs.playmode.components.ParentComponent
import com.onemangrove.xstriker.ecs.playmode.operations.*
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationFactory.delay
import se.feomedia.orion.OperationFactory.sequence

class DeathSystem : IteratingSystem(Aspect.one(HealthComponent::class.java)) {
  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var healthMapper: ComponentMapper<HealthComponent>

  @Wire
  private lateinit var parentMapper: ComponentMapper<ParentComponent>

  @Wire
  private lateinit var dyingMapper: ComponentMapper<DyingComponent>

  private fun kill(e: Int) {
    dyingMapper.create(e)
    if (parentMapper.has(e)) {
      val parent = parentMapper.get(e)
      val children = parent.children
      children.forEach {
        kill(it)
      }
    }
    operations.execute(e, sequence(
        setAnimation(Animations.DEATH),
        OperationFactory.delay(0.25f),
        setAnimation(Animations.NONE),
        markDead()
    ))
//    System.out.printf("Killed %s\n", e)
  }

  override fun process(e: Int) {
    val health = healthMapper.get(e)
    if (health.currentHealth > 0f || dyingMapper.has(e)) return
    kill(e)
  }
}
