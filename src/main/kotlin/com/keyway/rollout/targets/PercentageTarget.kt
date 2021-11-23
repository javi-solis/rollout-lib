package com.keyway.rollout.targets

import java.math.BigDecimal
import java.math.RoundingMode

data class PercentageTarget(val value: BigDecimal) {
    fun getDecimalValue(): BigDecimal {
        return value.setScale(4, RoundingMode.HALF_EVEN)
            .div(BigDecimal("100.0000"))
    }
}

val ZERO: PercentageTarget
    get() {
        return PercentageTarget(BigDecimal("00.0000"))
    }
