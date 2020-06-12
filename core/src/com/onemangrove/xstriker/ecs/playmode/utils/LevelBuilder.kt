package com.onemangrove.xstriker.ecs.playmode.utils

import com.artemis.World
import com.onemangrove.xstriker.ecs.playmode.components.LevelComponent
import com.onemangrove.xstriker.ecs.playmode.operations.setLevel
import se.feomedia.orion.Operation
import java.util.*

class LevelBuilder {
  private var clock = 0f
  private var commands = LinkedList<LevelComponent.Command>()

  fun wait(time: Float): LevelBuilder {
    clock += time
    return this
  }

  fun exec(operation: Operation): LevelBuilder {
    this.commands.add(LevelComponent.Command(clock, operation))
    return this
  }

  fun build(world: World) = setLevel(this.commands)
}
