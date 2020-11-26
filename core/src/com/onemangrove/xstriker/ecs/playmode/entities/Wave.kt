package com.onemangrove.xstriker.ecs.playmode.entities

import com.badlogic.gdx.Gdx
import com.onemangrove.xstriker.ecs.playmode.operations.setPosition
import com.onemangrove.xstriker.ecs.playmode.operations.spawn
import ecs.playmode.entities.Enemy
import se.feomedia.orion.OperationFactory.sequence

object Wave {


  fun smartBombWaveA(x: Float, y: Float) = sequence(
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(x, y))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(x - 100, y - 25))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(x + 100, y - 25))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(x - 200, y - 50))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(x + 200, y - 50)))
  )

  fun assaultFrigateWaveA(x: Float, y: Float) = sequence(
      spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(x, y))),
      spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(x - 100, y))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(x + 100, y))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(x - 200, y))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(x + 200, y)))
//    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(x - 300, y))),
//    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(x + 300, y)))
  )
}