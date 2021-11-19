package com.keyway.rollout.rules

import com.keyway.rollout.VariationsEnum
import com.keyway.rollout.targets.PercentageTarget

class PercentageVariationRule(
    override val name: String,
    override val conditional: ConditionalValueEnum,
    percentageTarget: PercentageTarget,
    override val variation: VariationsEnum
): IVariationRule, PercentageRule(name, conditional, emptySet(), percentageTarget)