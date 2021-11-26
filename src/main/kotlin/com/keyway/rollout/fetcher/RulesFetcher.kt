package com.keyway.rollout.fetcher

import com.keyway.rollout.rules.IRule
import com.keyway.rollout.rules.IVariationRule

interface RulesFetcher {
    fun getRules(rolloutKey: String): Set<IRule>
    fun getVariationRules(rolloutKey: String): Set<IVariationRule>
}