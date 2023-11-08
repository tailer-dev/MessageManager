package me.tailer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathByMob implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

    }

    @EventHandler
    public void onDeathByMob(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {
            Player p = ((Player) event.getEntity()).getPlayer();

        }

    }

}