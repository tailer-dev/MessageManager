package me.tailer.events;

import me.tailer.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoggingEvent implements Listener {

    Utils utils;

    public PlayerLoggingEvent(Utils utils) {
        this.utils = utils;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore() && utils.playerLoggingFirstTimeEnabled) {
            Bukkit.broadcastMessage(utils.colorize(utils.configFile.getString("player-logging.first-time.msg")
                    .replace("%player_name%", player.getName())
                    .replace("%player_displayname%", player.getDisplayName())));
            return;
        }
        if (!utils.playerLoggingJoinEnabled) return;
        event.setJoinMessage(utils.colorize(utils.configFile.getString("player-logging.join.msg")
                .replace("%player_name%", player.getName()).replace("%player_displayname%", player.getDisplayName())));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!utils.playerLoggingLeaveEnabled) return;
        event.setQuitMessage(utils.colorize(utils.configFile.getString("player-logging.leave.msg")
                .replace("%player_name%", player.getName())
                .replace("%player_displayname%", player.getDisplayName())));
    }


}
