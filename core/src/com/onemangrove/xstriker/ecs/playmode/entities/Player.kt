package ecs.playmode.entities

import com.artemis.Archetype
import com.artemis.ArchetypeBuilder
import com.artemis.World
import com.onemangrove.xstriker.Animations
import com.onemangrove.xstriker.ecs.playmode.components.*
import com.onemangrove.xstriker.ecs.playmode.entities.Thruster
import com.onemangrove.xstriker.ecs.playmode.operations.*
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.sequence

object Player {
  val THRUSTER_FORCE = 8f

  fun archetype(world: World): Archetype = ArchetypeBuilder()
      .add(PositionComponent::class.java)
      .add(CollidableComponent::class.java)
//            .add(VelocityComponent::class.java)
      .add(RenderableComponent::class.java)
//            .add(ControlsComponent::class.java)
      .add(HealthComponent::class.java)
      .add(DamageableComponent::class.java)
      .build(world)

//  fun default(): Operation = sequence(
//      setTag("player"),
//      setHealth(1f),
//      setMaxHealth(1f),
//      setBody(24f, 24f),
//      assignAnimation(Animations.STILL, "spritesheets/main.png", 0.25f, arrayListOf(
//          AssignAnimation.Frame(0, 0, 32, 32)
//      )),
//      assignAnimation(Animations.MOVING_UP, "spritesheets/main.png", 0.25f, arrayListOf(
//          AssignAnimation.Frame(0, 0, 32, 32)
//      )),
//      assignAnimation(Animations.MOVING_RIGHT, "spritesheets/main.png", 0.25f, arrayListOf(
//          AssignAnimation.Frame(0, 0, 32, 32)
//      )),
//      assignAnimation(Animations.MOVING_DOWN, "spritesheets/main.png", 0.25f, arrayListOf(
//          AssignAnimation.Frame(0, 0, 32, 32)
//      )),
//      assignAnimation(Animations.MOVING_LEFT, "spritesheets/main.png", 0.25f, arrayListOf(
//          AssignAnimation.Frame(0, 0, 32, 32)
//      )),
//      assignAnimation(Animations.DEATH, "spritesheets/main.png", 0.075f, arrayListOf(
//          AssignAnimation.Frame(32 * 11, 0, 32, 32),
//          AssignAnimation.Frame(32 * 12, 0, 32, 32),
//          AssignAnimation.Frame(32 * 13, 0, 32, 32),
//          AssignAnimation.Frame(32 * 14, 0, 32, 32),
//          AssignAnimation.Frame(32 * 15, 0, 32, 32)
//      ), false),
//      setAnimation(Animations.STILL),
//      spawnChild("ynThruster", "thruster", Thruster.default(
//          0f, 20f, 270f, 0f, -Player.THRUSTER_FORCE)
//      ),
//      spawnChild("xpThruster", "thruster", Thruster.default(
//          -20f, 0f, 0f, Player.THRUSTER_FORCE, 0f)
//      ),
//      spawnChild("ypThruster", "thruster", Thruster.default(
//          0f, -20f, 90f, 0f, Player.THRUSTER_FORCE)
//      ),
//      spawnChild("xnThruster", "thruster", Thruster.default(
//          20f, 0f, 180f, -Player.THRUSTER_FORCE, 0f)
//      ),
//      spawnChild("railGun", "gun", Gun.railGun(0f, 0f, 90f) {
//        Bullet.player()
//      })
//  )

  fun default(): Operation = sequence(
      setTag("player"),
      setHealth(1f),
      setMaxHealth(1f),
      setBody(24f, 24f),
      assignAnimation(Animations.STILL, "sprites/ship.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_UP, "sprites/ship.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_RIGHT, "sprites/ship.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_DOWN, "sprites/ship.png", 0.25f, arrayListOf(
          AssignAnimation.Frame(0, 0, 32, 32)
      )),
      assignAnimation(Animations.MOVING_LEFT, "sprites/ship.png", 0.25f, arrayListOf(
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
          0f, 20f, 270f, 0f, -Player.THRUSTER_FORCE)
      ),
      spawnChild("xpThruster", "thruster", Thruster.default(
          -20f, 0f, 0f, Player.THRUSTER_FORCE, 0f)
      ),
      spawnChild("ypThruster", "thruster", Thruster.default(
          0f, -20f, 90f, 0f, Player.THRUSTER_FORCE)
      ),
      spawnChild("xnThruster", "thruster", Thruster.default(
          20f, 0f, 180f, -Player.THRUSTER_FORCE, 0f)
      ),
      spawnChild("railGun", "gun", Gun.railGun(0f, 0f, 90f) {
        Bullet.player()
      }),
      spawnChild("pulseGun", "gun", Gun.pulseGun(0f, 0f, 90f) {
        Bullet.player()
      })
  )
}
