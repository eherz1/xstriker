package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

class HealthComponent : Component() {
  var currentHealth: Float = Float.MAX_VALUE
  var maximumHealth: Float = Float.MAX_VALUE
}
