package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import com.artemis.annotations.EntityId

/**
 * Spawner S spawns spawnee T and gives it a source component holding the spawner's entity ID.
 */
class SourceComponent : Component() {
  @EntityId
  var id: Int? = null
}
