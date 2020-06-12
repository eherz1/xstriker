package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import com.badlogic.gdx.math.Rectangle

class CollidableComponent : Component() {
  var bodyWidth: Float = 0f
  var bodyHeight: Float = 0f
  var collisionAction = "applyDamage"
  var collisionDamage = 1f
  var isBullet = false

  fun collisionRectangle(p: PositionComponent): Rectangle = Rectangle(
      p.x + (32 - this.bodyWidth) / 2,
      p.y + (32 - this.bodyHeight) / 2,
      this.bodyWidth,
      this.bodyHeight
  )
}
