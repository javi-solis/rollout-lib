package com.keyway.rollout

import com.keyway.rollout.providers.RulesProvider

class RulesManager(
    private val rolloutKey: String,
    private val rulesProvider: RulesProvider,
    private val defaultValue: Boolean = true) {

    fun evaluateRules(candidate: String): Boolean {
        val results = rulesProvider.getRules(rolloutKey).map {
            it.evaluate(candidate)
        }

        if (results.all { false }) {
            return defaultValue
        }

        return results.any { it }
    }

    fun evaluateVariationRules(candidate: String): VariationsEnum {
        rulesProvider.getVariationRules(rolloutKey).map {
            if (it.evaluate(candidate)) {
                return it.variation
            }
        }

        return VariationsEnum.NOT_DEFINED
    }
}