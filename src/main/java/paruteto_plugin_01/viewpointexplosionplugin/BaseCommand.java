package paruteto_plugin_01.viewpointexplosionplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public abstract class BaseCommand implements CommandExecutor, TabCompleter {
    private static ViewpointExplosionPlugin PLUGIN = null;// 直接扱わず、メソッド ***Plugin() で介す

    public BaseCommand(ViewpointExplosionPlugin plugin) {
        if (this.getPlugin() == null)
            this.setPlugin(plugin);
        if (this.getInstance() == null)
            throw new NullPointerException("Instance is null");
        if (this.getCommandName() == null)
            throw new NullPointerException("CommandName is null");

        this.register();
    }

    /**
     * プラグインのインスタンスをゲット
     *
     */
    final ViewpointExplosionPlugin getPlugin() {
        return BaseCommand.PLUGIN;
    }

    /**
     * プラグインのインスタンスをセットする
     *
     * @param plugin
     */
    final void setPlugin(ViewpointExplosionPlugin plugin) {
        if (plugin == null)
            throw new IllegalArgumentException("Argument \"plugin\" is null");
        BaseCommand.PLUGIN = plugin;
    }

    /**
     * CommandExecutor と TabCompleter の登録
     *
     */
    public void register() {
        PluginCommand c = this.getPlugin().getCommand(this.getCommandName());
        this.getPlugin().getLogger().info(this.getCommandName());
        if (c != null) {
            c.setExecutor(this.getInstance());
            if (this.getInstance() instanceof TabCompleter) {
                c.setTabCompleter((TabCompleter) this.getInstance());
                this.getPlugin().getLogger().info("[vpex]登録しました");
            }
        }
    }

    /**
     * 自身のインスタンスを返す
     *
     */
    abstract BaseCommand getInstance();

    /**
     * コマンド名をゲットする
     *
     * @return コマンド名を返す
     */
    public abstract String getCommandName();
}
