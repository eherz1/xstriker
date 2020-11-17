package com.onemangrove.xstriker.ecs.playmode.entities

import com.onemangrove.xstriker.ecs.playmode.operations.setPosition
import com.onemangrove.xstriker.ecs.playmode.operations.spawn
import ecs.playmode.entities.Enemy
import se.feomedia.orion.OperationFactory.sequence

object Wave {
  fun smartBombWaveA() = sequence(
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(100f, 600f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(100f, 500f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(100f, 400f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(100f, 300f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(100f, 200f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(800f, 600f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(800f, 500f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(800f, 400f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(800f, 300f))),
      spawn("enemy", sequence(Enemy.smartBomb(), setPosition(800f, 200f)))
  )

  fun assaultFrigateWaveA() = sequence(
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(200f, 400f))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(300f, 400f))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(400f, 400f))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(500f, 400f))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(600f, 400f))),
    spawn("enemy", sequence(Enemy.assaultFrigate(), setPosition(700f, 400f)))
  )
}