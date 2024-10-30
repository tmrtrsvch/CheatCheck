package ch.tmrtrsv.cheatcheck;

import ch.tmrtrsv.cheatcheck.commands.CheckCommand;
import ch.tmrtrsv.cheatcheck.commands.UncheckCommand;
import ch.tmrtrsv.cheatcheck.commands.ReloadCommand;
import ch.tmrtrsv.cheatcheck.listeners.EventListener;
import ch.tmrtrsv.cheatcheck.tabcompleters.ReloadTabCompleter;
import ch.tmrtrsv.cheatcheck.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CheatCheck extends JavaPlugin {

    private final EventListener eventListener;

    public CheatCheck() {
        this.eventListener = new EventListener(this);
    }

    @Override
    public void onEnable() {
        sendCredit();

        this.saveDefaultConfig();
        this.getCommand("check").setExecutor(new CheckCommand(this));
        this.getCommand("uncheck").setExecutor(new UncheckCommand(this));
        this.getCommand("cheatcheck").setExecutor(new ReloadCommand(this));
        this.getCommand("cheatcheck").setTabCompleter(new ReloadTabCompleter());

        getServer().getPluginManager().registerEvents(eventListener, this);
    }

    @Override
    public void onDisable() {
        eventListener.clearCheckedPlayers();
        sendCredit();
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    private void sendCredit() {
        Bukkit.getConsoleSender().sendMessage(Utils.color(""));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&f &#FF2900C&#FC2600h&#FA2300e&#F72000a&#F41D00t&#F11A00C&#EF1700h&#EC1500e&#E91200c&#E70F00k &#E10900v&#DE06001&#DC0300.&#D900002"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&f Автор: &#FB3908Т&#FC2B06и&#FD1D04м&#FE0E02у&#FF0000р"));
        Bukkit.getConsoleSender().sendMessage(Utils.color("&f Телеграм: &#008DFF@&#0086FFt&#007FFFm&#0078FFr&#0071FFt&#006BFFr&#0064FFs&#005DFFv&#0056FFc&#004FFFh"));
        Bukkit.getConsoleSender().sendMessage(Utils.color(""));
    }
}