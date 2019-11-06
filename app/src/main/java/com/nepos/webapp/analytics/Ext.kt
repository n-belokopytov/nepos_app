package com.nepos.webapp.analytics

fun <K, V : Any> Map<K, V?>.toVarargArray() = map { it.key to it.value }.toTypedArray()
