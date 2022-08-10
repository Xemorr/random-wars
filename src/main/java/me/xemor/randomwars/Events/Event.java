package me.xemor.randomwars.Events;

import me.xemor.randomwars.RandomWars;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public abstract class Event {

   private final String name;
   private final ItemStack icon;

   public Event(String name, ItemStack icon) {
      this.name = name;
      this.icon = icon;
   }
   public abstract void start(World world, RandomWars randomWars);

   public String getName() {
      return name;
   };
   abstract boolean playBossMusic();
   public ItemStack getIcon() {
      return icon;
   };
}
