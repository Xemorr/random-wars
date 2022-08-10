package me.xemor.randomwars.Spectator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventVoter implements Listener {
   public static ItemStack getItem() {
      ItemStack eventVoter = new ItemStack(Material.EMERALD);
      ItemMeta meta = eventVoter.getItemMeta();
      meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&a&lVoter&8&l]"));
      eventVoter.setItemMeta(meta);
      return eventVoter;
   }
}
