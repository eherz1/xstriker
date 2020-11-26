package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import com.onemangrove.xstriker.ecs.playmode.utils.isOffscreen

class MotionSystem : IteratingSystem(Aspect.all(PositionComponent::class.java, VelocityComponent::class.java)) {
  @Wire
  private lateinit var positionMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.PositionComponent>

  @Wire
  private lateinit var velocityMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent>

  @Wire
  private lateinit var mapMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.MapComponent>

  @Wire
  private lateinit var tagManager: TagManager

  @Wire
  private lateinit var cameraSystem: CameraSystem

  override fun process(e: Int) {
    val position = positionMapper.get(e)
    val velocity = velocityMapper.get(e)
    val px = position.x
    val py = position.y
    val npx = px + velocity.vx
    val npy = py + velocity.vy

    val playerE = tagManager.getEntityId("player")

    if (e == playerE) {
      System.out.printf("Is player!!")
      val mapE = tagManager.getEntity("map")
      if (mapE != null) {
        val map = mapMapper.get(mapE)
        if (map != null) {
          if (!map.wrapper!!.isOccupiable((npx / 32).toInt(), (npy / 32).toInt())) {
            velocity.vx = 0f
            velocity.vy = 0f
            return
          }
        }
      }

      val camera = cameraSystem.camera

      if (camera != null) {
        var abort = false
        val xMin = camera.position.x - camera.viewportWidth / 2
        val xMax = camera.position.x + camera.viewportWidth / 2
        val yMin = camera.position.y - camera.viewportHeight / 2
        val yMax = camera.position.y + camera.viewportHeight / 2

        if (npx > xMax) {
          position.x = xMax
          abort = true
        }

        if (npx < xMin) {
          position.x = xMin
          abort = true
        }

        if (npy > yMax) {
          position.y = yMax
          abort = true
        }

        if (npy < yMin) {
          position.y = yMin
          abort = true
        }

        if (abort) return
      }
      if (!isOffscreen(position, 1f)) {
        position.x = npx
        position.y = npy
      }
    } else {
      position.x = npx
      position.y = npy
    }
  }
}
