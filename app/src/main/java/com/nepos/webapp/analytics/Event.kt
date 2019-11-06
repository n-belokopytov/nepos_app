package com.nepos.webapp.analytics

abstract class Event(val name: String, val parameters: Map<String, Any?>)