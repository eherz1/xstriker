package ecs.playmode.entities

import com.artemis.Archetype
import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.ActionsComponent
import com.onemangrove.xstriker.ecs.playmode.components.PositionComponent
import com.onemangrove.xstriker.ecs.playmode.components.RenderableComponent
import com.onemangrove.xstriker.ecs.playmode.components.VelocityComponent
import com.onemangrove.xstriker.ecs.playmode.operations.*
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.delay
import se.feomedia.orion.OperationFactory.repeat
import se.feomedia.orion.OperationFactory.sequence

object Gun {
  private var count = 0L

  fun archetype(world: World): Archetype = ArchetypeBuilder()
      .add(RenderableComponent::class.java)
      .add(PositionComponent::class.java)
      .add(VelocityComponent::class.java)
      .add(ActionsComponent::class.java)
      .build(world)

  fun default(offsetX: Float, offsetY: Float, rotation: Float): Operation = sequence(
      setTag(String.format("gun%s", Gun.count++)),
      anchorToParent(),
      setOffset(offsetX, offsetY),
      setRotation(rotation)
  )

//  fun railGun(offsetX: Float, offsetY: Float, rotation: Float, bulletFactory: () -> Operation): Operation = sequence(
//      default(offsetX, offsetY, rotation),
//      assignAnimation(Animations.STILL, "spritesheets/main.png", 1f, arrayListOf(
//          AssignAnimation.Frame(32 * 3, 0, 32, 32)
//      )),
//      assignAnimation(Animations.FIRING_LOW, "spritesheets/main.png", 1f, arrayListOf(
//          AssignAnimation.Frame(32 * 3, 0, 32, 32)
//      )),
//      setAnimation(Animations.STILL),
//      addAction("fire", ActionsComponent.Action(100) {
//        spawn("bullet", bulletFactory())
//      })
//  )
  fun railGun(offsetX: Float, offsetY: Float, rotation: Float, bulletFactory: () -> Operation): Operation = sequence(
      default(offsetX, offsetY, rotation),
      assignAnimation(Animations.STILL, "sprites/rail-gun.png", 1f, arrayListOf(
          AssignAnimation.Frame(0, 0, 24, 24)
      )),
      assignAnimation(Animations.FIRING_LOW, "sprites/rail-gun.png", 1f, arrayListOf(
          AssignAnimation.Frame(0, 0, 24, 24)
      )),
      setAnimation(Animations.STILL),
      addAction("fire", ActionsComponent.Action(100) {
        spawn("bullet", bulletFactory())
      })
  )

  fun burstGun(
        offsetX: Float,
        offsetY: Float,
        rotation: Float,
        bulletFactory: () -> Operation): Operation = sequence(
      default(offsetX, offsetY, rotation),
      assignAnimation(Animations.STILL, "spritesheets/main.png", 1f, arrayListOf(
          AssignAnimation.Frame(32 * 3, 0, 32, 32)
      )),
      assignAnimation(Animations.FIRING_LOW, "spritesheets/main.png", 1f, arrayListOf(
          AssignAnimation.Frame(32 * 3, 0, 32, 32)
      )),
      setAnimation(Animations.STILL),
      addAction("fire", ActionsComponent.Action(100) {
        sequence(
          spawn("bullet", bulletFactory()),
          delay(.05f),
          spawn("bullet", bulletFactory()),
          delay(.05f),
          spawn("bullet", bulletFactory())
        )
      })
  )

  fun pulseGun(
        offsetX: Float,
        offsetY: Float,
        rotation: Float,
        bulletFactory: () -> Operation): Operation = sequence(
      default(offsetX, offsetY, rotation),
      assignAnimation(Animations.STILL, "sprites/pulse-gun.png", 1f, arrayListOf(
          AssignAnimation.Frame(0, 0, 24, 24)
      )),
      assignAnimation(Animations.FIRING_LOW, "sprites/pulse-gun.png", 1f, arrayListOf(
          AssignAnimation.Frame(0, 0, 24, 24)
      )),
      setAnimation(Animations.STILL),
      addAction("charge", ActionsComponent.Action(100) {
        sequence(
            delay(0.05f)
        )
      })
//      addAction("fire", ActionsComponent.Action(100) {
//        sequence(
//            spawn("bullet", bulletFactory()),
//            delay(.05f),
//            spawn("bullet", bulletFactory()),
//            delay(.05f),
//            spawn("bullet", bulletFactory())
//        )
//      })
  )
}
