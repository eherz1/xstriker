package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import com.artemis.annotations.EntityId

class AnchoredComponent : Component() {
  @EntityId
  var anchoredToId: Int? = null
}
