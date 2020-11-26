package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.ecs.playmode.components.TeamComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setTeam(name: String): Operation {
  val op = OperationFactory.operation(SetTeam::class.java)
  op.name = name
  return op
}

class SetTeam : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetTeam>() {
    @Wire
    private lateinit var teamMapper: ComponentMapper<TeamComponent>

    override fun act(op: SetTeam, node: OperationTree) {
      val teamComponent = teamMapper.create(op.entityId)
      teamComponent.name = op.name
    }
  }

  override fun executorType() = Executor::class.java

  var name: String? = null
}
