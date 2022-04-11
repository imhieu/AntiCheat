package com.lighter.data.manager;

import com.lighter.main.*;
import com.lighter.data.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;
import com.lighter.netty.*;
import io.netty.channel.*;
import com.google.common.collect.*;
import net.minecraft.server.v1_8_R3.*;

public class PlayerManager
{
    private static PlayerManager instance;
    private final Main plugin;
    private final Map<UUID, PlayerData> players;
    
    public Map<UUID, PlayerData> getPlayers() {
        return this.players;
    }
    
    public static void enable(final Main main) {
        PlayerManager.instance = new PlayerManager(main);
        final Iterator<Player> iterator = Bukkit.getOnlinePlayers().iterator();
        while (iterator.hasNext()) {
            PlayerManager.instance.inject(iterator.next());
        }
    }
    
    public PlayerData getPlayer(final Player player) {
        return this.players.get(player.getUniqueId());
    }
    
    public void inject(final Player player) {
        final PlayerData playerData = new PlayerData(player, this.plugin.getTypeLoader().loadChecks());
        final Channel channel = playerData.getEntityPlayer().playerConnection.networkManager.channel;
        this.players.put(player.getUniqueId(), playerData);
        if (channel != null) {
            channel.pipeline().addBefore("packet_handler", "lighter-encoder", (ChannelHandler)new PacketEncoder(playerData));
            channel.pipeline().addBefore("packet_handler", "lighter-decoder", (ChannelHandler)new PacketDecoder(playerData));
        }
    }
    
    public static void disable() {
        final Iterator<Player> iterator = Bukkit.getOnlinePlayers().iterator();
        while (iterator.hasNext()) {
            PlayerManager.instance.uninject(iterator.next());
        }
        PlayerManager.instance = null;
    }
    
    public PlayerManager(final Main plugin) {
        this.players = (Map<UUID, PlayerData>)Maps.newConcurrentMap();
        this.plugin = plugin;
    }
    
    public Main getPlugin() {
        return this.plugin;
    }
    
    public static PlayerManager getInstance() {
        return PlayerManager.instance;
    }
    
    public void uninject(final Player player) {
        final PlayerData playerData = this.players.remove(player.getUniqueId());
        if (playerData != null) {
            playerData.setEnabled(false);
            final PlayerConnection playerConnection = playerData.getEntityPlayer().playerConnection;
            if (playerConnection != null && !playerConnection.isDisconnected()) {
                final Channel channel = playerConnection.networkManager.channel;
                try {
                    channel.pipeline().remove("lighter-encoder");
                    channel.pipeline().remove("lighter-decoder");
                }
                catch (Throwable t) {}
            }
        }
    }
}
