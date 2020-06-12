package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.onemangrove.xstriker.ecs.playmode.components.LevelComponent

/**
 * Note that it's probably not the best idea to use iterating system here. We
 * must always guarantee that there is only one entity with level component.
 * A better approach will be to use BaseSystem and change level by using
 * events. I leave that work to be done at a later date.
 */
class LevelSystem : BaseSystem() {
  @Wire
  private lateinit var tagManager: TagManager

  @Wire
  private lateinit var levelMapper: ComponentMapper<LevelComponent>

  override fun processSystem() {
    val levelE = tagManager.getEntity("level") ?: return
    val level = levelMapper.get(levelE)
    level.t += Gdx.graphics.deltaTime
    while (true) {
      val next = level.commands.peek() ?: break
      if (level.t < next.t)
        break
//            println("Executing instruction at ${next.t}")
      next.op.register(world)
      level.commands.poll()
    }
  }
}
