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
      //      get("camera",
      //          setYVelocity(8f)
      //      ),
  )

  fun one(world: World): Operation = sequence(
      Level.default(world, 0f),
      delay(5f),
      Wave.assaultFrigateWaveA(),
      delay(10f),
      Wave.assaultFrigateWaveA(),
      delay(10f),
      Wave.assaultFrigateWaveA(),
      delay(5f)
    )
}
