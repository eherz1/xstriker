package com.onemangrove.xstriker.ecs.playmode.operations

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TiledMap
import com.onemangrove.xstriker.TiledMapWrapper
import com.onemangrove.xstriker.ecs.playmode.components.MapComponent
import se.feomedia.orion.Operation
import se.feomedia.orion.OperationFactory.operation
import se.feomedia.orion.OperationTree
import se.feomedia.orion.operation.SingleUseOperation

fun setMap(resource: String): Operation {
  val op = operation(SetMap::class.java)
  op.resource = resource
  return op
}

class SetMap : SingleUseOperation() {
  class Executor : SingleUseExecutor<SetMap>() {
    @Wire
    private lateinit var assetManager: AssetManager

    @Wire
    private lateinit var mapMapper: ComponentMapper<MapComponent>

    override fun act(op: SetMap, node: OperationTree) {
      val map = mapMapper.get(op.entityId) ?: mapMapper.create(op.entityId)
      if (!assetManager.isLoaded(op.resource)) {
        throw RuntimeException(String.format("Map '%s' is not loaded", op.resource))
      }
      map.resource = op.resource
      map.wrapper = TiledMapWrapper(assetManager.get(op.resource, TiledMap::class.java))
    }
  }

  override fun executorType() = Executor::class.java

  var resource: String? = null
}
