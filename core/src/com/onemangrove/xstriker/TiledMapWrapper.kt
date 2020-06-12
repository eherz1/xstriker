package com.onemangrove.xstriker

import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import java.util.regex.Pattern

class TiledMapWrapper(val tiledMap: TiledMap?) {

    init {
        this.metaLayer!!.setVisible(false)
    }

    fun dispose() {
        this.tiledMap!!.dispose()
    }

    val width: Float
        get() {
            // TODO: Can specify float here?
            return tiledMap!!.getProperties().get("width", Integer::class.java).toFloat()
        }

    val height: Float
        get() {
            // TODO: Can specify float here?
            return tiledMap!!.getProperties().get("height", Integer::class.java).toFloat()
        }

    val backgroundLayer: TiledMapTileLayer?
        get() {
            return tiledMap!!.getLayers().get("background") as TiledMapTileLayer
        }

    val terrainLayer: TiledMapTileLayer?
        get() {
            return tiledMap!!.getLayers().get("terrain") as TiledMapTileLayer
        }

    val metaLayer: TiledMapTileLayer?
        get() {
            return tiledMap!!.getLayers().get("meta") as TiledMapTileLayer
        }

    val objectLayer: MapLayer?
        get() {
            return tiledMap!!.getLayers().get("objects")
        }

    fun isCollidable(x: Int, y: Int): Boolean {
        val cell = metaLayer!!.getCell(x, y)
        if (cell == null) return false
        val collidableProp = cell.tile.properties.get("collidable") ?: "false"
        return "true".equals(collidableProp as String, ignoreCase = true)
    }

    fun isOccupiable(x: Int, y: Int): Boolean {
        return !isCollidable(x, y)
    }

    fun getObject(name: String?): MapObject? {
        if (name == null || "".equals(name))
            return null
        val it = objectLayer!!.getObjects().iterator()
        while (it.hasNext()) {
            val mObj = it.next()
            if (name.equals(mObj!!.getName()))
                return mObj
        }
        return null
    }

    fun getObjects(objectLayerName: String?, pattern: Pattern?): Collection<MapObject>? {
        val results = ArrayList<MapObject>()
        val it = objectLayer!!.getObjects().iterator()
        while (it!!.hasNext()) {
            val mObj = it!!.next()
            if (pattern!!.matcher(mObj!!.getName()).find())
                results!!.add(mObj)
        }
        return results
    }

    companion object {
        val TILE_WIDTH = 32
        val TILE_HEIGHT = 32
        val UNIT_SCALE = 1 / 32f
    }
}