package com.keyway.rollout.rules

import com.keyway.rollout.VariationsEnum
import com.keyway.rollout.targets.CollectionTarget

class CollectionVariationRule(
    override val name: String,
    override val conditional: ConditionalValueEnum,
    collectionTarget: CollectionTarget,
    override val variation: VariationsEnum): IVariationRule, CollectionRule(name, conditional, emptySet(), collectionTarget)