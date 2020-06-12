package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Renderable
import com.badlogic.gdx.utils.Array
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.RenderableComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun assignAnimation(animation: Animations,
                    resource: String,
                    duration: Float,
                    frames: Collection<AssignAnimation.Frame>,
                    looping: Boolean = true): Operation {
  val op = OperationFactory.operation(AssignAnimation::class.java)
  op.resource = resource
  op.animation = animation
  op.duration = duration
  op.frames = frames
  op.looping = looping
  return op
}

class AssignAnimation : SingleUseOperation() {
  class Frame(var x: Int = 0, var y: Int = 0, var w: Int = 0, var h: Int = 0)

  class Executor : SingleUseOperation.SingleUseExecutor<AssignAnimation>() {
    @Wire
    private lateinit var assetManager: AssetManager

    @Wire
    private lateinit var renderableMapper: ComponentMapper<RenderableComponent>

    override fun act(op: AssignAnimation, node: OperationTree) {
      val renderable = renderableMapper.get(op.entityId)
      val spritesheet = assetManager.get(op.resource!!, Texture::class.java)
      val textures = op.frames!!.map { TextureRegion(spritesheet, it.x, it.y, it.w, it.h) }
      val animation = Animation<TextureRegion>(op.duration, Array<TextureRegion>(textures.toTypedArray()))
      val looping = op.looping!!
      val animationEntry = RenderableComponent.AnimationEntry(animation, 0f, looping)
      renderable.animationMap.put(op.animation!!, animationEntry)
    }
  }

  override fun executorType() = Executor::class.java

  var animation: Animations? = null
  var duration = 0f
  var resource: String? = null
  var frames: Collection<Frame>? = null
  var looping: Boolean? = null
}
