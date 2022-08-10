package me.xemor.randomwars.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Phantom;
import org.bukkit.inventory.ItemStack;

public class PhantomAttack implements Event {
   private int amount;
   private ItemStack icon;
   private String name;

   public PhantomAttack(String name, Material icon, int amount) {
      this.amount = amount;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

      this.name = name;
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      int y = 90;

      for(int i = 0; i < this.amount; ++i) {
         int x = randomWars.getIslands().randomLocation();
         int z = randomWars.getIslands().randomLocation();
         Location location = new Location(world, (double)x, (double)y, (double)z);
         world.spawn(location, Phantom.class);
         world.setTime(15000L);
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
