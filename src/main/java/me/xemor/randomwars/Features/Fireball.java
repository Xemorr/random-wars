package me.xemor.randomwars.Features;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Fireball implements Listener {
   @EventHandler
   public void onFireball(PlayerInteractEvent e) {
      if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getItem().getType() == Material.FIRE_CHARGE) {
         Player player = e.getPlayer();
         e.getItem().setAmount(e.getItem().getAmount() - 1);
         Entity entity = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.FIREBALL);
         entity.setVelocity(player.getEyeLocation().getDirection());
      }

   }
}
