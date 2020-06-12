package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.ecs.playmode.operations.setRotationTangentTo
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper

/**
 * Given entity E and entity F, set E's rotation equal to its angle towards entity F.
 */
class TrackingSystem : IteratingSystem(Aspect.all(com.onemangrove.xstriker.ecs.playmode.components.TrackingComponent::class.java)) {
  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var trackingMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.TrackingComponent>

  public override fun process(entityId: Int) {
    val tracking = trackingMapper.get(entityId)
    operations.execute(entityId, setRotationTangentTo(tracking.trackedEntityId!!))
  }
}
