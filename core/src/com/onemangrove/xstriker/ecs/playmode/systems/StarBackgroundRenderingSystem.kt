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
  val NUM_STARS = 100

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
      ThreadLocalRandom.current().nextInt(0, Gdx.graphics.height)
  )

  private val stars = generateStarMap()
  private val batch = SpriteBatch()
  private var texture = computeTexture()

  private fun generateStarMap(): List<Star> {
    val stars = ArrayList<Star>(NUM_STARS)
    for (i in 0..NUM_STARS) {
      stars.add(randomStar())
    }
    return stars
  }

  private fun drawStar(pixmap: Pixmap, star: Star, color: Int) {
    when (star.type) {
      StarType.SMALL -> {
        pixmap.drawPixel(star.x, star.y, color)
      }
      StarType.LARGE -> {
        pixmap.drawPixel(star.x, star.y, color)
        pixmap.drawPixel(star.x - 1, star.y, color)
        pixmap.drawPixel(star.x + 1, star.y, color)
        pixmap.drawPixel(star.x, star.y - 1, color)
        pixmap.drawPixel(star.x, star.y + 1, color)
      }
    }
  }

  private fun drawStars(pixmap: Pixmap) {
    val random = ThreadLocalRandom.current()
    var color: Int
    for (star in stars) {
      color = random.nextInt(0, 2)
      when (color) {
        0 -> drawStar(pixmap, star, Color.argb8888(1f, 1f, 1f, 1f))
        1 -> drawStar(pixmap, star, Color.argb8888(1f, 0.9f, 0.9f, 0.9f))
      }
    }
  }

  fun computeTexture(): Texture {
    val pixmap = Pixmap(Gdx.graphics.width, Gdx.graphics.height, Pixmap.Format.RGB888)
    drawStars(pixmap)
    return Texture(pixmap)
  }

  fun renderTexture() {
    batch.begin()
    batch.draw(texture, 0f, 0f)
    batch.end()
  }

  var frameCounter = 0
  val frameDivisor = 8

  override fun processSystem() {
    frameCounter += 1
    if (frameCounter > frameDivisor) {
      texture = computeTexture()
      frameCounter = 0
    }
    renderTexture()
  }
}