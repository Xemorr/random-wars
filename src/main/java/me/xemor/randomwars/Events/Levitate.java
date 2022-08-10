package me.xemor.randomwars.Events;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Levitate extends Event {
   private int duration;
   private int amplifier;

   public Levitate(String name, ItemStack icon, int duration, int amplifier) {
      super(name, icon);
      this.duration = duration;
      this.amplifier = amplifier;
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      Iterator var3 = randomWars.getAlivePlayers().iterator();

      while(var3.hasNext()) {
         UUID uuid = (UUID)var3.next();
         Player player = Bukkit.getPlayer(uuid);
         player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, this.duration, this.amplifier));
      }

   }

   public String getName() {
      return "Levitation";
   }

   public boolean playBossMusic() {
      return false;
   }

}
