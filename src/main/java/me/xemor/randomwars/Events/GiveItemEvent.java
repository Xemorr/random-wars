package me.xemor.randomwars.Events;

import me.xemor.randomwars.RandomWars;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GiveItemEvent extends Event {

    private ItemStack[] items;

    public GiveItemEvent(String name, ItemStack icon, ItemStack... items) {
        super(name, icon);
        this.items = items;
    }

    @Override
    public void start(World world, RandomWars randomWars) {
        for (UUID uuid : randomWars.getAlivePlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            for (ItemStack item : items) {
                var leftovers = player.getInventory().addItem(item);
                for (ItemStack leftover : leftovers.values()) {
                    player.getWorld().dropItem(player.getLocation(), leftover);
                }
            }
        }
    }

    @Override
    boolean playBossMusic() {
        return false;
    }
}
