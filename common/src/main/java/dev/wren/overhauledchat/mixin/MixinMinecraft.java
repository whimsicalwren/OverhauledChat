package dev.wren.overhauledchat.mixin;

import dev.wren.overhauledchat.client.OverhauledChatScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Redirect(
            method = "openChatScreen",
            at = @At(
                    value = "NEW",
                    target = "(Ljava/lang/String;)Lnet/minecraft/client/gui/screens/ChatScreen;"
            )
    )
    private ChatScreen overhauledchat$openChatScreen(String initial) {
        return new OverhauledChatScreen(initial);
    }
}
