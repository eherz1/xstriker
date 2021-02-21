package com.onemangrove.xstriker.ecs.playmode.entities

import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.ActionsComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.RenderableComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import com.onemangrove.xstriker.ecs.playmode.operations.*
import se.feomedia.orion.OperationFactory.sequence

object Thruster {
  fun archetype(world: World) = ArchetypeBuilder()
      .add(RenderableComponent::class.java)
      .add(PositionComponent::class.java)
      .add(VelocityComponent::class.java)
      .build(world)

//  fun default(offsetX: Float,
//              offsetY: Float,
//              rotation: Float,
//              thrustX: Float,
//              thrustY: Float) = sequence(
//      anchorToParent(),
//      setOffset(offsetX, offsetY),
//      assignAnimation(Animations.FIRING_LOW, "spritesheets/main.png", 0.025f, arrayListOf(
//          AssignAnimation.Frame(32 * 1, 0, 32, 32),
//          AssignAnimation.Frame(32 * 2, 0, 32, 32))
//      ),
//      setRotation(rotation),
//      addAction("engage", ActionsComponent.Action(0L) {
//        sequence(
//            setAnimation(Animations.FIRING_LOW),
//            applyParent(move(thrustX, thrustY))
//        )
//      }),
//      addAction("engageDiagonal", ActionsComponent.Action(0L) {
//        sequence(
//            setAnimation(Animations.FIRING_LOW),
//            applyParent(move(thrustX * 0.75f, thrustY * 0.75f))
//        )
//      }),
//      addAction("disengage", ActionsComponent.Action(0L) {
//        sequence(
//            setAnimation(Animations.NONE))
////                    applyParent(decelerate(thrustX, thrustY)))
//      })
//  )

  fun default(offsetX: Float,
              offsetY: Float,
              rotation: Float,
              thrustX: Float,
              thrustY: Float) = sequence(
      anchorToParent(),
      setOffset(offsetX, offsetY),
//      assignAnimation(Animations.FIRING_LOW, "spritesheets/main.png", 0.025f, arrayListOf()),
//          AssignAnimation.Frame(32 * 1, 0, 32, 32),
//          AssignAnimation.Frame(32 * 2, 0, 32, 32))
//      ),
      setRotation(rotation),
      addAction("engage", ActionsComponent.Action(0L) {
        sequence(
            setAnimation(Animations.FIRING_LOW),
            applyParent(move(thrustX, thrustY))
        )
      }),
      addAction("engageDiagonal", ActionsComponent.Action(0L) {
        sequence(
            setAnimation(Animations.FIRING_LOW),
            applyParent(move(thrustX * 0.75f, thrustY * 0.75f))
        )
      }),
      addAction("disengage", ActionsComponent.Action(0L) {
        sequence(
            setAnimation(Animations.NONE))
//                    applyParent(decelerate(thrustX, thrustY)))
      })
  )
}
