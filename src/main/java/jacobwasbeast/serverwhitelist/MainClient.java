package jacobwasbeast.serverwhitelist;

import jacobwasbeast.serverwhitelist.config.Config;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {
    public static boolean first = true;
    @Override
    public void onInitializeClient() {
        System.out.println("Hello Fabric world!");
        ClientPlayNetworking.registerGlobalReceiver(Main.id, (client, handler, buf, responseSender) -> {
            int neededPlayers = buf.readInt();  // Read config values from the data buffer
            client.execute(() -> {
                Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
                config.neededPlayers = neededPlayers;
                System.out.println("Received config from server: " + config.neededPlayers);
            });
        });
    }
    public static boolean canDoAnything() {
        if (MinecraftClient.getInstance().isInSingleplayer()==false) {
            if (MinecraftClient.getInstance().getNetworkHandler() != null) {
                if (MinecraftClient.getInstance().getNetworkHandler().getPlayerList().size() < Main.config.neededPlayers) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        else {
            return true;
        }
        return true;
    }
}
