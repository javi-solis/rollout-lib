package com.keyway.rollout.fetcher

import com.keyway.rollout.rules.IRule
import com.keyway.rollout.rules.IVariationRule

class S3RulesFetcher: RulesFetcher {
    override fun getRules(rolloutKey: String): Set<IRule> {
        TODO("Not yet implemented")
    }

    override fun getVariationRules(rolloutKey: String): Set<IVariationRule> {
        TODO("Not yet implemented")
    }
}