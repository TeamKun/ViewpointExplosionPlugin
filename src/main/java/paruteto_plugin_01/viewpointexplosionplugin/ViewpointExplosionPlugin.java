package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ViewpointExplosionPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("プラグインが開始しました");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("プラグインが停止しました");
    }
}
