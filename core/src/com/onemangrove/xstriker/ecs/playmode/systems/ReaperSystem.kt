package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.ecs.playmode.components.DeadComponent
import com.onemangrove.xstriker.ecs.playmode.components.ParentComponent

class ReaperSystem : IteratingSystem(Aspect.one(DeadComponent::class.java)) {
  @Wire
  private lateinit var parentMapper: ComponentMapper<ParentComponent>

  private fun reap(e: Int) {
    if (parentMapper.has(e)) {
      val parent = parentMapper.get(e)
      val children = parent.children
      children.forEach {
        reap(it)
      }
    }
    world.delete(e)
//    System.out.printf("Reaped %s\n", e)
  }

  override fun process(e: Int) {
    reap(e)
  }
}
