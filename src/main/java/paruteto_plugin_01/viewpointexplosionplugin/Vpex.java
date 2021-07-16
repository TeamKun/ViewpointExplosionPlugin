package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final public class Vpex extends BaseCommand {

    public Vpex(ViewpointExplosionPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean result = false;
        if (args.length == 0) {
            sender.sendMessage(DecolationConst.RED + "[/vpex]の使用法をを確認してください");
            return result;
        }
        switch (args[0]) {
            case "cmn-conf":
                if (args.length == 1) {
                    sender.sendMessage(DecolationConst.RED + "[/vpex cmn-conf]の使用法をを確認してください");
                    break;
                }
                switch (args[1]) {
                    case "plugin-on":
                        if (args.length != 2) {
                            sender.sendMessage(DecolationConst.RED + "[vpex cmn-conf plugin-on]コマンドは、3つ目以降の引数は不要です");
                            break;
                        }
                        result = ViewpointExplosion.instance.cmnConf_pluginOn(sender, args);
                        break;
                    case "plugin-off":
                        if (args.length != 2) {
                            sender.sendMessage(DecolationConst.RED + "[vpex cmn-conf plugin-off]コマンドは、3つ目以降の引数は不要です");
                            break;
                        }
                        result = ViewpointExplosion.instance.cmnConf_pluginOff(sender, args);
                        break;
                    case "max-distance":
                        if (!(args.length == 3 && isNumber2(args[2]))) {
                            sender.sendMessage(DecolationConst.RED + "[vpex cmn-conf max-distance]コマンドは、第3引数に整数値を指定して下さい");
                            break;
                        }
                        result = ViewpointExplosion.instance.cmnConf_maxDistance(sender, args);
                        break;
                    case "min-distance":
                        if (!(args.length == 3 && isNumber2(args[2]))) {
                            sender.sendMessage(DecolationConst.RED + "[vpex cmn-conf min-distance]コマンドは、第3引数に整数値を指定して下さい");
                            break;
                        }
                        result = ViewpointExplosion.instance.cmnConf_minDistance(sender, args);
                        break;
                    case "power":
                        if (!(args.length == 3 && isNumber2(args[2]))) {
                            sender.sendMessage(DecolationConst.RED + "[vpex cmn-conf power]コマンドは、第3引数に整数値を指定して下さい");
                            break;
                        }
                        result = ViewpointExplosion.instance.cmnConf_power(sender, args);
                        break;
                    case "interval":
                        if (!(args.length == 3 && isNumber2(args[2]))) {
                            sender.sendMessage(DecolationConst.RED + "[vpex cmn-conf interval]コマンドは、第3引数に整数値を指定して下さい");
                            break;
                        }
                        result = ViewpointExplosion.instance.cmnConf_interval(sender, args);
                        break;
                    default:
                        sender.sendMessage(DecolationConst.RED + "[/vpex cmn-conf]の使用法をを確認してください");
                        break;
                }
                break;
            case "on":
                if (args.length == 1) {
                    sender.sendMessage(DecolationConst.RED + "[vpex on]コマンドは、第2引数以降に[PlayerID]or[@a]or[@r]を指定して下さい");
                    break;
                }
                ViewpointExplosion.instance.on(sender, args);
                break;
            case "off":
                if (args.length == 1) {
                    sender.sendMessage(DecolationConst.RED + "[vpex off]コマンドは、第2引数以降に[PlayerID]or[@a]or[@r]を指定して下さい");
                    break;
                }
                ViewpointExplosion.instance.off(sender, args);
                break;
            default:
                sender.sendMessage(DecolationConst.RED + "[/vpex]の使用法をを確認してください");
                break;
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<String>();
        if (args.length == 1) {
            completions.add("cmn-conf");
            completions.add("on");
            completions.add("off");

        }
        if (args.length == 2){
            if(args[0].equals("cmn-conf")){
                completions.add("plugin-on");
                completions.add("plugin-off");
                completions.add("max-distance");
                completions.add("min-distance");
                completions.add("power");
                completions.add("interval");
            }
            if(args[0].equals("on") || args[0].equals("off")){
                completions.add("@a");
                completions.add("@r");
                completions.addAll(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
            }
        }
        return completions;
    }

    @Override
    BaseCommand getInstance() {
        return this;
    }

    @Override
    public String getCommandName() {
        return "vpex";
    }

    public static boolean isNumber2(String value) {
        boolean result = false;
        if (value != null) {
            Pattern pattern = Pattern.compile("^[0-9]+$");
            result = pattern.matcher(value).matches();
        }
        return result;
    }

}