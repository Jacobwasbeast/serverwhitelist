package jacobwasbeast.serverwhitelist.mixins;


import jacobwasbeast.serverwhitelist.Main;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.server.PlayerManager.class)
public class PlayerManager {
    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    public void sendPlayerConfig(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        System.out.println("Player connected: " + player.getName());
        Main.syncToPlayer(player);
    }
}
