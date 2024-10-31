package com.gmail.denuelle42.denuanime.utils

import kotlin.reflect.full.memberProperties


fun <T : Any> T.toMap(): Map<String, Any?> {
    return this::class.memberProperties.associate { it.name to it.getter.call(this)}
}

fun <T : Any> T.toNonNullMap(): Map<String, Any> {
    return this::class.memberProperties
        .mapNotNull { prop ->
            prop.getter.call(this)?.let { value ->
                prop.name to value
            }
        }
        .toMap() as Map<String, Any> // Safe cast since we filtered out nulls
}
