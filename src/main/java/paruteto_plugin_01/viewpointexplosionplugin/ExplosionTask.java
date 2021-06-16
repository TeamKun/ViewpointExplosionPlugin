package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ExplosionTask {

    public static final ExplosionTask instance = new ExplosionTask();

    class Info {
        Player player;
        boolean enable;
    }

    List<Info> data = new ArrayList<Info>();

    int m_nMaxDistance = 50;
    int m_nExplosionPwoer = 3;

    public void addPlayer(Player player) {
        data.add(new Info());
        data.get(data.size() - 1).player = player;
        data.get(data.size() - 1).enable = true;
    }

    public void delPlayer(Player player) {
        data.removeIf(i -> i.player == player);
    }

    public void update() {
        for(Info i : data){
            if(!i.enable) continue;
            Player player = i.player;
            Entity entity = player.getTargetEntity(m_nMaxDistance);
            Block block = player.getTargetBlock(m_nMaxDistance);
            Location location;
            if (entity != null) {
                location = entity.getBoundingBox().getCenter().toLocation(entity.getWorld());
            } else if (block != null && !block.getType().equals(Material.AIR)) {
                location = block.getBoundingBox().getCenter().toLocation(block.getWorld());
            } else {
                location = player.getEyeLocation().clone().add(player.getLocation().getDirection().multiply(m_nMaxDistance));
            }
            player.getWorld().createExplosion(location, m_nExplosionPwoer);
        }
    }
}
