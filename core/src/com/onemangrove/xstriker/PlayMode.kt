package com.onemangrove.xstriker

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.managers.TagManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.onemangrove.xstriker.ecs.playmode.entities.Level
import com.onemangrove.xstriker.ecs.playmode.operations.spawn
import com.onemangrove.xstriker.ecs.playmode.systems.*
import com.onemangrove.xstriker.ecs.shared.systems.ControllerSystem
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper
import com.onemangrove.xstriker.ecs.shared.utils.WorldHolder
import net.mostlyoriginal.api.event.common.EventSystem
import se.feomedia.orion.system.OperationSystem

class PlayMode : com.onemangrove.xstriker.GameMode() {
  private val worldHolder: WorldHolder
  private val assetManager: AssetManager
  private val archetypeManager: ArchetypeManager
  private val operationsHelper: OperationsHelper
  private val tagManager: TagManager
  private var world: World

  init {
    worldHolder = WorldHolder()

    assetManager = AssetManager(InternalFileHandleResolver())
    assetManager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))

    archetypeManager = com.onemangrove.xstriker.ArchetypeManager()
    operationsHelper = OperationsHelper()
    tagManager = TagManager()

    val eventSystem = EventSystem()
//        val worldSerializationManager = WorldSerializationManager()
    world = World(WorldConfigurationBuilder()
//        .with(MapRenderingSystem()),
        .with(CameraSystem())
//        .with(WorldConfigurationBuilder.Priority.LOWEST, StarBackgroundRenderingSystem())
        .with(StarBackgroundRenderingSystem())
        .with(RenderingSystem())
//        .with(CollisionBoxRenderingSystem())
        .with(tagManager)
        .with(eventSystem)
        .with(OperationSystem())
        .with(ControllerSystem())
        .with(ControlsSystem())
        .with(MotionSystem())
        .with(AnchoringSystem())
        .with(CollisionSystem())
        .with(TrackingSystem())
        .with(AISystem())
        .with(DeathSystem())
        .with(ReaperSystem())
        .with(LevelSystem())
        .with(OverlaySystem())
        .build()
//        .setSystem(worldSerializationManager)
        .register(assetManager)
        .register(archetypeManager)
        .register(operationsHelper)
        .register(worldHolder))

    worldHolder.world = world
    archetypeManager.world = world
    archetypeManager.load()
    operationsHelper.world = world

//        worldSerializationManager.setSerializer(JsonArtemisSerializer(world))

    assetManager.load("spritesheets/main.png", Texture::class.java)
    assetManager.load("maps/test-0.tmx", TiledMap::class.java)
    assetManager.finishLoading()

    operationsHelper.execute(spawn("level", Level.one(world)))
  }

  override fun resize(width: Int, height: Int) {}

  override fun dispose() {
//        assetManager.get("maps/test-0.tmx", TiledMap::class.java).dispose()
  }

  override fun render(delta: Float) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
    world.setDelta(delta)
    world.process()
  }
}
