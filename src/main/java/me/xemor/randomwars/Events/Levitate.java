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

public class Levitate implements Event {
   private int duration;
   private int amplifier;
   private ItemStack icon;
   private String name;

   public Levitate(String name, Material icon, int duration, int amplifier) {
      this.duration = duration;
      this.amplifier = amplifier;
      this.name = name;
      if (icon != null) {
         this.icon = new ItemStack(icon);
      }

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

   public ItemStack getIcon() {
      return this.icon;
   }
}
