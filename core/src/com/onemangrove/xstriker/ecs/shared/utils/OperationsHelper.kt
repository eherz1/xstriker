package com.onemangrove.xstriker.ecs.shared.utils

import com.artemis.Entity
import com.artemis.World
import se.feomedia.orion.Operation

/**
 * Binds to a world, cuts down on boilerplate.
 */
class OperationsHelper {

    lateinit var world: World

    fun newExecution(operation: Operation) = OperationExecution(world, null, operation)

    fun newExecution(entityId: Int, operation: Operation) = OperationExecution(world, entityId, operation)

    fun newExecution(entity: Entity, operation: Operation) = OperationExecution(world, entity.id, operation)

    fun execute(operation: Operation) = newExecution(operation).execute()

    fun execute(entityId: Int, operation: Operation) = newExecution(entityId, operation).execute()

    fun execute(entity: Entity, operation: Operation) = newExecution(entity, operation).execute()

}
