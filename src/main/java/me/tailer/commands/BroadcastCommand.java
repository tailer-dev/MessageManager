package me.tailer.commands;

import me.tailer.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    Utils utils;
    public BroadcastCommand(Utils utils) {
        this.utils = utils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (label.equalsIgnoreCase("broadcast")) {
            if (sender.hasPermission(utils.broadcastPermission)) {

                if (args.length == 0) {

                } else {

                }

            } else {
                sender.sendMessage(utils.noPermission);
            }

        }


       return true;
    }

}
