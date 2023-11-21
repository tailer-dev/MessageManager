package me.tailer.commands;

import me.tailer.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {

    Utils utils;
    public BroadcastCommand(Utils utils) {
        this.utils = utils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (label.equalsIgnoreCase("broadcast") || label.equalsIgnoreCase("bc")) {
            if (!utils.broadcastEnabled) return true;
            if (sender.hasPermission(utils.broadcastPermission)) {

                if (args.length == 0) {
                    sender.sendMessage(utils.notEnoughArgs);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (String arg : args) {
                        stringBuilder.append(arg).append(" ");
                    }
                    if (sender instanceof ConsoleCommandSender) {
                        Bukkit.broadcastMessage(utils.colorize(utils.broadcastMessage
                                .replace("%player_name%", sender.getName())
                                .replace("%player_displayname%", sender.getName())
                                .replace("%message%", utils.colorize(stringBuilder.toString()))));
                    } else {
                        Bukkit.broadcastMessage(utils.colorize(utils.broadcastMessage
                                .replace("%player_name%", sender.getName())
                                .replace("%player_displayname%", ((Player) sender).getDisplayName())
                                .replace("%message%", utils.colorize(stringBuilder.toString()))));
                    }
                }
            } else {
                sender.sendMessage(utils.noPermission);
            }

        }

       return true;
    }

}
