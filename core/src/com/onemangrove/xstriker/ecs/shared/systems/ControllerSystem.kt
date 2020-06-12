package com.onemangrove.xstriker.ecs.shared.systems

import com.artemis.BaseSystem
import com.artemis.annotations.Wire
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerAdapter
import com.badlogic.gdx.controllers.Controllers
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem

class ControllerSystem : BaseSystem() {
  open class ControllerEvent : Event

  open class ControllerConnectedEvent(val controllerId: String) : ControllerEvent()

  open class ControllerDisconnectedEvent(val controllerId: String) : ControllerEvent()

  open class ControllerXAxisEvent(val controllerId: String, val magnitude: Float) : ControllerEvent()

  open class ControllerYAxisEvent(val controllerId: String, val magnitude: Float) : ControllerEvent()

  open class ControllerButtonDownEvent(val controllerId: String, val buttonCode: Int) : ControllerEvent()

  open class ControllerButtonUpEvent(val controllerId: String, val buttonCode: Int) : ControllerEvent()

  @Wire
  private lateinit var eventSystem: EventSystem

  private val gdxControllerAdapter = object : ControllerAdapter() {

    override fun connected(controller: Controller) {
      eventSystem.dispatch(ControllerConnectedEvent(controller.name))
    }

    override fun disconnected(controller: Controller) {
      eventSystem.dispatch(ControllerDisconnectedEvent(controller.name))
    }

    override fun axisMoved(controller: Controller, axisCode: Int, value: Float): Boolean {
      val magnitude = Math.round(value).toFloat()
      if (axisCode == IBuffaloController.AXIS_X)
        eventSystem.dispatch(ControllerXAxisEvent(controller.name, magnitude))
      if (axisCode == IBuffaloController.AXIS_Y)
        eventSystem.dispatch(ControllerYAxisEvent(controller.name, magnitude))
      return true
    }

    override fun buttonDown(controller: Controller, buttonCode: Int): Boolean {
      eventSystem.dispatch(ControllerButtonDownEvent(controller.name, buttonCode))
      return true
    }

    override fun buttonUp(controller: Controller, buttonCode: Int): Boolean {
      eventSystem.dispatch(ControllerButtonUpEvent(controller.name, buttonCode))
      return true
    }

  }

  override fun initialize() {
    Controllers.addListener(this.gdxControllerAdapter)
  }

  override fun processSystem() {}
}
