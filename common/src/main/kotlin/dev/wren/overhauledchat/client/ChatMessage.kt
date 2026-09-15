package dev.wren.overhauledchat.client

import net.minecraft.network.chat.Component

data class BasicChatMessage(override val component: Component, val id: String) : ChatMessage(component)

open class ChatMessage(open val component: Component)