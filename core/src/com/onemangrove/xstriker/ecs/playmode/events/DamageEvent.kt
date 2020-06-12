package com.onemangrove.xstriker.ecs.playmode.events

import net.mostlyoriginal.api.event.common.Event

class DamageEvent(val sourceEntityId: Int,
                  val x: Float,
                  val y: Float,
                  val radius: Float,
                  val damage: Float) : Event
