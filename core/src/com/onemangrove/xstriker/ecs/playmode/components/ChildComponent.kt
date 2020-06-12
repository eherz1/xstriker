package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

class ChildComponent : Component() {
  var childName: String? = null
  var parentId: Int? = null
}
