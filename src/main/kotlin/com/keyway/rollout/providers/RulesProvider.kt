package com.keyway.rollout.providers

import com.keyway.rollout.rules.IRule
import com.keyway.rollout.rules.IVariationRule

abstract class RulesProvider {

    init {
        validateRules()
    }

    abstract fun getRules(rolloutKey: String): Set<IRule>
    abstract fun getVariationRules(rolloutKey: String): Set<IVariationRule>

    private fun validateRules() {
        // Implement basics validations
    }
}
