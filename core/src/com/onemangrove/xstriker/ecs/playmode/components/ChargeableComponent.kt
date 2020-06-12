package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

class ChargeableComponent : Component() {
  var charging: Boolean = false
  var rate: Float = 0.001f
  var percent: Float = 0f
}
