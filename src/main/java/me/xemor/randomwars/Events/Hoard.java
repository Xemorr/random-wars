package me.xemor.randomwars.Events;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Hoard implements Event {
   private int amount;
   private EntityType entity;
   private ItemStack icon;

   public Hoard(EntityType entity, int amount, Material icon) {
      this.amount = amount;
      this.entity = entity;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      world.setTime(15000L);
      Iterator var3 = randomWars.getAlivePlayers().iterator();

      while(var3.hasNext()) {
         UUID uuid = (UUID)var3.next();
         Player player = Bukkit.getPlayer(uuid);

         for(int i = 0; i < this.amount; ++i) {
            world.spawn(player.getLocation(), this.entity.getEntityClass());
         }
      }

   }

   public String getName() {
      return this.entity.getEntityClass().getSimpleName() + " Hoard!";
   }

   public boolean playBossMusic() {
      return false;
   }

   public ItemStack getIcon() {
      return this.icon;
   }
}
