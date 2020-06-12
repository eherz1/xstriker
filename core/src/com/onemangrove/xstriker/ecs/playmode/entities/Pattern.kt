package com.onemangrove.xstriker.ecs.playmode.entities

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.onemangrove.xstriker.ecs.playmode.operations.moveBy
import com.onemangrove.xstriker.ecs.playmode.operations.moveCosine
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.sequence

object Pattern {
  fun downwardSine(): Operation = moveCosine(2f, Interpolation.linear, 2f)

  fun downwardSine2(): Operation = sequence(
      moveBy(Vector2(200f, -200f), 6f, Interpolation.linear)
//            delay(6f),
//            moveBy(Vector2(-400f, -200f), 6f, Interpolation.sine)
  )

}
