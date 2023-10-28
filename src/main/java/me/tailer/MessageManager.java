package me.tailer;

import org.bukkit.plugin.java.JavaPlugin;

public final class MessageManager extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        Utils utils = new Utils(getConfig());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
