package dev.wren.overhauledchat.client

import dev.wren.overhauledchat.util.logger
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.ChatComponent
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class OverhauledChatComponent : ChatComponent {

    private val minecraft: Minecraft
    private val loadedMessages: MutableMap<String, ChatMessage> = mutableMapOf()
    private var scrollPos: Int = 0

    constructor(minecraft: Minecraft) : super(minecraft) {
        this.minecraft = minecraft
        this.loadedMessages
    }

    companion object {
        private val LOGGER = logger("chat")
    }
}