package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.onemangrove.xstriker.Animations

class RenderableComponent : Component() {
  class AnimationEntry(val animation: Animation<*>, var stateTime: Float = 0f, var looping: Boolean = true)

  var animationMap = HashMap<Animations, AnimationEntry>()
  var activeAnimationKey = Animations.NONE
  var spriteBatch: SpriteBatch = SpriteBatch()
}
