package me.tailer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {


    public FileConfiguration configFile;

    public List<UUID> disabledStaffChat = new ArrayList<>();
    public List<UUID> defaultSendToStaffChat = new ArrayList<>();

    public boolean staffChatEnabled;
    public boolean broadcastEnabled;
    public boolean playerLoggingJoinEnabled;
    public boolean playerLoggingLeaveEnabled;
    public boolean playerLoggingFirstTimeEnabled;

    public String broadcastPermission;
    public String staffChatPermission;
    public String messagePermission;

    public String noPermission;

    public String staffChatQuickAccess;
    public String staffChatFormat;
    public String staffChatDisabledMessage;

    public Utils(FileConfiguration configFile) {
        this.configFile = configFile;

        staffChatEnabled = configFile.getBoolean("staff-chat.enabled");
        broadcastEnabled = configFile.getBoolean("broadcast.enabled");
        playerLoggingJoinEnabled = configFile.getBoolean("player-logging.join.enabled");
        playerLoggingLeaveEnabled = configFile.getBoolean("player-logging.leave.enabled");
        playerLoggingFirstTimeEnabled = configFile.getBoolean("player-logging.first-time.enabled");

        broadcastPermission = configFile.getString("permissions.broadcast-msg");
        staffChatPermission = configFile.getString("permissions.staff-chat");
        messagePermission = configFile.getString("permissions.msg");

        noPermission = colorize(configFile.getString("error-msgs.no-permission"));

        staffChatQuickAccess = configFile.getString("staff-chat.quick-msg");
        staffChatFormat = colorize(configFile.getString("staff-chat.msg-format"));

    }

    public String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void sendStaffChatMessage(String msg, Player originalSender) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(staffChatPermission) && !disabledStaffChat.contains(player.getUniqueId())) {
                player.sendMessage(staffChatFormat
                        .replace("%player_name%", originalSender.getName())
                        .replace("%player_displayname%", originalSender.getDisplayName())
                        .replace("%message%", colorize(msg)));
            }
        }
    }

    public void sendStaffChatMessage(String msg, CommandSender originalSender) {
        boolean isConsoleSending = originalSender instanceof ConsoleCommandSender;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(staffChatPermission) && !disabledStaffChat.contains(player.getUniqueId())) {

                if (isConsoleSending) {
                    player.sendMessage(staffChatFormat
                            .replace("%player_name%", originalSender.getName())
                            .replace("%player_displayname%", originalSender.getName())
                            .replace("%message%", colorize(msg)));
                } else {
                    Player originalPlayer = (Player) originalSender;
                    player.sendMessage(staffChatFormat
                            .replace("%player_name%", originalPlayer.getName())
                            .replace("%player_displayname%", originalPlayer.getDisplayName())
                            .replace("%message%", colorize(msg)));
                }
            }
        }
    }

}