package jacobwasbeast.serverwhitelist.mixins;

import jacobwasbeast.serverwhitelist.Main;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(Entity.class)
public abstract class ServerNetworkPacketsEntity {
    @Shadow @NotNull public abstract MinecraftServer getServer();

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void noTick(CallbackInfo ci) {
        if (!Main.canDoAnything(this.getServer())) {
            ci.cancel();
        }
    }


}
