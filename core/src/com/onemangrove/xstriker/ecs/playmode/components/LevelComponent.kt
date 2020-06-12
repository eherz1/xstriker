package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import se.feomedia.orion.Operation
import java.util.*

class LevelComponent : Component() {

    class Command(val t: Float, val op: Operation)

    var t = 0f

    var commands = LinkedList<com.onemangrove.xstriker.ecs.playmode.components.LevelComponent.Command>()

}
