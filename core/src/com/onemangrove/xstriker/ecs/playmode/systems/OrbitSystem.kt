package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.ecs.playmode.components.OffsetComponent
import com.onemangrove.xstriker.ecs.playmode.components.OrbitComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.operations.moveArc
import com.onemangrove.xstriker.ecs.playmode.operations.rotate
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper
import se.feomedia.orion.OperationFactory.sequence

/**
 * TODO: Set system iteration rate - correlate with orbit rate.
 */
class OrbitSystem : IteratingSystem(Aspect.one(OrbitComponent::class.java)) {
  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var orbitMapper: ComponentMapper<OrbitComponent>

  @Wire
  private lateinit var positionMapper: ComponentMapper<PositionComponent>

  @Wire
  private lateinit var offsetMapper: ComponentMapper<OffsetComponent>

  override fun process(entityId: Int) {
    val thisEntityOrbit = orbitMapper.get(entityId)!!
    val orbitedEntityPosition = positionMapper.get(thisEntityOrbit.orbitedEntityId!!)
    val degrees = if (thisEntityOrbit.clockwise) -2f else 2f
    operations.execute(entityId, sequence(
        moveArc(orbitedEntityPosition.x, orbitedEntityPosition.y, degrees),
        rotate(degrees))
    )
  }
}
