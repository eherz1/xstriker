package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.ecs.playmode.components.AnchoredComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent

class AnchoringSystem : IteratingSystem(Aspect.all(AnchoredComponent::class.java)) {
  @Wire
  lateinit var anchoredMapper: ComponentMapper<AnchoredComponent>

  @Wire
  lateinit var positionMapper: ComponentMapper<PositionComponent>

  override fun process(entityId: Int) {
    val anchored = anchoredMapper.get(entityId)
    val anchoredToEntity = anchored.anchoredToId!!
    val anchoredToPosition = positionMapper.get(anchoredToEntity)
    val position = positionMapper.get(entityId)
    position.x = anchoredToPosition.x
    position.y = anchoredToPosition.y
  }
}
