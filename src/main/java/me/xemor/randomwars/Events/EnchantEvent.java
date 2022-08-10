package me.xemor.randomwars.Events;

import me.xemor.randomwars.RandomWars;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.xemor.randomwars.Events.Event;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EnchantEvent extends Event {

    private final Enchantment[] enchantments = Enchantment.values();

    public EnchantEvent(String name, ItemStack icon) {
        super(name, icon);
    }

    @Override
    public void start(World world, RandomWars randomWars) {
        for (UUID uuid : randomWars.getAlivePlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            ItemStack[] items = player.getInventory().getContents();
            for (int i = 0; i < items.length; i++) {
                ItemStack item = items[i];
                int random = ThreadLocalRandom.current().nextInt(enchantments.length);
                Enchantment enchantment = enchantments[random];
                item.addEnchantment(enchantment, ThreadLocalRandom.current().nextInt(enchantment.getMaxLevel()));
                items[i] = item;
                player.getInventory().setContents(items);
            }
        }
    }

    @Override
    public String getName() {
        return null;
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
