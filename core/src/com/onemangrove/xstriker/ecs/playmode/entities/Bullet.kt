package ecs.playmode.entities

import com.artemis.Archetype
import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.*
import com.onemangrove.xstriker.ecs.playmode.operations.*
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.sequence

object Bullet {
  private var count = 0L

  fun archetype(world: World): Archetype = ArchetypeBuilder()
      .add(PositionComponent::class.java)
      .add(RenderableComponent::class.java)
      .add(VelocityComponent::class.java)
      .add(CollidableComponent::class.java)
      .add(DamageComponent::class.java)
      .add(HealthComponent::class.java)
      .build(world)

  fun default(): Operation = sequence(
      setTag(String.format("bullet%s", Bullet.count++)),
      setPositionEqualToSource(),
      setVelocityTangentToSource(1f),
      setHealth(1f),
      setBody(8f, 8f),
      markBullet()
  )

  fun player(): Operation = sequence(
      default(),
      setVelocityTangentToSource(15f),
      assignAnimation(Animations.STILL, "spritesheets/main.png", 0.025f, arrayListOf(
          AssignAnimation.Frame(32 * 5, 0, 32, 32),
          AssignAnimation.Frame(32 * 6, 0, 32, 32),
          AssignAnimation.Frame(32 * 7, 0, 32, 32))
      ),
      setAnimation(Animations.STILL)
  )

  fun enemy(): Operation = sequence(
      default(),
      setVelocityTangentToSource(5f),
      assignAnimation(Animations.STILL, "spritesheets/main.png", 0.025f, arrayListOf(
          AssignAnimation.Frame(32 * 8, 0, 32, 32),
          AssignAnimation.Frame(32 * 9, 0, 32, 32),
          AssignAnimation.Frame(32 * 10, 0, 32, 32))),
      setAnimation(Animations.STILL)
  )
}
