package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.onemangrove.xstriker.ecs.playmode.components.FocusComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent

class CameraSystem : IteratingSystem(Aspect.all(FocusComponent::class.java, PositionComponent::class.java)) {
  val camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

  @Wire
  lateinit var positionComponentMapper: ComponentMapper<PositionComponent>

  override fun process(entityId: Int) {
    val positionComponent = positionComponentMapper!!.get(entityId)
    camera.position.set(positionComponent.x, positionComponent.y, 0f)
    camera.update()
  }
}
