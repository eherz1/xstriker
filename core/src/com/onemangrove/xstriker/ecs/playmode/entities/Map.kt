package com.onemangrove.xstriker.ecs.playmode.entities

import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.ecs.playmode.components.MapComponent
import com.onemangrove.xstriker.ecs.playmode.operations.setTag

object Map {
  fun archetype(world: World) = ArchetypeBuilder()
      .add(MapComponent::class.java)
      .build(world)

  fun default() = setTag("map")
}
