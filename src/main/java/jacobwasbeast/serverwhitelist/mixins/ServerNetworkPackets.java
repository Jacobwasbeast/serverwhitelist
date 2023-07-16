package jacobwasbeast.serverwhitelist.mixins;

import jacobwasbeast.serverwhitelist.Main;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerHandshakeNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerNetworkPackets {
    @Shadow @NotNull public abstract MinecraftServer getServer();

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void noTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (!Main.canDoAnything(this.getServer())) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tickChunk", cancellable = true)
    public void noTickChunk(CallbackInfo ci) {
        if (!Main.canDoAnything(this.getServer())) {
            ci.cancel();
        }
    }

}
