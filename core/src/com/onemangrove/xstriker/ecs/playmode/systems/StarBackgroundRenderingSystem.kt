package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch


class StarBackgroundRenderingSystem : BaseSystem() {
  val NUM_STARS = 100 * 100

  enum class StarType(val value: Int) {
    SMALL(0),
    LARGE(1)
  }

  fun starForValue(value: Int): StarType {
    if (value < 1)
      return StarType.SMALL
    return StarType.LARGE
  }

  fun randomStarType(): StarType = starForValue(
      ThreadLocalRandom.current().nextInt(StarType.SMALL.value, StarType.LARGE.value + 1)
  )

  class Star(var type: StarType, var x: Int, var y: Int)

  fun randomStar(): Star = Star(
      randomStarType(),
      ThreadLocalRandom.current().nextInt(0, Gdx.graphics.width),
      ThreadLocalRandom.current().nextInt(0, Gdx.graphics.height * 100)
  )

  private val stars = generateStarMap()
  private val batch = SpriteBatch()
  private var texture = computeTexture(0, 0)

  private fun generateStarMap(): List<Star> {
    val stars = ArrayList<Star>(NUM_STARS)
    for (i in 0..NUM_STARS) {
      stars.add(randomStar())
    }
    return stars
  }

  private fun drawStar(pixmap: Pixmap, star: Star, color: Int, offsetX: Int, offsetY: Int) {
    val x = star.x + offsetX
    val y = star.y + offsetY
    when (star.type) {
      StarType.SMALL -> {
        pixmap.drawPixel(x, y, color)
      }
      StarType.LARGE -> {
        pixmap.drawPixel(x, y, color)
        pixmap.drawPixel(x - 1, y, color)
        pixmap.drawPixel(x + 1, y, color)
        pixmap.drawPixel(x, y - 1, color)
        pixmap.drawPixel(x, y + 1, color)
      }
    }
  }

  private fun drawStars(pixmap: Pixmap, offsetX: Int, offsetY: Int) {
    val random = ThreadLocalRandom.current()
    var color: Int
    for (star in stars) {
      color = random.nextInt(0, 2)
      when (color) {
        0 -> drawStar(pixmap, star, Color.argb8888(1f, 1f, 1f, 1f), offsetX, offsetY)
        1 -> drawStar(pixmap, star, Color.argb8888(1f, 0.9f, 0.9f, 0.9f), offsetX, offsetY)
      }
    }
  }

  fun computeTexture(offsetX: Int, offsetY: Int): Texture {
    val pixmap = Pixmap(Gdx.graphics.width, Gdx.graphics.height, Pixmap.Format.RGB888)
    drawStars(pixmap, offsetX, offsetY)
    return Texture(pixmap)
  }

  fun renderTexture() {
    batch.begin()
    batch.draw(texture, 0f, 0f)
//    batch.draw(texture, xPos, yPos++)
    batch.end()
  }

  var frameCounter = 0
  val frameDivisor = 1
  var xPos = 0
  var yPos = 0

  override fun processSystem() {
    frameCounter += 1
    if (frameCounter > frameDivisor) {
      texture = computeTexture(xPos, yPos)
      frameCounter = 0
    }
    yPos -= 8
    renderTexture()
  }
}