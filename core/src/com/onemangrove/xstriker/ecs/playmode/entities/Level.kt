package com.onemangrove.xstriker.ecs.playmode.entities

import com.artemis.Archetype
import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.ecs.playmode.components.LevelComponent
import com.onemangrove.xstriker.ecs.playmode.operations.*
import ecs.playmode.entities.Enemy
import ecs.playmode.entities.Player
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.delay
import se.feomedia.orion.OperationFactory.sequence

object Level {
  fun archetype(world: World): Archetype = ArchetypeBuilder()
      .add(LevelComponent::class.java)
      .build(world)

  fun default(world: World, scrollSpeed: Float): Operation = sequence(
      setTag("level"),
      spawn("map", sequence(Map.default(), setMap("maps/test-0.tmx"))),
      spawn("player", sequence(
          Player.default(),
          setPosition(32f * 16, 32f * 8))
      ),
      spawn("camera", sequence(
          Camera.default(),
//                    setPosition(32f * 16, 32f * 16),
          setPosition(32f * 15f, 32f * 32f),
          setYVelocity(scrollSpeed)
      ))
  )

  fun one(world: World): Operation = sequence(
      Level.default(world, 16f),
      delay(2f),
//      get("camera",
//          setYVelocity(8f)
//      ),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(200f, 400f))),
      delay(5f),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(200f, 400f))),
      delay(5f),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(200f, 400f))),
      delay(5f)
//      spawn("enemy", Enemy.frigate()),
//      delay(4f),
//      spawn("enemy", Enemy.frigate()),
//      delay(4f),
//      spawn("enemy", Enemy.frigate()),
//      delay(1f),
//      spawn("enemy", Enemy.frigate()),
//      delay(1f),
//      spawn("enemy", Enemy.frigate()),
//      delay(4f),
//      spawn("enemy", Enemy.frigate()),
//      delay(2f),
//      spawn("enemy", Enemy.frigate()),
//      delay(2f),
//      delay(4f),
//      get("camera", setYVelocity(8f)),
//      spawn("enemy", Enemy.frigate()),
//      delay(4f),
//      spawn("enemy", Enemy.frigate()),
//      delay(4f),
//      spawn("enemy", Enemy.frigate()),
//      delay(4f),
//      spawn("enemy", Enemy.frigate()),
//      delay(1f),
//      spawn("enemy", Enemy.frigate()),
//      delay(1f),
//      spawn("enemy", Enemy.frigate()),
//      delay(4f),
//      spawn("enemy", Enemy.frigate()),
//      delay(2f),
//      spawn("enemy", Enemy.frigate()),
//      delay(2f)
  )
}
