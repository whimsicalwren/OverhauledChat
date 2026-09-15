package dev.wren.overhauledchat.util

import dev.wren.overhauledchat.ID
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry
import net.neoforged.fml.config.IConfigSpec
import net.neoforged.fml.config.ModConfig

fun NeoForgeConfigRegistry.config(type: ModConfig.Type, spec: IConfigSpec) {
    register(ID, type, spec, "overhauledchat/" + type.name.lowercase() + ".toml")
}