package com.kglazuna.app

import com.kglazuna.app.model.Cat
import org.powermock.api.mockito.PowerMockito

fun <T : Any?> whenCall(methodCall: T) = PowerMockito.`when`(methodCall)

val catMock = listOf(
    Cat("0", "0"),
    Cat("1", "1")
)
