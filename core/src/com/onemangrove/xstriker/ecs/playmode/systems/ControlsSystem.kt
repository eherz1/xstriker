package com.onemangrove.xstriker.ecs.playmode.systems

import com.artemis.BaseSystem
import com.artemis.annotations.Wire
import com.artemis.managers.TagManager
import com.onemangrove.xstriker.ecs.playmode.operations.applyChild
import com.onemangrove.xstriker.ecs.playmode.operations.invokeAction
import com.onemangrove.xstriker.ecs.playmode.operations.rotate
import com.onemangrove.xstriker.ecs.shared.systems.ControllerSystem
import com.onemangrove.xstriker.ecs.shared.systems.IBuffaloController
import com.onemangrove.xstriker.ecs.shared.utils.OperationsHelper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe

class ControlsSystem : BaseSystem() {
  @Wire
  private lateinit var eventSystem: EventSystem

  @Wire
  private lateinit var operations: OperationsHelper

  @Wire
  private lateinit var tagManager: TagManager

  @Subscribe
  fun processEvent(event: ControllerSystem.ControllerButtonDownEvent) {
    if (event.buttonCode == IBuffaloController.BUTTON_X)
      engageRailgun = true
    if (event.buttonCode == IBuffaloController.BUTTON_L)
      rotateRailgunCW = true
    if (event.buttonCode == IBuffaloController.BUTTON_R)
      rotateRailgunCCW = true
  }

  @Subscribe
  fun processEvent(event: ControllerSystem.ControllerButtonUpEvent) {
    if (event.buttonCode == IBuffaloController.BUTTON_X)
      engageRailgun = false
    if (event.buttonCode == IBuffaloController.BUTTON_L)
      rotateRailgunCW = false
    if (event.buttonCode == IBuffaloController.BUTTON_R)
      rotateRailgunCCW = false
  }

  @Subscribe
  fun processEvent(event: ControllerSystem.ControllerXAxisEvent) {
    var player = tagManager.getEntity("player")
    if (player == null)
      return
    val magnitude = event.magnitude
    if (magnitude < 0f)
      engageEngineXN = true
    if (magnitude > 0f)
      engageEngineXP = true
    if (magnitude == 0f) {
      if (engageEngineXN) {
        engageEngineXN = false
        operations.execute(player, applyChild("xnThruster", invokeAction("disengage")))
      }
      if (engageEngineXP) {
        engageEngineXP = false
        operations.execute(player, applyChild("xpThruster", invokeAction("disengage")))
      }
    }
  }

  @Subscribe
  fun processEvent(event: ControllerSystem.ControllerYAxisEvent) {
    var player = tagManager.getEntity("player")
    if (player == null)
      return
    val magnitude = event.magnitude
    if (magnitude < 0f)
      engageEngineYP = true
    if (magnitude > 0f)
      engageEngineYN = true
    if (magnitude == 0f) {
      if (engageEngineYN) {
        engageEngineYN = false
        operations.execute(player, applyChild("ynThruster", invokeAction("disengage")))
      }
      if (engageEngineYP) {
        engageEngineYP = false
        operations.execute(player, applyChild("ypThruster", invokeAction("disengage")))
      }
    }
  }

  private fun processEvent(event: ControllerSystem.ControllerEvent) {}

  override fun processSystem() {
    var player = tagManager.getEntity("player")
    if (player == null)
      return
    if (engageEngineXN) {
      if (engageEngineYN || engageEngineYP) {
        operations.execute(player, applyChild("xnThruster", invokeAction("engageDiagonal")))
      } else {
        operations.execute(player, applyChild("xnThruster", invokeAction("engage")))
      }
    }
    if (engageEngineXP) {
      if (engageEngineYN || engageEngineYP) {
        operations.execute(player, applyChild("xpThruster", invokeAction("engageDiagonal")))
      } else {
        operations.execute(player, applyChild("xpThruster", invokeAction("engage")))
      }
    }
    if (engageEngineYN) {
      if (engageEngineXN || engageEngineXP) {
        operations.execute(player, applyChild("ynThruster", invokeAction("engageDiagonal")))
      } else {
        operations.execute(player, applyChild("ynThruster", invokeAction("engage")))
      }
    }
    if (engageEngineYP) {
      if (engageEngineXN || engageEngineXP) {
        operations.execute(player, applyChild("ypThruster", invokeAction("engageDiagonal")))
      } else {
        operations.execute(player, applyChild("ypThruster", invokeAction("engageDiagonal")))
      }
    }
    if (rotateRailgunCW)
      operations.execute(player, applyChild("railGun", rotate(3f)))
    if (rotateRailgunCCW)
      operations.execute(player, applyChild("railGun", rotate(-3f)))
    if (engageRailgun)
      operations.execute(player, applyChild("railGun", invokeAction("fire")))
  }

  var engageEngineXP = false
  var engageEngineXN = false
  var engageEngineYP = false
  var engageEngineYN = false
  var rotateRailgunCW = false
  var rotateRailgunCCW = false
  var engageRailgun = false
}
