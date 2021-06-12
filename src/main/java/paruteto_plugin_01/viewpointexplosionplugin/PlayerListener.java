package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;

import java.util.function.Predicate;

public class PlayerListener implements Listener {

    int m_nMaxDistance = 50;

    @EventHandler
    public void onPInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location eyeloc = player.getEyeLocation();
        Location loc = player.getLocation();
        RayTraceResult rtr = world.rayTrace(eyeloc.add(loc.getDirection().multiply(2)), loc.getDirection(), m_nMaxDistance - 2, FluidCollisionMode.NEVER, false, 1, null);
        if (rtr != null) {
            Location exloc = new Location(world, rtr.getHitPosition().getX(), rtr.getHitPosition().getY(), rtr.getHitPosition().getZ());
            loc.getWorld().createExplosion(exloc, 3);
        }
        else {
            loc.getWorld().createExplosion(loc.add(loc.getDirection().multiply(m_nMaxDistance)), 3);
        }
    }
}
