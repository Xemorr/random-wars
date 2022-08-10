package me.xemor.randomwars.Events;

import me.xemor.randomwars.RandomWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ClearInventoryEvent extends Event {

    private final double percentage;

    public ClearInventoryEvent(String name, ItemStack icon, double percentage) {
        super(name, icon);
        this.percentage = percentage;
    }


    @Override
    public void start(World world, RandomWars randomWars) {
        for (UUID uuid : randomWars.getAlivePlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            ItemStack[] items = player.getInventory().getContents();
            for (int i = 0; i < items.length; i++) {
                double random = ThreadLocalRandom.current().nextDouble();
                if (random < percentage) {
                    items[i] = new ItemStack(Material.AIR);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "null";
    }

    @Override
    public boolean playBossMusic() {
        return false;
    }

    @Override
    public ItemStack getIcon() {
        return null;
    }
}
