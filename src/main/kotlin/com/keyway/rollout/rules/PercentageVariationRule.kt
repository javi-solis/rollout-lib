package com.keyway.rollout.rules

import com.keyway.rollout.VariationsEnum
import com.keyway.rollout.targets.PercentageTarget
import com.keyway.rollout.targets.ZERO

class PercentageVariationRule(
    override val name: String,
    override val conditional: ConditionalValueEnum,
    maxPercentageTarget: PercentageTarget,
    override val variation: VariationsEnum,
    minPercentageTarget: PercentageTarget = ZERO,
): IVariationRule, PercentageRule(name, conditional, emptySet(), maxPercentageTarget, minPercentageTarget)