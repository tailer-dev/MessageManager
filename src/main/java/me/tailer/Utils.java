package me.tailer;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Utils {


    public FileConfiguration configFile;

    public boolean staffChatEnabled;
    public boolean broadcastEnabled;
    public boolean playerLoggingJoinEnabled;
    public boolean playerLoggingLeaveEnabled;
    public boolean playerLoggingFirstTimeEnabled;

    public String broadcastPermission;
    public String staffChatPermission;
    public String messagePermission;

    public String noPermission;

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

    }

    public String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }




}