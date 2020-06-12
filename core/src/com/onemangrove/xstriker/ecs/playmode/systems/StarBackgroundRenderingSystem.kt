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
  class Star(var type: Int, var x: Int, var y: Int)

  private val STAR_SMALL = 0
  private val STAR_MEDIUM = 1
  private val STAR_LARGE = 2

  private val stars = generateStarMap()

  private val pixmap = Pixmap(Gdx.graphics.width, Gdx.graphics.height, Pixmap.Format.RGB888)
  private val batch = SpriteBatch()

  private fun generateStarMap(): List<Star> {
    val random = ThreadLocalRandom.current()
    val stars = ArrayList<Star>()
    val w = Gdx.graphics.width
    val h = Gdx.graphics.height
    for (i in 0..100) {
      val type = random.nextInt(0, 3)
      val x = random.nextInt(0, w)
      val y = random.nextInt(0, h)
      stars.add(Star(type, x, y))
    }
    return stars
  }

  private fun drawStar(star: Star, color: Int) {
    when (star.type) {
      STAR_SMALL -> {
        pixmap.drawPixel(star.x, star.y, color)
      }
      STAR_MEDIUM -> {
        pixmap.drawPixel(star.x, star.y, color)
        pixmap.drawPixel(star.x - 1, star.y, color)
        pixmap.drawPixel(star.x + 1, star.y, color)
        pixmap.drawPixel(star.x, star.y - 1, color)
        pixmap.drawPixel(star.x, star.y + 1, color)
      }
      STAR_LARGE -> {
        pixmap.drawPixel(star.x, star.y, color)
        pixmap.drawPixel(star.x - 1, star.y, color)
        pixmap.drawPixel(star.x + 1, star.y, color)
        pixmap.drawPixel(star.x, star.y - 1, color)
        pixmap.drawPixel(star.x, star.y + 1, color)
        pixmap.drawPixel(star.x + 1, star.y + 1, color)
        pixmap.drawPixel(star.x - 1, star.y - 1, color)
        pixmap.drawPixel(star.x + 1, star.y - 1, color)
        pixmap.drawPixel(star.x - 1, star.y + 1, color)
      }
    }
  }

  private fun drawStars() {
    val random = ThreadLocalRandom.current()
    var color: Int
    for (star in stars) {
      color = random.nextInt(0, 2)
      when (color) {
        0 -> drawStar(star, Color.WHITE.toIntBits())
        1 -> drawStar(star, Color.GRAY.toIntBits())
//        2 -> drawStar(star, Color.BLUE.toIntBits())
      }
    }
    val texture = Texture(pixmap)
    batch.begin()
    batch.draw(texture, 0f, 0f)
    batch.end()
  }

  override fun processSystem() {
    drawStars()
  }
}