package me.tailer.events;

import me.tailer.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathByMob implements Listener {

    Utils utils;

    public DeathByMob(Utils utils) {
        this.utils = utils;
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!utils.deathMessagesEnabled) return;
        Player deadPlayer = event.getEntity();
        Player killerPlayer = event.getEntity().getKiller();
        if (killerPlayer == null) {
            event.setDeathMessage("");
            return;
        }

        event.setDeathMessage(utils.playerDeathMsg
                .replace("%player_name%", deadPlayer.getDisplayName())
                .replace("%player_displayname", deadPlayer.getDisplayName())
                .replace("%killer_name%", killerPlayer.getName())
                .replace("%killer_displayname%", killerPlayer.getDisplayName()));

    }

    @EventHandler
    public void onDeathByMob(EntityDamageByEntityEvent event) {
        if (!utils.deathMessagesEnabled) return;
        if (!(event.getDamager().getType() == EntityType.PLAYER) && event.getEntity().getType() == EntityType.PLAYER) {
            Entity entity = event.getDamager();
            Player p = (Player) event.getEntity();

            if (p.getHealth() <= event.getDamage()) {

                if (utils.playerDeathMsg.contains("%killer_displayname%")) {
                    if (entity.getCustomName() == null) {
                        Bukkit.getConsoleSender().sendMessage("§4§lHello! You have tried to use the displayname of the killer, however they do not have a custom name and is throwing a null error. If you want to use this, the mob in question MUST have a custom name. Otherwise, turn back to %killer_name%");
                        Bukkit.broadcastMessage(utils.playerDeathMsg
                                .replace("%player_name%", p.getDisplayName())
                                .replace("%player_displayname", p.getDisplayName())
                                .replace("%killer_name%", entity.getName())
                                .replace("%killer_displayname%", entity.getName()));
                    } else {
                        Bukkit.broadcastMessage(utils.playerDeathMsg
                                .replace("%player_name%", p.getDisplayName())
                                .replace("%player_displayname", p.getDisplayName())
                                .replace("%killer_name%", entity.getName())
                                .replace("%killer_displayname%", entity.getCustomName()));
                    }
                } else {
                    Bukkit.broadcastMessage(utils.playerDeathMsg
                            .replace("%player_name%", p.getDisplayName())
                            .replace("%player_displayname", p.getDisplayName())
                            .replace("%killer_name%", entity.getName())
                            .replace("%killer_displayname%", entity.getName()));
                }
            }
        }

    }

}