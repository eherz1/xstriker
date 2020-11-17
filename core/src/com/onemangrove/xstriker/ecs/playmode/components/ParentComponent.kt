package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import com.artemis.ComponentMapper
import java.util.*

class ParentComponent : Component() {
  var children = LinkedList<Int>()

  fun findChild(childMapper: ComponentMapper<ChildComponent>, childName: String): Int? {
    var childComponent: ChildComponent? = null
    for (child in children) {
      childComponent = childMapper.get(child)
      if (childComponent != null)
        if (childName.equals(childComponent.childName))
          return child
    }
    return null
  }
}
