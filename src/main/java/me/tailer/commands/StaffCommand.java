package me.tailer.commands;

import me.tailer.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffCommand implements CommandExecutor {


    Utils utils;
    public StaffCommand(Utils utils) {
        this.utils = utils;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (label.equalsIgnoreCase("sc") || label.equalsIgnoreCase("staffchat")) {
            if (sender.hasPermission(utils.staffChatPermission)) {
                if (utils.staffChatEnabled) {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (String arg : args) {
                        stringBuilder.append(arg);
                    }
                    utils.sendStaffChatMessage(stringBuilder.toString(), sender);
                }
            } else {
                sender.sendMessage(utils.noPermission);
            }
            return true;
        }

        if (label.equalsIgnoreCase("sctoggle") || label.equalsIgnoreCase("staffchattoggle")) {

            if (sender.hasPermission(utils.staffChatPermission)) {



            }

        }


        return true;
    }


    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (event.getMessage().startsWith(utils.staffChatQuickAccess) && utils.staffChatEnabled && player.hasPermission(utils.staffChatPermission)) {
            message = message.substring(1);
            utils.sendStaffChatMessage(message, player);
        }

        if (utils.defaultSendToStaffChat.contains(player.getUniqueId())) {
            utils.sendStaffChatMessage(message, player);
        }

    }

}