package jacobwasbeast.serverwhitelist.config;

import io.netty.buffer.Unpooled;
import jacobwasbeast.serverwhitelist.Main;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;


@me.shedaniel.autoconfig.annotation.Config(name = "serverwhitelist")
public class Config implements ConfigData {
    public int neededPlayers = 4;
}