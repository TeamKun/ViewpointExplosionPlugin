package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ViewpointExplosionPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Start ViewpointExplosionPlugin");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        new BukkitRunnable() {
            @Override
            public void run() {
                ViewpointExplosion.instance.update();
            }
        }.runTaskTimer(this, 0, 1);
        Vpex vpex = new Vpex(this);
        ViewpointExplosion.instance.resetPlayerList();
    }

    @Override
    public void onDisable() {
        getLogger().info("Finished ViewpointExplosionPlugin");
    }

}
