package me.tailer;

import me.tailer.commands.BroadcastCommand;
import me.tailer.commands.MessageCmd;
import me.tailer.commands.StaffCommand;
import me.tailer.events.DeathByMob;
import me.tailer.events.PlayerLoggingEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;

public final class MessageManager extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        Utils utils = new Utils(getConfig());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerLoggingEvent(utils), this);

        if (utils.staffChatEnabled) {
            StaffCommand staffCommand = new StaffCommand(utils);

            PluginCommand staffChatCommand = getCommand("sc");
            staffChatCommand.setExecutor(staffCommand);
            staffChatCommand.setAliases(Collections.singletonList("staffchat"));

            PluginCommand staffChatViewCommand = getCommand("scview");
            staffChatViewCommand.setExecutor(staffCommand);
            staffChatViewCommand.setAliases(Collections.singletonList("staffchatview"));

            PluginCommand staffChatToggleCommand = getCommand("sctoggle");
            staffChatToggleCommand.setExecutor(staffCommand);
            staffChatToggleCommand.setAliases(Collections.singletonList("staffchattoggle"));

            getServer().getPluginManager().registerEvents(staffCommand, this);
        }

        if (utils.playerLoggingJoinEnabled || utils.playerLoggingFirstTimeEnabled || utils.playerLoggingLeaveEnabled) {
            getServer().getPluginManager().registerEvents(new PlayerLoggingEvent(utils), this);
        }

        if (utils.broadcastEnabled) {
            PluginCommand broadcastCommand = getCommand("broadcast");
            broadcastCommand.setExecutor(new BroadcastCommand(utils));
            broadcastCommand.setAliases(Collections.singletonList("bc"));
        }

        if (utils.deathMessagesEnabled) {
            getServer().getPluginManager().registerEvents(new DeathByMob(utils), this);
        }

        if (utils.playerMessagingEnabled) {
            MessageCmd messageCmd = new MessageCmd(utils);
            PluginCommand messageCommand = getCommand("msg");
            messageCommand.setExecutor(messageCmd);
            messageCommand.setAliases(Arrays.asList("message", "m", "whisper"));

            PluginCommand replyCommand = getCommand("reply");
            replyCommand.setExecutor(messageCmd);
            replyCommand.setAliases(Arrays.asList("r", "respond"));

        }



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
