package com.keyway.rollout.rules

import com.keyway.rollout.targets.PercentageTarget
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

open class PercentageRule(
    override val name: String,
    override val conditional: ConditionalValueEnum,
    override val children: Set<IRule> = emptySet(),
    private val percentageTarget: PercentageTarget
): IRule {

    private fun randomFactor(candidate: String): BigDecimal {
        return Random((candidate+name).hashCode())
            .nextDouble(0.0,1.0)
            .toBigDecimal()
            .setScale(4, RoundingMode.HALF_EVEN)
    }

    override fun evaluate(candidate: String): Boolean {
        val decimalTarget = percentageTarget.value
            .setScale(4, RoundingMode.HALF_EVEN)
            .div(BigDecimal("100.0000"))

        val targetResult = randomFactor(candidate) <= decimalTarget
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