package me.xemor.randomwars.Events;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class ComboEvent implements Event {
   private Event[] events;
   private String name;
   private ItemStack icon;

   public ComboEvent(String name, Material icon, Event... events) {
      this.events = events;
      this.name = name;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      Event[] var3 = this.events;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Event event = var3[var5];
         event.start(world, randomWars);
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
