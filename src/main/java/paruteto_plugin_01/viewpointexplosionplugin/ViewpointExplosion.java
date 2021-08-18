package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ViewpointExplosion {

    public static final ViewpointExplosion instance = new ViewpointExplosion();

    List<String> enableList = new ArrayList<String>();

    int m_nMinDistance = 5;
    int m_nMaxDistance = 50;
    int m_nExplosionPwoer = 3;
    int m_nUpdateInterval = 20;

    int m_nUpdateCnt = 0;

    Random rand = new Random();

    public boolean CheckList(List<String> list, String str){
        for (String a : list) if (a.equals(str)) return true;
        return false;
    }

    public void update() {
        m_nUpdateCnt++;
        if (m_nUpdateCnt < m_nUpdateInterval){
            return;
        }
        m_nUpdateCnt = 0;
        for (String i : enableList) {
            Player player = Bukkit.getPlayer(i);
            if(player == null) continue;
            if (player.getGameMode() == GameMode.SPECTATOR) continue;
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
        List<String> allPlayer = Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
            switch (arg) {
                case "@a":
                    sender.sendMessage(String.format("全プレイヤーの爆発を有効にしました。"));
                    enableList = allPlayer;
                    break;

                case "@r":
                    int num = rand.nextInt(allPlayer.size());
                    if(CheckList(enableList, allPlayer.get(num))){
                        sender.sendMessage(String.format("[%s]はすでに爆発が有効になっています。", allPlayer.get(num)));
                    }
                    else{
                        sender.sendMessage(String.format("[%s]の爆発を有効にしました。", allPlayer.get(num)));
                        enableList.add(allPlayer.get(num));
                    }
                    break;

                default:
                    if (CheckList(allPlayer, arg)){
                        if (CheckList(enableList, arg)){
                            sender.sendMessage(String.format("[%s]はすでに爆発が有効になっています。", arg));
                        }
                        else{
                            sender.sendMessage(String.format("[%s]の爆発を有効にしました。", arg));
                            enableList.add(arg);
                        }
                    }
                    else{
                        sender.sendMessage(String.format("プレイヤー[%s]が見つかりませんでした。", arg));
                    }
                    break;
            }
        }
        return true;
    }
    boolean off(CommandSender sender, String[] args){
        List<String> allPlayer = Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
            switch (arg) {
                case "@a":
                    sender.sendMessage(String.format("全プレイヤーの爆発を無効にしました。"));
                    enableList.clear();
                    break;

                case "@r":
                    int num = rand.nextInt(allPlayer.size());
                    if(!CheckList(enableList, allPlayer.get(num))){
                        sender.sendMessage(String.format("[%s]はすでに爆発が無効になっています。", allPlayer.get(num)));
                    }
                    else{
                        sender.sendMessage(String.format("[%s]の爆発を無効にしました。", allPlayer.get(num)));
                        enableList.remove(enableList.indexOf(allPlayer.get(num)));
                    }
                    break;

                default:
                        if(!CheckList(enableList, arg)){
                            if (!CheckList(allPlayer, arg)){
                                sender.sendMessage(String.format("プレイヤー[%s]が見つかりませんでした。", arg));
                            }
                            else {
                                sender.sendMessage(String.format("[%s]はすでに爆発が無効になっています。", arg));
                            }
                        }
                        else{
                            sender.sendMessage(String.format("[%s]の爆発を無効にしました。", arg));
                            enableList.remove(enableList.indexOf(arg));
                        }
                    break;
            }
        }
        return true;
    }
}
