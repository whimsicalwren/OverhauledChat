package dev.wren.overhauledchat.client

import net.minecraft.client.gui.screens.ChatScreen

class OverhauledChatScreen : ChatScreen {

    private var initial: String

    constructor(initial: String) : super(initial) {
        this.initial = initial
    }


}