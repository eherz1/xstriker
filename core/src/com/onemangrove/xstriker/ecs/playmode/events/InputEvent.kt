package com.onemangrove.xstriker.ecs.playmode.events

import net.mostlyoriginal.api.event.common.Event

class InputEvent : Event {
  enum class InputSource {
    KEYBOARD,
    MOUSE,
    GAMEPAD_1,
    GAMEPAD_2,
    GAMEPAD_3,
    GAMEPAD_4
  }

  enum class InputType {}
}
