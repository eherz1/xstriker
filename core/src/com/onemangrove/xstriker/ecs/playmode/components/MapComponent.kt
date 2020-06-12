package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component
import com.onemangrove.xstriker.TiledMapWrapper

class MapComponent : Component() {

    var resource: String? = null

    var unitScale = 1f

    var wrapper: com.onemangrove.xstriker.TiledMapWrapper? = null

}
