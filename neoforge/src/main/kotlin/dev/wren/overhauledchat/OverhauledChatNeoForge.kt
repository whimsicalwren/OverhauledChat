package dev.wren.overhauledchat

import dev.wren.overhauledchat.config.ConfigUpdater
import dev.wren.overhauledchat.util.config
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.neoforge.common.NeoForge

@Mod(ID)
class OverhauledChatNeoForge(container: ModContainer) {
    init {
        val neoBus = NeoForge.EVENT_BUS
        val modBus = container.eventBus!! // amaze amaze amaze

        // region register listeners
        modBus.addListener(::onConfigLoad)
        modBus.addListener(::onConfigReload)
        // endregion

        container.config(ModConfig.Type.CLIENT, ConfigUpdater.CLIENT_SPEC)
        container.config(ModConfig.Type.COMMON, ConfigUpdater.COMMON_SPEC)
        container.config(ModConfig.Type.SERVER, ConfigUpdater.SERVER_SPEC)

        OverhauledChat.init()
        LOGGER.info("neoforge init")
    }

    // region event listeners
    private fun onConfigLoad(event: ModConfigEvent.Loading) {
        if (event.config.modId == ID) {
            val config = event.config.loadedConfig?.config() ?: return
            ConfigUpdater.update(config)
        }
    }

    private fun onConfigReload(event: ModConfigEvent.Reloading) {
        if (event.config.modId == ID) {
            val config = event.config.loadedConfig?.config() ?: return
            ConfigUpdater.update(config)
        }
    }
    // endregion
}