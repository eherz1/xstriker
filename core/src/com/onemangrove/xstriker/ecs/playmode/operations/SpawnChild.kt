package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.ChildComponent
import com.onemangrove.xstriker.ecs.playmode.components.ParentComponent
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun spawnChild(childName: String, archetypeName: String, initializer: Operation?): Operation {
  val op = OperationFactory.operation(SpawnChild::class.java)
  op.childName = childName
  op.archetypeName = archetypeName
  op.initializer = initializer
  return op
}

class SpawnChild : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SpawnChild>() {
    @Wire
    private lateinit var worldHolder: WorldHolder

    @Wire
    private lateinit var archetypeManager: com.onemangrove.xstriker.ArchetypeManager

    @Wire
    private lateinit var parentMapper: ComponentMapper<ParentComponent>

    @Wire
    private lateinit var childMapper: ComponentMapper<ChildComponent>

    override fun act(op: SpawnChild, node: OperationTree) {
      val world = worldHolder.world
      val archetype = archetypeManager.get(op.archetypeName!!)!!
      val spawnedId = world.create(archetype)
      val spawnedChild = childMapper.get(spawnedId) ?: childMapper.create(spawnedId)
      spawnedChild.parentId = op.entityId
      spawnedChild.childName = op.childName
      val parent = parentMapper.create(op.entityId)
      parent.children.add(spawnedId)
      val initializer = op.initializer ?: return
      initializer.register(world, spawnedId)
    }
  }

  override fun executorType() = Executor::class.java

  var childName: String? = null
  var archetypeName: String? = null
  var initializer: Operation? = null
}
