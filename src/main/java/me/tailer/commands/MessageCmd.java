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


        if (label.equals("msg") || label.equals("whisper") || label.equals("m")) {
            Player player = (Player) sender;
            Player targetMessagePlayer = Bukkit.getPlayer(args[0]);
            if (targetMessagePlayer == null) {
                sender.sendMessage(utils.playerOffline);
            } else {

                if (args.length > 1) {

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        stringBuilder.append(args[i]).append(" ");
                    }

                    targetMessagePlayer.sendMessage(utils.messagingFormatToPlayer
                            .replace("%sender%", player.getName())
                            .replace("%sender_displayname%", player.getDisplayName())
                            .replace("%message%", utils.colorize(stringBuilder.toString())));
                    player.sendMessage(utils.messagingFormatFromSelf
                            .replace("%receiver%", targetMessagePlayer.getName())
                            .replace("%receiver_displayname%", targetMessagePlayer.getDisplayName())
                            .replace("%message%", utils.colorize(stringBuilder.toString())));

                } else {
                    sender.sendMessage(utils.notEnoughArgs);
                }

            }

        }


        return true;
    }




}