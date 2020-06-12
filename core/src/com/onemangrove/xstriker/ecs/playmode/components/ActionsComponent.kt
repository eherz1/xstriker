package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import se.feomedia.orion.Operation

class ActionsComponent : Component() {
  class Action(val frequency: Long, val operationFactory: (() -> Operation)) {
    var last = 0L
  }

  val actions = HashMap<String, ActionsComponent.Action>()
}
