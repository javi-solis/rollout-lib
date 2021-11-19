import com.keyway.rollout.VariationsEnum
import com.keyway.rollout.rules.ConditionalValueEnum
import com.keyway.rollout.rules.PercentageRule
import com.keyway.rollout.rules.PercentageVariationRule
import com.keyway.rollout.targets.PercentageTarget
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class PercentageRuleTest {

    @Test
    fun `given the same evaluation key, the name of the rule determines the rollout result`() {
        val rule = PercentageRule("test-rollout",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("50.00"))
        )
        val anotherRule = PercentageRule("another-rollout",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("50.00"))
        )

        val result = rule.evaluate("1ffeddcb-0f80-417e-800d-302608e2b83e")
        val anotherResult = anotherRule.evaluate("1ffeddcb-0f80-417e-800d-302608e2b83e")

        assertNotEquals(result, anotherResult)
    }

    @Test
    fun `include certain percentage in rollout`() {
        val rule = PercentageRule("test-rollout",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("02.40"))
        )
        val result = rule.evaluate("1ffeddcb-0f80-417e-800d-302608e2b83e")

        assertTrue(result)
    }

    @Test
    fun `rollout includes 100 returns always true`() {
        val rule = PercentageRule("test-rollout",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("100.00"))
        )

        for (x in 1..100) {
            assertTrue(rule.evaluate(UUID.randomUUID().toString()))
        }
    }

    @Test
    fun `rollout excludes 100 returns always false`() {
        val rule = PercentageRule("test-rollout",
            ConditionalValueEnum.EXCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("100.00"))
        )

        for (x in 1..100) {
            assertFalse(rule.evaluate(UUID.randomUUID().toString()))
        }
    }

    @Test
    fun `exclude certain percentage in rollout`() {
        val rule = PercentageRule("test-rollout",
            ConditionalValueEnum.EXCLUDE,
            emptySet(),
            PercentageTarget(BigDecimal("23.00"))
        )

        val result = rule.evaluate("88bd3188-930c-44e4-8026-44ea3b2c1ca2")

        assertTrue(result)
    }

    @Test
    fun `variation test`() {
        val rule = PercentageVariationRule(
            "test-rollout",
            ConditionalValueEnum.INCLUDE,
            PercentageTarget(BigDecimal("50.00")),
            VariationsEnum.A)

        val result = rule.evaluate("88bd3188-930c-44e4-8026-44ea3b2c1ca2")

        assertTrue(result)
    }
}