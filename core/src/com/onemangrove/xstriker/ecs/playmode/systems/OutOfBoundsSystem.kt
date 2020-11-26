package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.*
import com.onemangrove.xstriker.ecs.playmode.operations.*
import com.onemangrove.xstriker.ecs.playmode.utils.isOffscreen
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper
import se.feomedia.orion.OperationFactory

class OutOfBoundsSystem : IteratingSystem(Aspect.one(PositionComponent::class.java)) {
  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var parentMapper: ComponentMapper<ParentComponent>

  @Wire
  private lateinit var positionMapper: ComponentMapper<PositionComponent>

  @Wire
  private lateinit var dyingMapper: ComponentMapper<DyingComponent>

  private fun kill(e: Int) {
    dyingMapper.create(e)
    if (parentMapper.has(e)) {
      val parent = parentMapper.get(e)
      val children = parent.children
      children.forEach {
        kill(it)
      }
    }
    operations.execute(e, OperationFactory.sequence(
        setAnimation(Animations.DEATH),
        OperationFactory.delay(0.25f),
        setAnimation(Animations.NONE),
        markDead()
    ))
//    System.out.printf("Killed %s\n", e)
  }

  val OFF_SCREEN_BUFFER_AMOUNT = 100f;

  override fun process(e: Int) {
    val position = positionMapper.get(e)
    if (isOffscreen(position, OFF_SCREEN_BUFFER_AMOUNT)) {
//      System.out.printf("Killing off screen entity ${e}\n")
      kill(e)
    }
  }
}
