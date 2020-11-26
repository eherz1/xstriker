package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.onemangrove.xstriker.ecs.playmode.components.*
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper

class CollisionSystem : IteratingSystem(Aspect.all(PositionComponent::class.java, CollidableComponent::class.java)) {
  @Wire
  private lateinit var tagManager: TagManager

  @Wire
  private lateinit var collidableMapper: ComponentMapper<CollidableComponent>

  @Wire
  private lateinit var positionMapper: ComponentMapper<PositionComponent>

  @Wire
  private lateinit var childMapper: ComponentMapper<ChildComponent>

  @Wire
  private lateinit var sourceMapper: ComponentMapper<SourceComponent>

  @Wire
  private lateinit var healthMapper: ComponentMapper<HealthComponent>

  @Wire
  private lateinit var teamMapper: ComponentMapper<TeamComponent>

  private fun damage(e: Int, d: Float) {
    var healthComponent = healthMapper.get(e)
    healthComponent.currentHealth -= d
    if (healthComponent.currentHealth < 0f) {
      healthComponent.currentHealth = 0f
    }
  }

  private fun collide(e: Int, c: CollidableComponent) {
    when (c.collisionAction) {
      "applyDamage" -> {
        damage(e, c.collisionDamage)
      }
    }
  }

  /**
   * Don't collide the same entity with itself.
   */
  private fun isCollideWithSelf(e1: Int, e2: Int): Boolean {
    return e1 == e2
  }

  /**
   * Don't collide bullets with the person firing them, for instance.
   */
  private fun isCollideWithSource(e1: Int, e2: Int): Boolean {
    val source1 = sourceMapper.get(e1)
    val source2 = sourceMapper.get(e2)
    val collide1 = source1 != null && source1.id == e2
    val collide2 = source2 != null && source2.id == e1
    return collide1 || collide2
  }

  /**
   * Don't collide with the parent objects of the source, either.
   */
  private fun isCollideWithSourceParent(root0: Int, root1: Int, source1: Int?, source2: Int?): Boolean {
    var child0 = if (source1 != null && source1 > -1) childMapper.get(source1) else null
    var child1 = if (source2 != null && source2 > -1) childMapper.get(source2) else null
    if (child0 == null && child1 == null) return false
    var parent0 = child0?.parentId
    var parent1 = child1?.parentId
    val collide1 = root0 == parent1
    val collide2 = root1 == parent0
    return collide1 || collide2 || isCollideWithSourceParent(root0, root1, parent0, parent1)
  }

  /**
   * Don't collide objects on the same team.
   */
  private fun isCollideWithTeam(e1: Int, e2: Int): Boolean {
    val t1 = teamMapper.get(e1) ?: return false
    val t2 = teamMapper.get(e2) ?: return false
    return t1.name === t2.name
  }

  /**
   * Don't collide with the parent objects of the source, either.
   */
  private fun isCollideWithSourceParent(e1: Int, e2: Int): Boolean {
    val source1 = sourceMapper.get(e1)
    val source2 = sourceMapper.get(e2)
    return isCollideWithSourceParent(e1, e2, source1?.id, source2?.id)
  }

  override fun process(e1: Int) {
    for (e2 in this.entityIds.data) {
      val t1 = tagManager.getTag(e1) ?: ""
      val t2 = tagManager.getTag(e2) ?: ""

      if (isCollideWithSelf(e1, e2) ||
          isCollideWithSource(e1, e2) ||
          isCollideWithSourceParent(e1, e2) ||
          isCollideWithTeam(e1, e2))
        continue

      val h1 = healthMapper.get(e1)
      val h2 = healthMapper.get(e2)

      // Don't collide dying entities
      if ((h1 != null && h1.currentHealth <= 0f) || (h2 != null && h2.currentHealth <= 0f)) continue

      val c1 = collidableMapper.get(e1) ?: continue
      val c2 = collidableMapper.get(e2) ?: continue

      // Don't collide bullets with other bullets
      if (c1.isBullet && c2.isBullet) return

      val p1 = positionMapper.get(e1) ?: continue
      val p2 = positionMapper.get(e2) ?: continue
      val r1 = c1.collisionRectangle(p1)
      val r2 = c2.collisionRectangle(p2)

//      System.out.printf("Colliding %s:%s and %s:%s\n",
//          tagManager.getTag(e1),
//          e1,
//          tagManager.getTag(e2),
//          e2
//      )

      // Bail out if there's no collision
      if (!Intersector.intersectRectangles(r1, r2, Rectangle())) continue

      collide(e1, c2)
      collide(e2, c1)
    }
  }
}
