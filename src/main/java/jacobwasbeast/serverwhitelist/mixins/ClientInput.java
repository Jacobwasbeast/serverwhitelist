package jacobwasbeast.serverwhitelist.mixins;

import jacobwasbeast.serverwhitelist.MainClient;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class ClientInput {

    @Inject(at = @At("HEAD"), method = "onKey", cancellable = true)
    public void ticka(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
    if (!MainClient.canDoAnything()) {
            if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen || MinecraftClient.getInstance().currentScreen instanceof OptionsScreen) {

            }
            else {
                if (key != InputUtil.GLFW_KEY_ESCAPE) {
                    ci.cancel();
                }
            }
        }
    }


}
