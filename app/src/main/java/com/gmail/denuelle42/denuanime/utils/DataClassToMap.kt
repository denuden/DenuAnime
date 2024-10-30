package com.gmail.denuelle42.denuanime.utils

import kotlin.reflect.full.memberProperties


fun <T : Any> T.toMap(): Map<String, Any?> {
    return this::class.memberProperties.associate { it.name to it.getter.call(this)}
}