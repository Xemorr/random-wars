package me.xemor.randomwars.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Wither;
import org.bukkit.inventory.ItemStack;

public class WitherAttack extends Event {
   private int amount;
   private ItemStack icon;
   private String name;

   public WitherAttack(String name, ItemStack icon, int amount) {
      super(name, icon);
      this.amount = amount;
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      int y = 105;

      for (int i = 0; i < this.amount * randomWars.getStartingPlayers().size(); i++) {
         Location location = randomWars.getIslands().randomLocation();
         location.setY(y);
         world.spawn(location, Wither.class);
      }

   }

   public boolean playBossMusic() {
      return true;
   }
}
