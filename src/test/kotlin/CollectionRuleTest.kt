import com.keyway.rollout.rules.*
import com.keyway.rollout.targets.CollectionTarget
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CollectionRuleTest {

    @Test
    fun `given a list of included candidates, collection rule should return true when it is in the list`() {
        val rule = CollectionRule("test-rollout",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            CollectionTarget(
                setOf(
                    "1ffeddcb-0f80-417e-840d-502608e2b83e",
                    "9afeddcd-3f80-4i7e-800d-302308e2b84f"))
        )

        Assertions.assertTrue(rule.evaluate("1ffeddcb-0f80-417e-840d-502608e2b83e"))
        Assertions.assertTrue(rule.evaluate("9afeddcd-3f80-4i7e-800d-302308e2b84f"))
    }

    @Test
    fun `given a list of excluded candidates, collection rule should return false when it is in the list`() {
        val rule = CollectionRule("test-rollout",
            ConditionalValueEnum.EXCLUDE,
            emptySet(),
            CollectionTarget(
                setOf(
                    "1ffeddcb-0f80-417e-840d-502608e2b83e",
                    "9afeddcd-3f80-4i7e-800d-302308e2b84f"))
        )

        Assertions.assertFalse(rule.evaluate("1ffeddcb-0f80-417e-840d-502608e2b83e"))
        Assertions.assertFalse(rule.evaluate("9afeddcd-3f80-4i7e-800d-302308e2b84f"))
    }

    @Test
    fun `given a list of included candidates, collection rule should return false when it is not in the list`() {
        val rule = CollectionRule("test-rollout",
            ConditionalValueEnum.INCLUDE,
            emptySet(),
            CollectionTarget(
                setOf(
                    "1ffeddcb-0f80-417e-840d-502608e2b83e",
                    "9afeddcd-3f80-4i7e-800d-302308e2b84f"))
        )

        Assertions.assertFalse(rule.evaluate("80e17075-11e3-423f-8442-d9eab8d69eca"))
        Assertions.assertFalse(rule.evaluate("1e4c1d17-2b2e-4f80-af15-50d74921307b"))
    }

    @Test
    fun `given a list of excluded candidates, collection rule should return true when it is not in the list`() {
        val rule = CollectionRule("test-rollout",
            ConditionalValueEnum.EXCLUDE,
            emptySet(),
            CollectionTarget(
                setOf(
                    "1ffeddcb-0f80-417e-840d-502608e2b83e",
                    "9afeddcd-3f80-4i7e-800d-302308e2b84f"))
        )

        Assertions.assertTrue(rule.evaluate("80e17075-11e3-423f-8442-d9eab8d69eca"))
        Assertions.assertTrue(rule.evaluate("1e4c1d17-2b2e-4f80-af15-50d74921307b"))
    }
}