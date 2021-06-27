package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ViewpointExplosion {

    public static final ViewpointExplosion instance = new ViewpointExplosion();

    class Info {
        Player player;
        boolean enable;
    }

    List<Info> data = new ArrayList<Info>();

    boolean pluginEnable = false;

    int m_nMinDistance = 5;
    int m_nMaxDistance = 50;
    int m_nExplosionPwoer = 3;

    public void addPlayer(Player player) {
        data.add(new Info());
        data.get(data.size() - 1).player = player;
        data.get(data.size() - 1).enable = pluginEnable;
    }

    public void delPlayer(Player player) {
        data.removeIf(i -> i.player == player);
    }

    public void update() {
        if(pluginEnable) {
            for (Info i : data) {
                if (!i.enable) continue;
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
                if (player.getEyeLocation().distance(location) >= m_nMinDistance)
                    player.getWorld().createExplosion(location, m_nExplosionPwoer);
            }
        }
    }

    boolean cmnConf_pluginOn(CommandSender sender, String[] args){
        sender.sendMessage("mnConf_pluginOff was called");
        pluginEnable = true;
        for (Info i : data) {
            i.enable = true;
        }
        return true;
    }
    boolean cmnConf_pluginOff(CommandSender sender, String[] args){
        sender.sendMessage("cmnConf_pluginOff was called");
        pluginEnable = false;
        for (Info i : data) {
            i.enable = false;
        }
        return true;
    }
    boolean cmnConf_maxDistance(CommandSender sender, String[] args){
        sender.sendMessage("cmnConf_maxDistanceを was called");
        m_nMaxDistance = Integer.parseInt(args[2]);
        return true;
    }
    boolean cmnConf_minDistance(CommandSender sender, String[] args){
        sender.sendMessage("cmnConf_minDistanceを was called");
        m_nMinDistance = Integer.parseInt(args[2]);
        return true;
    }
    boolean cmnConf_power(CommandSender sender, String[] args){
        sender.sendMessage("cmnConf_power was called");
        m_nExplosionPwoer = Integer.parseInt(args[2]);
        return true;
    }
    boolean on(CommandSender sender, String[] args){
        sender.sendMessage("on was called");
        return true;
    }
    boolean off(CommandSender sender, String[] args){
        sender.sendMessage("off was called");
        return true;
    }
}
