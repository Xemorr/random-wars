package me.xemor.randomwars.Events;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventorySwapper implements Event {
   private String name;
   private ItemStack icon;

   public InventorySwapper(String name, Material icon) {
      this.name = name;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      List<UUID> alivePlayers = randomWars.getAlivePlayers();
      UUID lastPlayerUUID = (UUID)alivePlayers.get(alivePlayers.size() - 1);
      ItemStack[] contents = Bukkit.getPlayer(lastPlayerUUID).getInventory().getContents();

      ItemStack[] lastContents;
      for(Iterator var6 = alivePlayers.iterator(); var6.hasNext(); contents = lastContents) {
         UUID uuid = (UUID)var6.next();
         Player player = Bukkit.getPlayer(uuid);
         lastContents = player.getInventory().getContents();
         player.getInventory().setContents(contents);
      }

   }

   public String getName() {
      return "Inventory Swapping";
   }

   public boolean playBossMusic() {
      return false;
   }

   public ItemStack getIcon() {
      return this.icon;
   }
}
