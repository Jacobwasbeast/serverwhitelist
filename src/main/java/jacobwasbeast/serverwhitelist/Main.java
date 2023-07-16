package jacobwasbeast.serverwhitelist;

import com.mojang.authlib.GameProfile;
import jacobwasbeast.serverwhitelist.config.Config;
import jacobwasbeast.serverwhitelist.mixins.Server;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main implements net.fabricmc.api.ModInitializer {
    public static boolean doa = false;
    public static boolean first = false;
    public static MinecraftServer server = null;
    public static HashMap<UUID, Vec3d> pos = new HashMap<UUID, net.minecraft.util.math.Vec3d>();
    public static Config config;
    public static Identifier id = new Identifier("serverwhitelist", "sync_config");
    @Override
    public void onInitialize() {
        System.out.println("Hello Fabric world!");
        AutoConfig.register(Config.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Config.class).getConfig();
        // if server
        ServerPlayNetworking.registerGlobalReceiver(id, (server, player, handler, buf, responseSender) -> {
            Main.syncToPlayer(player);
            System.out.println("Sending config to client: " + config.neededPlayers);
        });
    }

    public static boolean canDoAnything(MinecraftServer server) {
        if (server.getPlayerManager().getPlayerList().size() < Main.config.neededPlayers) {
            return false;
        }
        else {
            return true;
        }
    }
    public static void syncToPlayer(ServerPlayerEntity player) {
        PacketByteBuf data = PacketByteBufs.create();
        data.writeInt(Main.config.neededPlayers);  // Add config values to the data buffer
        ServerPlayNetworking.send((ServerPlayerEntity) player, Main.id, data);
        System.out.println("Sent config to player: " + Main.config.neededPlayers);
    }
}
