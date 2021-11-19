package com.keyway.rollout.rules

import java.lang.IllegalArgumentException
import java.util.*

enum class ConditionalValueEnum {
    INCLUDE,
    EXCLUDE;

    companion object {
        private val ordinalMap = values().associateBy(ConditionalValueEnum::ordinal)

        fun fromIndex(index: Int): ConditionalValueEnum? {
            if (index <= 0 || index > ordinalMap.size) throw Exception("invalid conditional value")

            return ordinalMap[index - 1]
        }

        fun toIndex(conditional: String): Int {
            return values().indexOf(get(conditional)) + 1
        }

        fun get(value: String): ConditionalValueEnum {
            return try {
                valueOf(value.uppercase(Locale.getDefault()))
            } catch (e: IllegalArgumentException) {
                throw Exception("invalid conditional value")
            }
        }
    }
}

