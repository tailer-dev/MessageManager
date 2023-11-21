package me.tailer.commands;

import me.tailer.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class MessageCmd implements CommandExecutor {

    Utils utils;
    private HashMap<UUID, UUID> playerReply = new HashMap<>();
    public MessageCmd(Utils utils) {
        this.utils = utils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("You can't use this command");
            return true;
        }

        Player player = (Player) sender;
        if (label.equals("msg") || label.equals("whisper") || label.equals("m") || label.equalsIgnoreCase("message")) {
            if (!utils.playerMessagingEnabled) return true;

            if (!player.hasPermission(utils.messagePermission)) {
                player.sendMessage(utils.noPermission);
                return true;
            }
            if (args.length < 1) {
                player.sendMessage(utils.notEnoughArgs);
                return true;
            }

            Player targetMessagePlayer = Bukkit.getPlayer(args[0]);
            if (targetMessagePlayer == null) {
                player.sendMessage(utils.playerOffline);
            } else {

                if (args.length > 1) {

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        stringBuilder.append(args[i]).append(" ");
                    }

                    playerReply.put(targetMessagePlayer.getUniqueId(), player.getUniqueId());

                    targetMessagePlayer.sendMessage(utils.messagingFormatToPlayer
                            .replace("%sender%", player.getName())
                            .replace("%sender_displayname%", player.getDisplayName())
                            .replace("%message%", utils.colorize(stringBuilder.toString())));
                    player.sendMessage(utils.messagingFormatFromSelf
                            .replace("%receiver%", targetMessagePlayer.getName())
                            .replace("%receiver_displayname%", targetMessagePlayer.getDisplayName())
                            .replace("%message%", utils.colorize(stringBuilder.toString())));

                } else {
                    player.sendMessage(utils.notEnoughArgs);
                }

            }
            return true;

        }


        if (label.equalsIgnoreCase("r") || label.equalsIgnoreCase("reply") || label.equalsIgnoreCase("respond")) {
            if (!utils.playerMessagingEnabled) return true;
            if (!player.hasPermission(utils.messagePermission)) {
                player.sendMessage(utils.noPermission);
                return true;
            }

            if (playerReply.containsKey(player.getUniqueId())) {

                if (args.length == 0) {
                    player.sendMessage(utils.notEnoughArgs);
                    return true;
                }

                Player targetMessagePlayer = Bukkit.getPlayer(playerReply.get(player.getUniqueId()));

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    stringBuilder.append(args[i]).append(" ");
                }

                playerReply.put(targetMessagePlayer.getUniqueId(), player.getUniqueId());

                targetMessagePlayer.sendMessage(utils.messagingFormatToPlayer
                        .replace("%sender%", player.getName())
                        .replace("%sender_displayname%", player.getDisplayName())
                        .replace("%message%", utils.colorize(stringBuilder.toString())));
                player.sendMessage(utils.messagingFormatFromSelf
                        .replace("%receiver%", targetMessagePlayer.getName())
                        .replace("%receiver_displayname%", targetMessagePlayer.getDisplayName())
                        .replace("%message%", utils.colorize(stringBuilder.toString())));

            } else {
                player.sendMessage(utils.messagingNooneToReplyTo);
            }
            return true;
        }




        return true;
    }




}