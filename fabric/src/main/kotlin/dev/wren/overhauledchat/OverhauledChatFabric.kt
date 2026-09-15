package dev.wren.overhauledchat

import dev.wren.overhauledchat.config.ConfigUpdater
import dev.wren.overhauledchat.util.config
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents
import net.fabricmc.api.ModInitializer
import net.neoforged.fml.config.ModConfig

class OverhauledChatFabric : ModInitializer {
    override fun onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.config(ModConfig.Type.CLIENT, ConfigUpdater.CLIENT_SPEC)
        NeoForgeConfigRegistry.INSTANCE.config(ModConfig.Type.COMMON, ConfigUpdater.COMMON_SPEC)
        NeoForgeConfigRegistry.INSTANCE.config(ModConfig.Type.SERVER, ConfigUpdater.SERVER_SPEC)

        registerEventListeners()

        OverhauledChat.init()
        LOGGER.info("fabric init")
    }

    private fun registerEventListeners() {
        NeoForgeModConfigEvents.loading(ID).register {
            val config = it.loadedConfig?.config() ?: return@register
            ConfigUpdater.update(config)
        }
        NeoForgeModConfigEvents.reloading(ID).register {
            val config = it.loadedConfig?.config() ?: return@register
            ConfigUpdater.update(config)
        }
    }
}