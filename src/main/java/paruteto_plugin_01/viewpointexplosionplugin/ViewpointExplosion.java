package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    int m_nUpdateInterval = 20;

    int m_nUpdateCnt = 0;

    Random rand = new Random();

    public void addPlayer(Player player) {
        data.add(new Info());
        data.get(data.size() - 1).player = player;
        data.get(data.size() - 1).enable = pluginEnable;
    }

    public void delPlayer(Player player) {
        data.removeIf(i -> i.player == player);
    }

    public void update() {
        m_nUpdateCnt++;
        if (m_nUpdateCnt < m_nUpdateInterval){
            return;
        }
        m_nUpdateCnt = 0;
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
        sender.sendMessage("注視点爆発プラグインを有効にしました。");
        pluginEnable = true;
        for (Info i : data) {
            i.enable = true;
        }
        return true;
    }
    boolean cmnConf_pluginOff(CommandSender sender, String[] args){
        sender.sendMessage("注視点爆発プラグインを無効にしました。");
        pluginEnable = false;
        for (Info i : data) {
            i.enable = false;
        }
        return true;
    }
    boolean cmnConf_maxDistance(CommandSender sender, String[] args){
        sender.sendMessage(String.format("爆発最大距離を%dに変更しました。", Integer.parseInt(args[2])));
        m_nMaxDistance = Integer.parseInt(args[2]);
        return true;
    }
    boolean cmnConf_minDistance(CommandSender sender, String[] args){
        sender.sendMessage(String.format("爆発最小距離を%dに変更しました。", Integer.parseInt(args[2])));
        m_nMinDistance = Integer.parseInt(args[2]);
        return true;
    }
    boolean cmnConf_power(CommandSender sender, String[] args){
        sender.sendMessage(String.format("爆発規模を%dに変更しました。", Integer.parseInt(args[2])));
        m_nExplosionPwoer = Integer.parseInt(args[2]);
        return true;
    }
    boolean cmnConf_interval(CommandSender sender, String[] args){
        sender.sendMessage(String.format("爆発間隔を%dtickに変更しました。", Integer.parseInt(args[2])));
        m_nUpdateInterval = Integer.parseInt(args[2]);
        return true;
    }
    boolean on(CommandSender sender, String[] args){
        for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
            switch (arg) {
                case "@a":
                    for (Info i : data) {
                        i.enable = true;
                    }
                    sender.sendMessage(String.format("全プレイヤーの爆発を有効にしました。"));
                    break;

                case "@r":
                    int num = rand.nextInt(data.size());
                    if(data.get(num).enable){
                        sender.sendMessage(String.format("[%s]はすでに爆発が有効になっています。", data.get(num).player.getName()));
                    }
                    else{
                        data.get(num).enable = true;
                        sender.sendMessage(String.format("[%s]の爆発を有効にしました。", data.get(num).player.getName()));
                    }
                    break;

                default:
                    boolean found = false;
                    for (Info i : data) {
                        if (i.player.getName().matches(arg)){
                            if(i.enable){
                                sender.sendMessage(String.format("[%s]はすでに爆発が有効になっています。", arg));
                            }
                            else{
                                i.enable = true;
                                sender.sendMessage(String.format("[%s]の爆発を有効にしました。", arg));
                            }
                            found = true;
                        }
                    }
                    if (!found){
                        sender.sendMessage(String.format("プレイヤー[%s]が見つかりませんでした。", arg));
                    }
                    break;
            }
        }
        return true;
    }
    boolean off(CommandSender sender, String[] args){
        for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
            switch (arg) {
                case "@a":
                    for (Info i : data) {
                        i.enable = false;
                    }
                    sender.sendMessage(String.format("全プレイヤーの爆発を無効にしました。"));
                    break;

                case "@r":
                    int num = rand.nextInt(data.size());
                    if(!data.get(num).enable){
                        sender.sendMessage(String.format("[%s]はすでに爆発が無効になっています。", data.get(num).player.getName()));
                    }
                    else{
                        data.get(num).enable = false;
                        sender.sendMessage(String.format("[%s]の爆発を無効にしました。", data.get(num).player.getName()));
                    }
                    break;

                default:
                    boolean found = false;
                    for (Info i : data) {
                        if (i.player.getName().matches(arg)){
                            if(!i.enable){
                                sender.sendMessage(String.format("[%s]はすでに爆発が無効になっています。", arg));
                            }
                            else{
                                i.enable = false;
                                sender.sendMessage(String.format("[%s]の爆発を無効にしました。", arg));
                            }
                            found = true;
                        }
                    }
                    if (!found){
                        sender.sendMessage(String.format("プレイヤー[%s]が見つかりませんでした。", arg));
                    }
                    break;
            }
        }
        return true;
    }
}
