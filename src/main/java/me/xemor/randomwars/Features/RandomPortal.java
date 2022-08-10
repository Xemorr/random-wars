package me.xemor.randomwars.Features;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class RandomPortal implements Listener {
   me.xemor.randomwars.RandomWars randomWars;
   Random random = new Random();

   public RandomPortal(me.xemor.randomwars.RandomWars randomWars) {
      this.randomWars = randomWars;
   }

   @EventHandler
   public void onPortal(PlayerPortalEvent e) {
      e.setCancelled(true);
      List players = this.randomWars.getAlivePlayers();

      Player otherPlayer;
      do {
         otherPlayer = Bukkit.getPlayer((UUID)players.get(this.random.nextInt(players.size())));
      } while(e.getPlayer() == otherPlayer);

      e.getPlayer().teleport(otherPlayer);
   }
}
