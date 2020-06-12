package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.OffsetComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.RenderableComponent
import com.onemangrove.xstriker.ecs.playmode.components.RotationComponent

class RenderingSystem : IteratingSystem(Aspect.all(PositionComponent::class.java, RenderableComponent::class.java)) {
  @Wire
  private lateinit var cameraSystem: CameraSystem

  @Wire
  private lateinit var renderableComponentMapper: ComponentMapper<RenderableComponent>

  @Wire
  private lateinit var positionComponentMapper: ComponentMapper<PositionComponent>

  @Wire
  private lateinit var rotationComponentMapper: ComponentMapper<RotationComponent>

  @Wire
  private lateinit var offsetComponentMapper: ComponentMapper<OffsetComponent>

  public override fun process(entityId: Int) {
    val renderable = renderableComponentMapper.get(entityId)
    val position = positionComponentMapper.get(entityId)
    val offset = offsetComponentMapper.get(entityId)
    val rotation = rotationComponentMapper.get(entityId)

    if (renderable.activeAnimationKey == Animations.NONE) return

    val animationEntry = renderable.animationMap.get(renderable.activeAnimationKey) ?: return

    animationEntry.stateTime += Gdx.graphics.deltaTime

    val animation = animationEntry.animation
    val stateTime = animationEntry.stateTime
    val looping = animationEntry.looping
    val frame = animation.getKeyFrame(stateTime, looping) as TextureRegion

    var offsetX = 0f
    var offsetY = 0f

    if (offset != null) {
      offsetX = offset.offsetX
      offsetY = offset.offsetY
    }

    var degrees = 90f

    if (rotation != null) {
      degrees = rotation.rotation
    }

    val originX = frame.regionWidth / 2f
    val originY = frame.regionHeight / 2f

    val x = position.x + offsetX
    val y = position.y + offsetY

//    val camera = cameraSystem.camera

    val spriteBatch = renderable.spriteBatch
    spriteBatch.begin()
//        spriteBatch.setProjectionMatrix(camera.combined)
    spriteBatch.draw(
        frame,
        x,
        y,
        originX,
        originY,
        frame.regionWidth.toFloat(),
        frame.regionHeight.toFloat(),
        1f,
        1f,
        degrees,
        true)
    spriteBatch.end()
  }
}
