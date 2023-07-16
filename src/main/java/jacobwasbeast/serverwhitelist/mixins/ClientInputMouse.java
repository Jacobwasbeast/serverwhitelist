package jacobwasbeast.serverwhitelist.mixins;

import jacobwasbeast.serverwhitelist.MainClient;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class ClientInputMouse {

    @Inject(at = @At("HEAD"), method = "onCursorPos", cancellable = true)
    public void ticka(long window, double x, double y, CallbackInfo ci) {
        if (!MainClient.canDoAnything()) {
            if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen || MinecraftClient.getInstance().currentScreen instanceof OptionsScreen || MinecraftClient.getInstance().currentScreen instanceof GameOptionsScreen || MinecraftClient.getInstance().currentScreen instanceof GameMenuScreen) {

            }
            else {
                ci.cancel();
            }
        }

    }
    @Inject(at = @At("HEAD"), method = "onMouseButton", cancellable = true)
    public void tickb(long window, int button, int action, int mods, CallbackInfo ci) {
        if (!MainClient.canDoAnything()) {
            if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen || MinecraftClient.getInstance().currentScreen instanceof OptionsScreen || MinecraftClient.getInstance().currentScreen instanceof GameOptionsScreen || MinecraftClient.getInstance().currentScreen instanceof GameMenuScreen) {

            }
            else {
                ci.cancel();
            }
        }
    }
    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    public void tickc(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (!MainClient.canDoAnything()) {
            if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen || MinecraftClient.getInstance().currentScreen instanceof OptionsScreen) {

            }
            else {
                ci.cancel();
            }
        }
    }

}
