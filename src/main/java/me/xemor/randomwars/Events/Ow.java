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

public class Ow extends Event {
   private ItemStack icon;

   public Ow(String name, ItemStack icon) {
      super(name, icon);
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      for (UUID uuid : randomWars.getAlivePlayers()) {
         Player player = Bukkit.getPlayer(uuid);
         player.setHealth(1.0D);
         world.spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 1);
         player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 0.0F);
      }
   }

   public boolean playBossMusic() {
      return false;
   }
}
