package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent

class OverlaySystem() : BaseSystem() {
  @Wire
  private lateinit var tagManager: TagManager

  @Wire
  private lateinit var positionMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.PositionComponent>

  private val font = BitmapFont()

  private val spriteBatch = SpriteBatch()

  override fun processSystem() {
    val playerId = tagManager.getEntityId("player");
    val playerPosition = if (playerId != -1) positionMapper.get(playerId) else null
    val cameraId = tagManager.getEntityId("camera")
    val cameraPosition = if (cameraId != -1) positionMapper.get(cameraId) else null

    val playerAbsX = playerPosition?.x
    val playerAbsY = playerPosition?.y
    val playerCellX = if (playerAbsX != null) playerAbsX / 32f else null
    val playerCellY = if (playerAbsY != null) playerAbsY / 32f else null
    val cameraAbsX = cameraPosition?.x
    val cameraAbsY = cameraPosition?.y
    val cameraCellX = if (cameraAbsX != null) cameraAbsX / 32f else null
    val cameraCellY = if (cameraAbsY != null) cameraAbsY / 32f else null

    val overlay = StringBuilder()
        .append("playerAbs   ").append(playerAbsX?.toInt() ?: "-").append(", ").append(playerAbsY?.toInt()
            ?: "-").append("\n")
        .append("playerCell  ").append(playerCellX?.toInt() ?: "-").append(", ").append(playerCellY?.toInt()
            ?: "-").append("\n")
        .append("cameraAbs   ").append(cameraAbsX?.toInt() ?: "-").append(", ").append(cameraAbsY?.toInt()
            ?: "-").append("\n")
        .append("cameraCell  ").append(cameraCellX?.toInt() ?: "-").append(", ").append(cameraCellY?.toInt()
            ?: "-").append("\n")
        .toString()

    spriteBatch.begin()
    font.draw(spriteBatch, overlay, 12f, Gdx.graphics.height - 12f)
    spriteBatch.end()
  }
}
