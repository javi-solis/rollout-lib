package com.keyway.rollout.rules

import com.keyway.rollout.targets.CollectionTarget

open class CollectionRule(
    override val name: String,
    override val conditional: ConditionalValueEnum,
    override val children: Set<IRule> = emptySet(),
    private val collectionTarget: CollectionTarget,
): IRule {
    override fun evaluate(candidate: String): Boolean {
        val targetResult = collectionTarget.values.contains(candidate)
        val result = if (conditional == ConditionalValueEnum.INCLUDE) targetResult else !targetResult

        if (!result || children.isNullOrEmpty()) {
            return result
        } else {
            children.forEach {
                if (!it.evaluate(candidate)) {
                    return false
                }
            }
            return result
        }
    }
}