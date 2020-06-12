package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

/**
 * Apply an offset when considering the true position of an entity from its origin, represented by its position.
 * This offset is the length l in the equation by r = l * cos(theta)
 */
class OffsetComponent : Component() {
  var offsetX = 0f
  var offsetY = 0f
}
