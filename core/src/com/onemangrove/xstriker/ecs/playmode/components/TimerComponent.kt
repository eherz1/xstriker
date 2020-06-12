package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

class TimerComponent : Component() {

    var t: Float? = 0f

    fun isExpired() = t!! <= 0f

}
