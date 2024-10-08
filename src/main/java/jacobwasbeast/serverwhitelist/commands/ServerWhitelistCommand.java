package jacobwasbeast.serverwhitelist.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import jacobwasbeast.serverwhitelist.Main;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class ServerWhitelistCommand implements CommandRegistrationCallback {
    public ServerWhitelistCommand(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment environment) {
        register(serverCommandSourceCommandDispatcher, commandRegistryAccess, environment);
    }

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("serverwhitelist")
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("players", IntegerArgumentType.integer())
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayerEntity serverPlayerEntity) {
                                        if (serverPlayerEntity.hasPermissionLevel(4)) {
                                            Main.config.neededPlayers = IntegerArgumentType.getInteger(context, "players");
                                            AutoConfig.getConfigHolder(Main.config.getClass()).save();
                                            for (ServerPlayerEntity player : serverPlayerEntity.getServer().getPlayerManager().getPlayerList()) {
                                                Main.syncToPlayer(player);
                                            }
                                            serverPlayerEntity.sendMessage(Text.literal("Set needed players to " + Main.config.neededPlayers), false);
                                            return 1;
                                        } else {
                                            serverPlayerEntity.sendMessage(Text.literal("You do not have permission to run this command"), false);
                                            return 0;
                                        }
                                    } else {
                                        Main.config.neededPlayers = IntegerArgumentType.getInteger(context, "players");
                                        AutoConfig.getConfigHolder(Main.config.getClass()).save();
                                        for (ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
                                            Main.syncToPlayer(player);
                                        }
                                        context.getSource().sendFeedback((Supplier<Text>) Text.literal("Set needed players to " + Main.config.neededPlayers), false);
                                        return 1;
                                    }
                                })
                        )
                )
        );
    }
}
