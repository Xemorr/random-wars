package me.xemor.randomwars.Events;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RandomTeleport extends Event {

   public RandomTeleport(String name, ItemStack icon) {
      super(name, icon);
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      List<UUID> alivePlayers = randomWars.getAlivePlayers();
      UUID lastPlayerUUID = (UUID)alivePlayers.get(alivePlayers.size() - 1);
      Location location = Bukkit.getPlayer(lastPlayerUUID).getLocation();

      Location lastLocation;
      for(Iterator var6 = alivePlayers.iterator(); var6.hasNext(); location = lastLocation) {
         UUID uuid = (UUID)var6.next();
         Player player = Bukkit.getPlayer(uuid);
         lastLocation = player.getLocation();
         player.teleportAsync(location);
      }

   }
   public boolean playBossMusic() {
      return false;
   }
}
