package jacobwasbeast.serverwhitelist.mixins;

import jacobwasbeast.serverwhitelist.Main;
import jacobwasbeast.serverwhitelist.MainClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class Client {
    @Shadow @Nullable private ClientWorld world;

    @Shadow protected abstract void renderEndSky(MatrixStack matrices);

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void ticka(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        if (!MainClient.canDoAnything()) {
            ci.cancel();
            if (MainClient.first) {
                MainClient.first = false;
                ClientPlayNetworking.send(Main.id, PacketByteBufs.create());
            }
            if (MinecraftClient.getInstance().currentScreen instanceof DownloadingTerrainScreen || MinecraftClient.getInstance().currentScreen instanceof InventoryScreen) {
                MinecraftClient.getInstance().setScreen(null);
            }

            renderEndSky(matrices);
        }
        else {
            MainClient.first = true;
        }

    }


}
