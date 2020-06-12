package com.onemangrove.xstriker.ecs.shared.utils

import com.artemis.Entity
import com.artemis.World
import se.feomedia.orion.Operation

class OperationExecution {

    private val world: World

    private val entityId: Int?

    private val operation: Operation

    constructor(world: World, entityId: Int?, operation: Operation) {
        this.world = world
        this.entityId = entityId
        this.operation = operation
    }

    constructor(world: World, entity: Entity, operation: Operation) {
        this.world = world
        this.entityId = entity.id
        this.operation = operation
    }

    fun execute() {
        if (this.entityId != null)
            this.operation.register(world, entityId)
        else
            this.operation.register(world)
    }

}
