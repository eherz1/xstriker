package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.onemangrove.xstriker.ecs.playmode.components.TimerComponent

class TimerSystem : IteratingSystem(Aspect.all(com.onemangrove.xstriker.ecs.playmode.components.TimerComponent::class.java)) {

    @Wire private var timerComponentMapper: ComponentMapper<com.onemangrove.xstriker.ecs.playmode.components.TimerComponent>? = null

    public override fun process(entityId: Int) {
        val timerComponent = timerComponentMapper!!.get(entityId)
        val deltaTime = Gdx.graphics.getDeltaTime()
        if (timerComponent.t!! > 0f) {
            timerComponent.t = timerComponent.t!! - deltaTime
        }
    }

}
