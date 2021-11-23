package com.keyway.rollout.rules

import com.keyway.rollout.targets.PercentageTarget
import com.keyway.rollout.targets.ZERO
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

open class PercentageRule(
    override val name: String,
    override val conditional: ConditionalValueEnum,
    override val children: Set<IRule> = emptySet(),
    private val maxPercentageTarget: PercentageTarget,
    private val minPercentageTarget: PercentageTarget = ZERO
): IRule {

    private fun randomFactor(candidate: String): BigDecimal {
        return Random((candidate+name).hashCode())
            .nextDouble(0.0,1.0)
            .toBigDecimal()
            .setScale(4, RoundingMode.HALF_EVEN)
    }

    override fun evaluate(candidate: String): Boolean {
        val randomFactor = randomFactor(candidate)
        val targetResult = randomFactor <= maxPercentageTarget.getDecimalValue()
                && randomFactor > minPercentageTarget.getDecimalValue()
        val result = if (conditional == ConditionalValueEnum.INCLUDE) targetResult else !targetResult
        println("$candidate >> ${randomFactor(candidate)}")
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