package me.xemor.randomwars.Events;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Ghast;
import org.bukkit.inventory.ItemStack;

public class NetherInvasion implements Event {
   private int amount;
   private String name;
   private ItemStack icon;

   public NetherInvasion(String name, Material icon, int amount) {
      this.amount = amount;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

      this.name = name;
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      int y = 80;

      for(int i = 0; i < this.amount; ++i) {
         int x = randomWars.getIslands().randomLocation();
         int z = randomWars.getIslands().randomLocation();
         Location location = new Location(world, (double)x, (double)y, (double)z);
         Random random = new Random();
         int rng = random.nextInt(2);
         if (rng == 1) {
            world.spawn(location, Ghast.class);
         } else {
            world.spawn(location, Blaze.class);
         }
      }

   }

   public String getName() {
      return this.name;
   }

   public boolean playBossMusic() {
      return true;
   }

   public ItemStack getIcon() {
      return this.icon;
   }
}
