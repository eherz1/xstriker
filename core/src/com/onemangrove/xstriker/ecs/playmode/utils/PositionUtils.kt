package com.onemangrove.xstriker.ecs.playmode.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent

fun isOffscreen(position: PositionComponent, tolerance: Float): Boolean {
  return isOffscreen(position.x, position.y, tolerance)
}

fun isOffscreen(position: PositionComponent): Boolean {
  return isOffscreen(position, 0f)
}

fun isOffscreen(x: Float, y: Float, tolerance: Float): Boolean {
  return x > Gdx.graphics.width + tolerance || x < 0 - tolerance || y > Gdx.graphics.height + tolerance || y < 0 - tolerance
}

fun isOffscreen(x: Float, y: Float): Boolean {
  return isOffscreen(x, y, 0f)
}
