package me.tailer.commands;

import me.tailer.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffCommand implements CommandExecutor, Listener {


    Utils utils;
    public StaffCommand(Utils utils) {
        this.utils = utils;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (label.equalsIgnoreCase("sc") || label.equalsIgnoreCase("staffchat")) {
            if (!utils.staffChatEnabled) return true;
            if (sender.hasPermission(utils.staffChatPermission) || sender instanceof ConsoleCommandSender) {
                StringBuilder stringBuilder = new StringBuilder();

                for (String arg : args) {
                    stringBuilder.append(arg).append(" ");
                }
                utils.sendStaffChatMessage(stringBuilder.toString(), sender);
            } else {
                sender.sendMessage(utils.noPermission);
            }
            return true;
        }

        if (label.equalsIgnoreCase("scview") || label.equalsIgnoreCase("staffchatview")) {
            if (!utils.staffChatEnabled) return true;
            if (sender.hasPermission(utils.staffChatPermission)) {

                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (utils.disabledStaffChat.contains(player.getUniqueId())) {
                        utils.disabledStaffChat.remove(player.getUniqueId());
                        player.sendMessage(utils.staffChatViewEnabledMessage);
                    } else {
                        utils.disabledStaffChat.add(player.getUniqueId());
                        player.sendMessage(utils.staffChatViewDisabledMessage);
                    }
                } else {
                    sender.sendMessage("You can't use this command");
                }
            } else {
                sender.sendMessage(utils.noPermission);
            }
        }

        if (label.equalsIgnoreCase("sctoggle") || label.equalsIgnoreCase("staffchattoggle")) {
            if (!utils.staffChatEnabled) return true;
            if (sender.hasPermission(utils.staffChatPermission)) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (utils.defaultSendToStaffChat.contains(player.getUniqueId())) {
                        utils.defaultSendToStaffChat.remove(player.getUniqueId());
                        player.sendMessage(utils.staffChatDefaultDisabledMessage);
                    } else {
                        utils.defaultSendToStaffChat.add(player.getUniqueId());
                        player.sendMessage(utils.staffChatDefaultEnabledMessage);
                    }
                } else {
                    sender.sendMessage("You can't use this command");
                }
            } else {
                sender.sendMessage(utils.noPermission);
            }


        }
        return true;
    }


    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (event.getMessage().startsWith(utils.staffChatQuickAccess) && utils.staffChatEnabled && player.hasPermission(utils.staffChatPermission)) {
            event.setCancelled(true);
            message = message.substring(1);
            utils.sendStaffChatMessage(message, player);
        }

        if (utils.defaultSendToStaffChat.contains(player.getUniqueId())) {
            event.setCancelled(true);
            utils.sendStaffChatMessage(message, player);
        }

    }

}