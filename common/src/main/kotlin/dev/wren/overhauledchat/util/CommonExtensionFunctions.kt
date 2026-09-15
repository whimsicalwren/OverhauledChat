package dev.wren.overhauledchat.util

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

fun String.component(): MutableComponent = Component.literal(this)

inline fun <T> T.applyIf(condition: Boolean, applyFunc: (T) -> T): T {
    if (condition) {
        applyFunc.invoke(this)
    }
    return this
}

