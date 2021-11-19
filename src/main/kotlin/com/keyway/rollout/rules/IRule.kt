package com.keyway.rollout.rules

interface IRule {
    val name: String
    val conditional: ConditionalValueEnum
    val children: Set<IRule>
    fun evaluate(candidate: String): Boolean
}
