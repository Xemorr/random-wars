package me.xemor.randomwars.Events;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public interface Event {
   void start(World var1, me.xemor.randomwars.RandomWars var2);

   String getName();

   boolean playBossMusic();

   ItemStack getIcon();
}
