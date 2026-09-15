package dev.wren.overhauledchat.mixin;

import dev.wren.overhauledchat.client.OverhauledChatComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class MixinGui {

    @Redirect(
            method = "<init>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/client/Minecraft;)Lnet/minecraft/client/gui/components/ChatComponent;"
            )
    )
    private ChatComponent overhauledchat$init(Minecraft minecraft) {
        return new OverhauledChatComponent(minecraft);
    }
}
