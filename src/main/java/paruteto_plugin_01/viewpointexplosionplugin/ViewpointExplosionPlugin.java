package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ViewpointExplosionPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("注視点爆発プラグイン開始");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                ExplosionTask.instance.update();
            }
        }.runTaskTimer(this, 0L, 5L);
    }

    @Override
    public void onDisable() {
        getLogger().info("注視点爆発プラグイン終了");
    }

}
