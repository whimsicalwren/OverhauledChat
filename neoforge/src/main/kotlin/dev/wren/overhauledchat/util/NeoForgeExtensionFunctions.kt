package dev.wren.overhauledchat.util

import net.neoforged.fml.ModContainer
import net.neoforged.fml.config.IConfigSpec
import net.neoforged.fml.config.ModConfig


fun ModContainer.config(type: ModConfig.Type, spec: IConfigSpec) {
    registerConfig(type, spec, "overhauledchat/" + type.name.lowercase() + ".toml")
}