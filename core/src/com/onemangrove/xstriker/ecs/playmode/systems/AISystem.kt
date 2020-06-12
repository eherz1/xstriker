package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.artemis.systems.IteratingSystem
import com.onemangrove.xstriker.ecs.playmode.components.AIComponent
import com.onemangrove.xstriker.ecs.playmode.operations.applyChild
import com.onemangrove.xstriker.ecs.playmode.operations.invokeAction
import com.onemangrove.xstriker.ecs.playmode.operations.setVelocityTangentTo
import com.onemangrove.xstriker.ecs.playmode.operations.setVelocityTowards
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper
import se.feomedia.orion.Operation

class AISystem : IteratingSystem(Aspect.all(AIComponent::class.java)) {
  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var aiMapper: ComponentMapper<AIComponent>

  @Wire
  private lateinit var tagManager: TagManager

  private val timerMap = hashMapOf<Int, Long>()

  fun frigateAI(e: Int) {
    val now = System.currentTimeMillis()
    val time = timerMap.get(e)
    if (time == null) {
      timerMap.put(e, now)
      return
    }
    if (now - time > 1000) {
      operations.execute(e, applyChild("railGun", invokeAction("fire")))
      timerMap.put(e, now)
    }
  }

  fun smartBombAI(e: Int) {
    val playerId = tagManager.getEntityId("player")
    if (playerId < 0) return
    operations.execute(e, setVelocityTowards(playerId, 2f))
  }

  override fun process(e: Int) {
    val ai = aiMapper.get(e)
    when (ai.aiType) {
      "frigate" -> frigateAI(e)
      "smartBomb" -> smartBombAI(e)
    }
  }
}