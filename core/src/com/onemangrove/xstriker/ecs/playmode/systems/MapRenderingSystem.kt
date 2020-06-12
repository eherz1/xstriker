package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.onemangrove.xstriker.ecs.playmode.components.MapComponent

class MapRenderingSystem : IteratingSystem(Aspect.one(com.onemangrove.xstriker.ecs.playmode.components.MapComponent::class.java)) {

    @Wire private lateinit var cameraSystem: CameraSystem

    @Wire private lateinit var mapMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.MapComponent>

    var rendererMap = HashMap<Int, OrthogonalTiledMapRenderer>()

    override fun process(entity: Int) {
        val map = mapMapper.get(entity)
        if (map.wrapper == null) return
        var renderer = rendererMap.get(entity)
        if (renderer == null) {
            renderer = OrthogonalTiledMapRenderer(map.wrapper!!.tiledMap, map.unitScale)
            rendererMap.put(entity, renderer)
        }
        renderer.setView(cameraSystem.camera)
        renderer.render()
    }

}
