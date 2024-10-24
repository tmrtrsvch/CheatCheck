package ch.tmrtrsv.cheatcheck.listeners;

import ch.tmrtrsv.cheatcheck.CheatCheck;
import ch.tmrtrsv.cheatcheck.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EventListener implements Listener {

    private final CheatCheck plugin;
    private final Set<UUID> checkedPlayers;

    public EventListener(CheatCheck plugin) {
        this.plugin = plugin;
        this.checkedPlayers = new HashSet<>();
    }

    public Set<UUID> getCheckedPlayers() {
        return checkedPlayers;
    }

    public void clearCheckedPlayers() {
        this.checkedPlayers.clear();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (checkedPlayers.contains(player.getUniqueId())) {
            event.setTo(event.getFrom());
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (checkedPlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(Utils.color(plugin.getConfig().getString("messages.cannot_use_commands")));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (checkedPlayers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}