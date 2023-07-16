package jacobwasbeast.serverwhitelist.mixins;

import jacobwasbeast.serverwhitelist.Main;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.FabricUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.BooleanSupplier;

@Mixin(net.minecraft.server.MinecraftServer.class)
public abstract class Server {
    @Shadow public abstract PlayerManager getPlayerManager();

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void ticka(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (this.getPlayerManager().getPlayerList().size()<Main.config.neededPlayers) {
            for (int i = 0; i < this.getPlayerManager().getPlayerList().size(); i++) {
                if (Main.pos.containsKey(this.getPlayerManager().getPlayerList().get(i).getUuid())) {
                    this.getPlayerManager().getPlayerList().get(i).teleport(Main.pos.get(this.getPlayerManager().getPlayerList().get(i).getUuid()).getX(), Main.pos.get(this.getPlayerManager().getPlayerList().get(i).getUuid()).getY(), Main.pos.get(this.getPlayerManager().getPlayerList().get(i).getUuid()).getZ());
                }
                else {
                    Main.syncToPlayer(this.getPlayerManager().getPlayerList().get(i));
                    Main.pos.put(this.getPlayerManager().getPlayerList().get(i).getUuid(), this.getPlayerManager().getPlayerList().get(i).getPos());
                }
                if (new Random().nextInt(2000)==1) {
                    this.getPlayerManager().getPlayerList().get(i).sendMessage(Text.literal("§6§lYou are not allowed to play on this server until there are at least " + Main.config.neededPlayers + " players online.  There are " + this.getPlayerManager().getPlayerList().size() + " including yourself."), false);
                }
                this.getPlayerManager().getPlayerList().get(i).setInvulnerable(true);
                if (Main.server==null) {
                    Main.server = this.getPlayerManager().getPlayerList().get(i).world.getServer();;
                }
            }
            Main.doa = true;

        }
        else {
            if (Main.doa) {
                for (int i = 0; i < this.getPlayerManager().getPlayerList().size(); i++) {
                    this.getPlayerManager().getPlayerList().get(i).sendMessage(Text.literal("You are now allowed to play on this server."), false);
                    this.getPlayerManager().getPlayerList().get(i).setInvulnerable(false);
                }
                Main.doa = false;
                Main.pos.clear();
                Main.server = null;
            }
        }
    }

}
