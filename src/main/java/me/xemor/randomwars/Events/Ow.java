package me.xemor.randomwars.Events;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Ow implements Event {
   private ItemStack icon;
   private String name;

   public Ow(String name, Material icon) {
      this.name = name;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      for (UUID uuid : randomWars.getAlivePlayers()) {
         Player player = Bukkit.getPlayer(uuid);
         player.setHealth(1.0D);
         world.spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 1);
         player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 0.0F);
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
