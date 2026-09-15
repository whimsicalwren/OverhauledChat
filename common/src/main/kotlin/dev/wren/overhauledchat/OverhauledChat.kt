package dev.wren.overhauledchat

import dev.wren.overhauledchat.util.logger


object OverhauledChat {

    fun init() {
        LOGGER.info("common init")
    }

}

val LOGGER = logger("Overhauled Chat")
const val ID = "overhauledchat"