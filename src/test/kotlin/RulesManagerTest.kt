import com.keyway.rollout.RulesManager
import com.keyway.rollout.VariationsEnum
import com.keyway.rollout.providers.RulesProvider
import com.keyway.rollout.rules.CollectionRule
import com.keyway.rollout.rules.ConditionalValueEnum
import com.keyway.rollout.rules.PercentageRule
import com.keyway.rollout.rules.PercentageVariationRule
import com.keyway.rollout.targets.CollectionTarget
import com.keyway.rollout.targets.PercentageTarget
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class RulesManagerTest {

    private val percentageRule = PercentageRule(
        "test",
        ConditionalValueEnum.INCLUDE,
        emptySet(),
        PercentageTarget(BigDecimal("100.00")))

    private val variationRuleA = PercentageVariationRule(
        "test-rollout",
        ConditionalValueEnum.INCLUDE,
        PercentageTarget(BigDecimal("50.00")),
        VariationsEnum.A,
        PercentageTarget(BigDecimal("00.00"))
    )

    private val variationRuleB = PercentageVariationRule(
        "test-rollout",
        ConditionalValueEnum.INCLUDE,
        PercentageTarget(BigDecimal("100.00")),
        VariationsEnum.B,
        PercentageTarget(BigDecimal("50.00")))

    private val collectionRule = CollectionRule(
        "test",
        ConditionalValueEnum.INCLUDE,
        emptySet(),
        CollectionTarget(setOf("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
    )

    private val collectionRuleWith10Candidates = CollectionRule(
        "test",
        ConditionalValueEnum.INCLUDE,
        emptySet(),
        CollectionTarget(setOf(
            "620563a7-b195-40a9-8c57-0a5f60c2faba",
            "b2ae7f38-6f55-4a6e-8f3d-0400741fa04d",
            "8d51f953-05f8-418c-9dda-b3822e56e879",
            "bd75409b-3f86-49cf-9800-625ff2e343da",
            "7654bab2-f236-4a6d-95f8-0733686470e8",
            "b86e984e-bf5f-49be-8c8b-70f87f315857",
            "c7f0d194-f35d-467b-897a-39d3093a171e",
            "1e8c87fd-caec-473c-a41b-e60d84a95e92",
            "6e981d86-4bf8-4228-8138-2150749945d8",
            "de18a73d-0d92-4a43-b712-8d7e032f8669"))
    )

    private val chainedRuleWithChildAt100 = CollectionRule(
        "test",
        ConditionalValueEnum.INCLUDE,
        setOf(PercentageRule(
            "test",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("100.00")))),
        CollectionTarget(setOf(
            "998d7756-c8bd-4bc7-8d5f-eacc0a193b58",
            "14f0fde2-ae7f-40a0-ab87-72c3c68c6fce",
            "c9de328a-aae5-42aa-900c-1564410cfdcd",
            "adfd7dca-f017-4e25-bef5-98cb7e36c379",
            "c662dc25-d951-4015-8229-3388d1181c24",
            "f9e833be-5250-4e71-b034-0b0e30432af4",
            "ece6cd7f-1673-44db-aad3-ee61355727e2",
            "11914790-0a6b-43bb-8e6c-98e036c078e6",
            "e5efd191-2ac5-4775-ab76-0e7ad01cbc11"))
    )

    private val chainedRuleWithChildAt10 = CollectionRule(
        "test",
        ConditionalValueEnum.INCLUDE,
        setOf(PercentageRule(
            "test",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("10.00")))),
        CollectionTarget(setOf(
            "998d7756-c8bd-4bc7-8d5f-eacc0a193b58",
            "14f0fde2-ae7f-40a0-ab87-72c3c68c6fce",
            "c9de328a-aae5-42aa-900c-1564410cfdcd",
            "adfd7dca-f017-4e25-bef5-98cb7e36c379",
            "c662dc25-d951-4015-8229-3388d1181c24",
            "f9e833be-5250-4e71-b034-0b0e30432af4",
            "ece6cd7f-1673-44db-aad3-ee61355727e2",
            "11914790-0a6b-43bb-8e6c-98e036c078e6",
            "e5efd191-2ac5-4775-ab76-0e7ad01cbc11"))
    )

    @Test
    fun `evaluate a single rule should return true`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getRules("test") } returns setOf(percentageRule)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertTrue(rulesManager.evaluateRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
    }

    @Test
    fun `evaluate a two rules should return true when all evaluations are true`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getRules("test") } returns setOf(percentageRule, collectionRule)
        val rulesManager = RulesManager("test",mockRuleProvider)

        assertTrue(rulesManager.evaluateRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
    }

    @Test
    fun `evaluate a two rules should return true when at least on evaluation is true`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getRules("test") } returns setOf(percentageRule, collectionRule)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertTrue(rulesManager.evaluateRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b99"))
    }

    @Test
    fun `chained rules returns false when the parent condition is false`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getRules("test") } returns setOf(chainedRuleWithChildAt100)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertFalse(rulesManager.evaluateRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b99"))
    }

    @Test
    fun `chained rules returns true when the parent and its children condition are true`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getRules("test") } returns setOf(chainedRuleWithChildAt100)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertTrue(rulesManager.evaluateRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
    }

    @Test
    fun `chained rules returns false when its children condition are false`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getRules("test") } returns setOf(chainedRuleWithChildAt10)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertFalse(rulesManager.evaluateRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
    }

    @Test
    fun `two sibling rules returns true when at least one rule condition is true`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getRules("test") } returns setOf(collectionRule, collectionRuleWith10Candidates)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertTrue(rulesManager.evaluateRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
        assertTrue(rulesManager.evaluateRules( "7654bab2-f236-4a6d-95f8-0733686470e8"))
    }

    @Test
    fun `two variation percentage rules return the correct variation depending on the candidate`() {
        val mockRuleProvider = mockk<RulesProvider>()
        every { mockRuleProvider.getVariationRules("test") } returns setOf(variationRuleA, variationRuleB)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertEquals(VariationsEnum.B, rulesManager.evaluateVariationRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
        assertEquals(VariationsEnum.A, rulesManager.evaluateVariationRules( "7654bab2-f236-4a3d-95f8-0733686470e8"))
    }

    @Test
    fun `two variation percentage rules return NOT_DEFINED variation`() {
        val mockRuleProvider = mockk<RulesProvider>()
        val variationRuleA = PercentageVariationRule(
            "test-rollout",
            ConditionalValueEnum.INCLUDE,
            PercentageTarget(BigDecimal("10.00")),
            VariationsEnum.A,
            PercentageTarget(BigDecimal("00.00"))
        )
        val variationRuleB = PercentageVariationRule(
            "test-rollout",
            ConditionalValueEnum.INCLUDE,
            PercentageTarget(BigDecimal("20.00")),
            VariationsEnum.B,
            PercentageTarget(BigDecimal("10.00"))
        )

        every { mockRuleProvider.getVariationRules("test") } returns setOf(variationRuleA, variationRuleB)
        val rulesManager = RulesManager("test", mockRuleProvider)

        assertEquals(VariationsEnum.NOT_DEFINED, rulesManager.evaluateVariationRules("998d7756-c8bd-4bc7-8d5f-eacc0a193b58"))
    }
}