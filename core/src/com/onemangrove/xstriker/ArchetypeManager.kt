package com.onemangrove.xstriker

import com.artemis.Archetype
import com.artemis.World
import com.onemangrove.xstriker.ecs.playmode.entities.*
import ecs.playmode.entities.Bullet
import ecs.playmode.entities.Enemy
import ecs.playmode.entities.Gun
import ecs.playmode.entities.Player

class ArchetypeManager {
  lateinit var world: World

  val archetypes = HashMap<String, Archetype>()

  fun load() {
    archetypes.put("bullet", Bullet.archetype(world))
    archetypes.put("camera", Camera.archetype(world))
    archetypes.put("gun", Gun.archetype(world))
    archetypes.put("level", Level.archetype(world))
    archetypes.put("map", com.onemangrove.xstriker.ecs.playmode.entities.Map.archetype(world))
    archetypes.put("player", Player.archetype(world))
    archetypes.put("thruster", Thruster.archetype(world))
    archetypes.put("enemy", Enemy.archetype(world))
  }

  fun unload() {}

  fun load(name: String) {}

  fun unload(name: String) {}

  fun get(name: String) = archetypes.get(name)
}
