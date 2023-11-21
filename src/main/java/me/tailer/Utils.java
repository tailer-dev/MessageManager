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
    public boolean deathMessagesEnabled;
    public boolean playerMessagingEnabled;

    public String broadcastPermission;
    public String staffChatPermission;
    public String messagePermission;

    public String noPermission;
    public String notEnoughArgs;
    public String playerOffline;

    public String staffChatQuickAccess;
    public String staffChatFormat;
    public String staffChatViewDisabledMessage;
    public String staffChatViewEnabledMessage;
    public String staffChatDefaultEnabledMessage;
    public String staffChatDefaultDisabledMessage;

    public String broadcastMessage;

    public String playerDeathMsg;

    public String messagingFormatFromSelf;
    public String messagingFormatToPlayer;
    public String messagingNooneToReplyTo;

    public Utils(FileConfiguration configFile) {
        this.configFile = configFile;

        staffChatEnabled = configFile.getBoolean("staff-chat.enabled");
        broadcastEnabled = configFile.getBoolean("broadcast.enabled");
        playerLoggingJoinEnabled = configFile.getBoolean("player-logging.join-enabled");
        playerLoggingLeaveEnabled = configFile.getBoolean("player-logging.leave-enabled");
        playerLoggingFirstTimeEnabled = configFile.getBoolean("player-logging.first-time-enabled");
        deathMessagesEnabled = configFile.getBoolean("player-death-msgs.enabled");
        playerMessagingEnabled = configFile.getBoolean("messaging.enabled");

        broadcastPermission = configFile.getString("permissions.broadcast-msg");
        staffChatPermission = configFile.getString("permissions.staff-chat");
        messagePermission = configFile.getString("permissions.msg");

        noPermission = colorize(configFile.getString("error-msgs.no-permission"));
        notEnoughArgs = colorize(configFile.getString("error-msgs.not-enough-args"));
        playerOffline = colorize(configFile.getString("error-msgs.player-offline"));

        staffChatQuickAccess = configFile.getString("staff-chat.quick-msg");
        staffChatFormat = colorize(configFile.getString("staff-chat.msg-format"));

        staffChatViewDisabledMessage = colorize(configFile.getString("staff-chat.toggled-view-off"));
        staffChatViewEnabledMessage = colorize(configFile.getString("staff-chat.toggled-view-on"));

        staffChatDefaultEnabledMessage = colorize(configFile.getString("staff-chat.toggled-on"));
        staffChatDefaultDisabledMessage = colorize(configFile.getString("staff-chat.toggled-off"));

        broadcastMessage = configFile.getString("broadcast.msg");

        playerDeathMsg = colorize(configFile.getString("player-death-msgs.death-msg"));

        messagingFormatFromSelf = colorize(configFile.getString("messaging.format-from-player"));
        messagingFormatToPlayer = colorize(configFile.getString("messaging.format-to-player"));
        messagingNooneToReplyTo = colorize(configFile.getString("messaging.no-one-to-reply-to"));

    }

    public String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void sendStaffChatMessage(String msg, Player originalSender) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(staffChatPermission) && !disabledStaffChat.contains(player.getUniqueId())) {
                String msgToSend = staffChatFormat
                        .replace("%player_name%", originalSender.getName())
                        .replace("%player_displayname%", originalSender.getDisplayName())
                        .replace("%message%", colorize(msg));
                player.sendMessage(msgToSend);
                Bukkit.getConsoleSender().sendMessage(msgToSend);
            }
        }
    }

    public void sendStaffChatMessage(String msg, CommandSender originalSender) {
        boolean isConsoleSending = originalSender instanceof ConsoleCommandSender;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(staffChatPermission) && !disabledStaffChat.contains(player.getUniqueId())) {

                if (isConsoleSending) {
                    String msgToSend = staffChatFormat
                            .replace("%player_name%", originalSender.getName())
                            .replace("%player_displayname%", originalSender.getName())
                            .replace("%message%", colorize(msg));
                    player.sendMessage(msgToSend);
                    Bukkit.getConsoleSender().sendMessage(msgToSend);
                } else {
                    Player originalPlayer = (Player) originalSender;
                    String msgToSend = staffChatFormat
                            .replace("%player_name%", originalPlayer.getName())
                            .replace("%player_displayname%", originalPlayer.getDisplayName())
                            .replace("%message%", colorize(msg));
                    player.sendMessage(msgToSend);
                    Bukkit.getConsoleSender().sendMessage(msgToSend);
                }
            }
        }

    }

}