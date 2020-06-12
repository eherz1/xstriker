package com.onemangrove.xstriker.ecs.playmode.components

import com.artemis.Component

/**
 * Marks the entity upon which the camera anchors.
 *
 * At most the world should contain one entity with the focus component.
 *
 */
class FocusComponent : Component()
