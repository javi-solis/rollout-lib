package com.keyway.rollout.providers

import com.keyway.rollout.fetcher.RulesFetcher
import com.keyway.rollout.rules.IRule
import com.keyway.rollout.rules.IVariationRule

abstract class RulesProvider(
    rolloutKey: String,
    rulesFetcher: RulesFetcher) {

    private var rules: Set<IRule> = rulesFetcher.getRules(rolloutKey)
    private var variationRules: Set<IVariationRule> = rulesFetcher.getVariationRules(rolloutKey)

    open fun getRules(rolloutKey: String): Set<IRule> {
        validateRules(rules)
        return rules
    }

    open fun getVariationRules(rolloutKey: String): Set<IVariationRule> {
        validateRules(variationRules)
        return variationRules
    }

    private fun validateRules(rules: Set<IRule>) {
        // Implement basics validations
    }
}
