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
         Location location = randomWars.getIslands().randomLocation();
         location.setY(y);
         TNTPrimed tnt = (TNTPrimed)world.spawn(location, TNTPrimed.class);
         tnt.setFuseTicks(280);
      }

   }
   public boolean playBossMusic() {
      return false;
   }

}
