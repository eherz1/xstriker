package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.RenderableComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setAnimation(animation: Animations): Operation {
  val op = OperationFactory.operation(SetAnimation::class.java)
  op.animation = animation
  return op
}

class SetAnimation : SingleUseOperation() {
  class Executor : SingleUseOperation.SingleUseExecutor<SetAnimation>() {
    @Wire
    private lateinit var renderableMapper: ComponentMapper<RenderableComponent>

    override fun act(op: SetAnimation, node: OperationTree) {
      val entityId = op.entityId
      val animation = op.animation!!
      val renderable = renderableMapper.get(entityId)
      renderable.activeAnimationKey = animation
    }
  }

  override fun executorType() = Executor::class.java

  var animation: com.onemangrove.xstriker.Animations? = null
}
