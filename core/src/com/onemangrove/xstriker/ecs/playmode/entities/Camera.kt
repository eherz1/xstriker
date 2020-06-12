package com.onemangrove.xstriker.ecs.playmode.entities

import com.artemis.Archetype
import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.ecs.playmode.components.FocusComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import com.onemangrove.xstriker.ecs.playmode.operations.setTag

object Camera {
  fun archetype(world: World): Archetype = ArchetypeBuilder()
      .add(FocusComponent::class.java)
      .add(PositionComponent::class.java)
      .add(VelocityComponent::class.java)
      .build(world)

  fun default() = setTag("camera")
}
