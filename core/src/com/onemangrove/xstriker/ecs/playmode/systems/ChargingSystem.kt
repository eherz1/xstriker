package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.ChargeableComponent
import com.onemangrove.xstriker.ecs.playmode.components.RenderableComponent
import com.onemangrove.xstriker.ecs.playmode.operations.setAnimation
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper

class ChargingSystem : IteratingSystem(Aspect.one(ChargeableComponent::class.java)) {
  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var chargeableMapper: ComponentMapper<ChargeableComponent>

  @Wire
  private lateinit var renderableMapper: ComponentMapper<RenderableComponent>

  private val animations = arrayOf<Animations>(
      Animations.FIRING_LOW,
      Animations.FIRING_MEDIUM,
      Animations.FIRING_HIGH)

  override fun process(entityId: Int) {
    val chargeableComponent = chargeableMapper.get(entityId)
    val renderableComponent = renderableMapper.get(entityId)
    val animation = animations[(chargeableComponent.percent * 100f / 33f).toInt()]
    if (chargeableComponent.charging && chargeableComponent.percent < 1f) {
      chargeableComponent.percent += chargeableComponent.rate
      if (renderableComponent != null)
        operations.execute(entityId, setAnimation(animation))
    } else {
      chargeableComponent.percent = 0f
    }
  }
}