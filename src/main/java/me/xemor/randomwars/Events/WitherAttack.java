package me.xemor.randomwars.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Wither;
import org.bukkit.inventory.ItemStack;

public class WitherAttack implements Event {
   private int amount;
   private ItemStack icon;
   private String name;

   public WitherAttack(String name, Material icon, int amount) {
      this.amount = amount;
      this.name = name;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      int y = 90;

      for(int i = 0; i < this.amount * randomWars.getStartingPlayers().size(); ++i) {
         int x = randomWars.getIslands().randomLocation();
         int z = randomWars.getIslands().randomLocation();
         Location location = new Location(world, (double)x, (double)y, (double)z);
         world.spawn(location, Wither.class);
      }

   }

   public String getName() {
      return "Wither Attack";
   }

   public boolean playBossMusic() {
      return true;
   }

   public ItemStack getIcon() {
      return this.icon;
   }
}
