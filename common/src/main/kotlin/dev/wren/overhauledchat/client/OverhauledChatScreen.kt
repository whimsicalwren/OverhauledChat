package dev.wren.overhauledchat.client

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.CommandSuggestions
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.narration.NarratedElementType
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.gui.screens.ChatScreen
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.util.Mth
import net.minecraft.util.StringUtil
import org.apache.commons.lang3.StringUtils

class OverhauledChatScreen : ChatScreen {

    private var historyPos: Int = 0
    var commandSuggestions: CommandSuggestions? = null
    private var historyBuffer = ""

    private var initial: String

    companion object {
        private val USAGE_TEXT: Component = Component.translatable("chat_screen.usage")
    }

    constructor(initial: String) : super(initial) {
        this.initial = initial
        dev.wren.overhauledchat.LOGGER.info("aaaaaaa")
    }

    override fun init() {
        this.historyPos = this.minecraft!!.gui.chat.recentChat.size
        this.input = object : EditBox(
            this.minecraft!!.fontFilterFishy,
            4,
            this.height - 12,
            this.width - 4,
            12,
            Component.translatable("chat.editBox")
        ) {
            override fun createNarrationMessage(): MutableComponent {
                return super.createNarrationMessage()
                    .append(this@OverhauledChatScreen.commandSuggestions?.getNarrationMessage())
            }
        }
        this.input.setMaxLength(256)
        this.input.isBordered = false
        this.input.value = this.initial
        this.input.setResponder { value: String? -> this.onEdited(value) }
        this.input.setCanLoseFocus(false)
        this.addWidget<EditBox?>(this.input)
        this.commandSuggestions =
            CommandSuggestions(this.minecraft, this, this.input, this.font, false, false, 1, 10, true, -805306368)
        this.commandSuggestions?.setAllowHiding(false)
        this.commandSuggestions?.updateCommandInfo()
    }

    override fun setInitialFocus() {
        super.setInitialFocus()
    }

    private fun onEdited(value: String?) {
        val s = this.input.value
        this.commandSuggestions!!.setAllowSuggestions(s != this.initial)
        this.commandSuggestions!!.updateCommandInfo()
    }

    override fun resize(minecraft: Minecraft, width: Int, height: Int) {
        super.resize(minecraft, width, height)
    }

    override fun removed() {
        super.removed()
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (this.commandSuggestions!!.keyPressed(keyCode, scanCode, modifiers)) {
            return true
        } else if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true
        } else if (keyCode == 256) {
            this.minecraft!!.setScreen(null as Screen?)
            return true
        } else if (keyCode != 257 && keyCode != 335) {
            if (keyCode == 265) {
                this.moveInHistory(-1)
                return true
            } else if (keyCode == 264) {
                this.moveInHistory(1)
                return true
            } else if (keyCode == 266) {
                this.minecraft!!.gui.chat.scrollChat(this.minecraft!!.gui.chat.linesPerPage - 1)
                return true
            } else if (keyCode == 267) {
                this.minecraft!!.gui.chat.scrollChat(-this.minecraft!!.gui.chat.linesPerPage + 1)
                return true
            } else {
                return false
            }
        } else {
            this.handleChatInput(this.input.value, true)
            this.minecraft!!.setScreen(null as Screen?)
            return true
        }
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        var scrollY = Mth.clamp(scrollY, -1.0, 1.0)
        if (this.commandSuggestions!!.mouseScrolled(scrollY)) {
            return true
        } else {
            if (!hasShiftDown()) {
                scrollY *= 7.0
            }

            this.minecraft!!.gui.chat.scrollChat(scrollY.toInt())
            return true
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (this.commandSuggestions!!.mouseClicked((mouseX.toInt()).toDouble(), (mouseY.toInt()).toDouble(), button)) {
            return true
        } else {
            if (button == 0) {
                val chatcomponent = this.minecraft!!.gui.chat
                if (chatcomponent.handleChatQueueClicked(mouseX, mouseY)) {
                    return true
                }

                val style = this.getComponentStyleAt(mouseX, mouseY)
                if (style != null && this.handleComponentClicked(style)) {
                    this.initial = this.input.value
                    return true
                }
            }

            return if (this.input.mouseClicked(mouseX, mouseY, button)) true else super.mouseClicked(
                mouseX,
                mouseY,
                button
            )
        }
    }

    override fun insertText(text: String, overwrite: Boolean) {
        if (overwrite) {
            this.input.value = text
        } else {
            this.input.insertText(text)
        }
    }

    override fun moveInHistory(msgPos: Int) {
        var i = this.historyPos + msgPos
        val j = this.minecraft!!.gui.chat.recentChat.size
        i = Mth.clamp(i, 0, j)
        if (i != this.historyPos) {
            if (i == j) {
                this.historyPos = j
                this.input.value = this.historyBuffer
            } else {
                if (this.historyPos == j) {
                    this.historyBuffer = this.input.value
                }

                this.input.value = this.minecraft!!.gui.chat.recentChat[i]
                this.commandSuggestions!!.setAllowSuggestions(false)
                this.historyPos = i
            }
        }
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        this.minecraft!!.gui.chat.render(guiGraphics, this.minecraft!!.gui.guiTicks, mouseX, mouseY, true)
        guiGraphics.fill(
            2,
            this.height - 14,
            this.width - 2,
            this.height - 2,
            this.minecraft!!.options.getBackgroundColor(Int.MIN_VALUE)
        )
        this.input.render(guiGraphics, mouseX, mouseY, partialTick)
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        guiGraphics.pose().pushPose()
        guiGraphics.pose().translate(0.0f, 0.0f, 200.0f)
        this.commandSuggestions!!.render(guiGraphics, mouseX, mouseY)
        guiGraphics.pose().popPose()
        val guimessagetag = this.minecraft!!.gui.chat.getMessageTagAt(mouseX.toDouble(), mouseY.toDouble())
        if (guimessagetag?.text() != null) {
            guiGraphics.renderTooltip(this.font, this.font.split(guimessagetag.text(), 210), mouseX, mouseY)
        } else {
            val style = this.getComponentStyleAt(mouseX.toDouble(), mouseY.toDouble())
            if (style?.getHoverEvent() != null) {
                guiGraphics.renderComponentHoverEffect(this.font, style, mouseX, mouseY)
            }
        }
    }

    override fun updateNarrationState(output: NarrationElementOutput) {
        output.add(NarratedElementType.TITLE, this.getTitle())
        output.add(NarratedElementType.USAGE, USAGE_TEXT)
        val s = this.input.value
        if (s.isNotEmpty()) {
            output.nest()
                .add(NarratedElementType.TITLE, Component.translatable("chat_screen.message", *arrayOf<Any>(s)))
        }

    }

    private fun getComponentStyleAt(mouseX: Double, mouseY: Double): Style? {
        return this.minecraft!!.gui.chat.getClickedComponentStyleAt(mouseX, mouseY)
    }

    override fun handleChatInput(message: String, addToRecentChat: Boolean) {
        val message = this.normalizeChatMessage(message)
        if (message.isNotEmpty()) {
            if (addToRecentChat) {
                this.minecraft!!.gui.chat.addRecentChat(message)
            }

            if (message.startsWith("/")) {
                this.minecraft!!.player!!.connection.sendCommand(message.substring(1))
            } else {
                this.minecraft!!.player!!.connection.sendChat(message)
            }
        }
    }

    override fun normalizeChatMessage(message: String): String =
        StringUtil.trimChatMessage(StringUtils.normalizeSpace(message.trim { it <= ' ' }))

}