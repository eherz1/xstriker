package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.TrackingComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun track(trackedEntityId: Int): Operation {
  val op = OperationFactory.operation(Track::class.java)
  op.trackedEntityId = trackedEntityId
  return op
}

class Track : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<Track>() {
    @Wire
    private lateinit var trackingMapper: ComponentMapper<TrackingComponent>

    override fun act(op: Track, node: OperationTree) {
      val entityId = op.entityId!!
      val trackedEntityId = op.trackedEntityId!!
      var tracking = trackingMapper.get(entityId)
      if (tracking == null) tracking = trackingMapper.create(entityId)
      tracking.trackedEntityId = trackedEntityId
    }
  }

  override fun executorType() = Executor::class.java

  var trackedEntityId: Int? = null
}
