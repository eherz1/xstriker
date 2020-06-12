package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.LevelComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation
import java.util.*

fun setLevel(commands: LinkedList<LevelComponent.Command>): Operation {
  val op = operation(SetLevel::class.java)
  op.commands = commands
  return op
}

class SetLevel : SingleUseOperation() {
  class Executor : SingleUseExecutor<SetLevel>() {
    @Wire
    private lateinit var levelMapper: ComponentMapper<LevelComponent>

    override fun act(op: SetLevel, node: OperationTree?) {
      val level = levelMapper.get(op.entityId) ?: levelMapper.create(op.entityId)
      level.t = 0f
      level.commands.addAll(op.commands!!)
    }
  }

  override fun executorType() = Executor::class.java

  var commands: Collection<com.onemangrove.xstriker.ecs.playmode.components.LevelComponent.Command>? = null
}
