package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

/**
 * Given entity E and entity F, set E's rotation equal to its angle towards entity F.
 */
class TrackingComponent : Component() {
  var trackedEntityId: Int? = null
}
