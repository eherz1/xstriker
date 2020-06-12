package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.onemangrove.xstriker.ecs.playmode.components.TrackingComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun trackByTag(trackedEntityTag: String): Operation {
  val op = OperationFactory.operation(TrackByTag::class.java)
  op.trackedEntityTag = trackedEntityTag
  return op
}

class TrackByTag : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<TrackByTag>() {
    @Wire
    private lateinit var tagManager: TagManager

    @Wire
    private lateinit var trackingMapper: ComponentMapper<TrackingComponent>

    override fun act(op: TrackByTag, node: OperationTree) {
      val entityId = op.entityId!!
      var tracking = trackingMapper.get(entityId) ?: trackingMapper.create(entityId)
      val trackedEntityTag = op.trackedEntityTag!!
      val trackedEntityId = tagManager.getEntityId(trackedEntityTag)
      tracking.trackedEntityId = trackedEntityId
    }
  }

  override fun executorType() = Executor::class.java

  var trackedEntityTag: String? = null
}
