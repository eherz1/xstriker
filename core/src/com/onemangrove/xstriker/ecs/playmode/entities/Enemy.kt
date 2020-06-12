package ecs.playmode.entities

import com.artemis.Archetype
import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.*
import com.onemangrove.xstriker.ecs.playmode.entities.Pattern
import com.onemangrove.xstriker.ecs.playmode.entities.Thruster
import com.onemangrove.xstriker.ecs.playmode.operations.*
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.sequence

object Enemy {
  private val THRUSTER_FORCE = 0.05f

  private var count = 0L

  fun archetype(world: World): Archetype = ArchetypeBuilder()
      .add(PositionComponent::class.java)
      .add(CollidableComponent::class.java)
      .add(VelocityComponent::class.java)
      .add(RenderableComponent::class.java)
      .add(HealthComponent::class.java)
      .add(DamageableComponent::class.java)
      .build(world)

  fun default(bodyWidth: Float,
              bodyHeight: Float,
              health: Float,
              maxHealth: Float) = sequence(
      setTag(String.format("enemy%s", Enemy.count++)),
      setBody(bodyWidth, bodyHeight),
      setHealth(health),
      setMaxHealth(maxHealth)
  )

  fun frigate(): Operation = sequence(
      default(32f, 32f, 1f, 1f),
      assignAnimation(Animations.STILL, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_UP, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_RIGHT, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_DOWN, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_LEFT, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.DEATH, "spritesheets/main.png", 0.075f, arrayListOf(
          AssignAnimation.Frame(32 * 11, 0, 32, 32),
          AssignAnimation.Frame(32 * 12, 0, 32, 32),
          AssignAnimation.Frame(32 * 13, 0, 32, 32),
          AssignAnimation.Frame(32 * 14, 0, 32, 32),
          AssignAnimation.Frame(32 * 15, 0, 32, 32)
      ), false),
      setAnimation(Animations.STILL),
      spawnChild("ynThruster", "thruster", Thruster.default(
          0f, 20f, 270f, 0f, -Enemy.THRUSTER_FORCE
      )),
      spawnChild("xpThruster", "thruster", Thruster.default(
          0f, 20f, 270f, 0f, -Enemy.THRUSTER_FORCE
      )),
      spawnChild("ypThruster", "thruster", Thruster.default(
          -20f, 0f, 0f, Enemy.THRUSTER_FORCE, 0f
      )),
      spawnChild("xnThruster", "thruster", Thruster.default(
          20f, 0f, 180f, -Enemy.THRUSTER_FORCE, 0f
      )),
      spawnChild("railGun", "gun", sequence(
          Gun.railGun(0f, 0f, 90f, { Bullet.enemy() }),
          trackByTag("player")
      )),
      setAI("frigate"),
      Pattern.downwardSine()
  )

  fun assaultFrigate(): Operation = sequence(
      default(32f, 32f, 1f, 1f),
      assignAnimation(Animations.STILL, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_UP, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_RIGHT, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_DOWN, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_LEFT, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.DEATH, "spritesheets/main.png", 0.075f, arrayListOf(
          AssignAnimation.Frame(32 * 11, 0, 32, 32),
          AssignAnimation.Frame(32 * 12, 0, 32, 32),
          AssignAnimation.Frame(32 * 13, 0, 32, 32),
          AssignAnimation.Frame(32 * 14, 0, 32, 32),
          AssignAnimation.Frame(32 * 15, 0, 32, 32)
      ), false),
      setAnimation(Animations.STILL),
      spawnChild("ynThruster", "thruster", Thruster.default(
          0f, 20f, 270f, 0f, -Enemy.THRUSTER_FORCE
      )),
      spawnChild("xpThruster", "thruster", Thruster.default(
          0f, 20f, 270f, 0f, -Enemy.THRUSTER_FORCE
      )),
      spawnChild("ypThruster", "thruster", Thruster.default(
          -20f, 0f, 0f, Enemy.THRUSTER_FORCE, 0f
      )),
      spawnChild("xnThruster", "thruster", Thruster.default(
          20f, 0f, 180f, -Enemy.THRUSTER_FORCE, 0f
      )),
      spawnChild("railGun", "gun", sequence(
          Gun.burstGun(0f, 0f, 90f, { Bullet.enemy() }),
          trackByTag("player")
      )),
      setAI("frigate"),
      Pattern.downwardSine()
  )

  fun smartBomb(): Operation = sequence(
      default(32f, 32f, 1f, 1f),
      assignAnimation(Animations.STILL, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_UP, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_RIGHT, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_DOWN, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_LEFT, "spritesheets/main.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.DEATH, "spritesheets/main.png", 0.075f, arrayListOf(
          AssignAnimation.Frame(32 * 11, 0, 32, 32),
          AssignAnimation.Frame(32 * 12, 0, 32, 32),
          AssignAnimation.Frame(32 * 13, 0, 32, 32),
          AssignAnimation.Frame(32 * 14, 0, 32, 32),
          AssignAnimation.Frame(32 * 15, 0, 32, 32)
      ), false),
      setAnimation(Animations.STILL),
      spawnChild("ynThruster", "thruster", Thruster.default(
          0f, 20f, 270f, 0f, -Enemy.THRUSTER_FORCE
      )),
      spawnChild("xpThruster", "thruster", Thruster.default(
          0f, 20f, 270f, 0f, -Enemy.THRUSTER_FORCE
      )),
      spawnChild("ypThruster", "thruster", Thruster.default(
          -20f, 0f, 0f, Enemy.THRUSTER_FORCE, 0f
      )),
      spawnChild("xnThruster", "thruster", Thruster.default(
          20f, 0f, 180f, -Enemy.THRUSTER_FORCE, 0f
      )),
      setAI("smartBomb")
  )
}
