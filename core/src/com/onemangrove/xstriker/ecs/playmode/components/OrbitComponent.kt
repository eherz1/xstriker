package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

class OrbitComponent : Component() {
  var orbitedEntityId: Int? = null
  var clockwise: Boolean = false
  var angleRadians = 0f
  // TODO: Decide on how to do speed
  var orbitalSpeed = 0f
}
