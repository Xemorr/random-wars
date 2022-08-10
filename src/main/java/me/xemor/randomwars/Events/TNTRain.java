package me.xemor.randomwars.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;

public class TNTRain extends Event {
   private int multiplier;
   private ItemStack icon;
   private String name;

   public TNTRain(String name, ItemStack icon, int multiplier) {
      super(name, icon);
      this.multiplier = multiplier;
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      int y = 320;

      for(int i = 0; i < this.multiplier * randomWars.getStartingPlayers().size(); ++i) {
         int x = randomWars.getIslands().randomLocation();
         int z = randomWars.getIslands().randomLocation();
         TNTPrimed tnt = (TNTPrimed)world.spawn(new Location(world, (double)x, (double)y, (double)z), TNTPrimed.class);
         tnt.setFuseTicks(300);
      }

   }

   public String getName() {
      return this.name;
   }

   public boolean playBossMusic() {
      return false;
   }

   public ItemStack getIcon() {
      return this.icon;
   }
}
