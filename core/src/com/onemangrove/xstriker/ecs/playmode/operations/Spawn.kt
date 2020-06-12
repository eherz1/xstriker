package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ArchetypeManager
import com.onemangrove.xstriker.ecs.playmode.components.SourceComponent
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun spawn(archetypeName: String, initializer: Operation?): Operation {
  val op = OperationFactory.operation(Spawn::class.java)
  op.archetypeName = archetypeName
  op.initializer = initializer
  return op
}

class Spawn : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<Spawn>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    @Wire
    private lateinit var archetypeManager: ArchetypeManager

    @Wire
    private lateinit var sourceMapper: ComponentMapper<SourceComponent>

    override fun act(op: Spawn, node: OperationTree) {
      val world = worldHolder.world
      val archetype = archetypeManager.get(op.archetypeName!!)
      val entityId = world.create(archetype)
      val source = sourceMapper.create(entityId)
      source.id = op.entityId
      if (op.initializer == null) return
      op.initializer!!.register(world, entityId)
    }
  }

  override fun executorType() = Executor::class.java

  var archetypeName: String? = null
  var initializer: Operation? = null
}
