package com.keyway.rollout.rules

import com.keyway.rollout.VariationsEnum

interface IVariationRule: IRule {
    val variation: VariationsEnum
}
