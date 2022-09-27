package me.xemor.randomwars.Events;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Ghast;
import org.bukkit.inventory.ItemStack;

public class NetherInvasion extends Event {
   private int amount;

   public NetherInvasion(String name, ItemStack icon, int amount) {
      super(name, icon);
      this.amount = amount;
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      int y = 105;

      for(int i = 0; i < this.amount; ++i) {
         Location location = randomWars.getIslands().randomLocation();
         location.setY(105);
         Random random = new Random();
         int rng = random.nextInt(2);
         if (rng == 1) {
            world.spawn(location, Ghast.class);
         } else {
            world.spawn(location, Blaze.class);
         }
      }

   }
   public boolean playBossMusic() {
      return true;
   }

}
