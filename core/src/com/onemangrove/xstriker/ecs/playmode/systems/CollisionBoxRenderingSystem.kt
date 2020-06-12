package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.onemangrove.xstriker.ecs.playmode.components.CollidableComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent

/**
 * Debug system for checking the collision boxes of entities.
 */
class CollisionBoxRenderingSystem : IteratingSystem(Aspect.all(PositionComponent::class.java, CollidableComponent::class.java)) {
  @Wire
  private lateinit var collidableMapper: ComponentMapper<CollidableComponent>

  @Wire
  private lateinit var positionMapper: ComponentMapper<PositionComponent>

  private val shapeRenderer = ShapeRenderer()

  override fun process(e: Int) {
    val c = collidableMapper.get(e)
    val p = positionMapper.get(e)
    val r = c.collisionRectangle(p)
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    shapeRenderer.setColor(Color.YELLOW)
//    System.out.printf("%s:(%s,%s,%s,%s)\n", e, r.x, r.y, r.width, r.height)
    shapeRenderer.rect(r.x, r.y, r.width, r.height)
    shapeRenderer.end()
  }
}
