package dev.wren.overhauledchat.config

import dev.wren.overhauledchat.internal.config.ConfigEntry


object OverhauledChatConfig {

    @JvmField
    val client = ClientConfig()

    @JvmField
    val common = CommonConfig()

    @JvmField
    val server = ServerConfig()

    class ClientConfig {
        @ConfigEntry
        var showMarkdown = true

        @ConfigEntry
        var showMarkdownWhileTyping = true

        @ConfigEntry(
            description = "If true, then"
        )
        var doNotDisturb = false
    }

    class CommonConfig {

    }

    class ServerConfig {

    }
}