package me.xemor.randomwars.Spectator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.UUID;

public class SpectatorPlayer {
   private Player player;

   public SpectatorPlayer(Player player) {
      this.player = player;
   }

   public SpectatorPlayer(UUID uuid) {
      Player player = Bukkit.getPlayer(uuid);
      this.player = player;
   }

   public boolean isSpectator() {
      return this.player.hasMetadata("RandomBlockSpectator") && ((MetadataValue)this.player.getMetadata("RandomBlockSpectator").get(0)).asBoolean();
   }

   private void applySpectatorInventory() {
      PlayerInventory playerInventory = this.player.getInventory();
      playerInventory.clear();
      playerInventory.setItem(0, TeleportCompass.getItem());
      playerInventory.setItem(1, EventVoter.getItem());
   }

   public void setSpectator(me.xemor.randomwars.RandomWars randomWars) {
      this.player.setGameMode(GameMode.SPECTATOR);
      this.player.setAllowFlight(true);
      this.player.setFlying(true);
      this.applySpectatorInventory();
      this.player.setMetadata("RandomBlockSpectator", new FixedMetadataValue(JavaPlugin.getPlugin(me.xemor.randomwars.RandomWars.class), true));
   }

   public void unsetSpectator(me.xemor.randomwars.RandomWars randomWars) {
      this.player.setGameMode(GameMode.SURVIVAL);
      this.player.setAllowFlight(false);
      this.player.setFlying(false);
      this.player.getInventory().clear();
      this.player.setMetadata("RandomBlockSpectator", new FixedMetadataValue(JavaPlugin.getPlugin(me.xemor.randomwars.RandomWars.class), false));
   }

   private void show(me.xemor.randomwars.RandomWars randomWars) {
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player toShowTo = (Player)var2.next();
         this.player.showPlayer(JavaPlugin.getPlugin(me.xemor.randomwars.RandomWars.class), toShowTo);
      }

   }

   private void hide(me.xemor.randomwars.RandomWars randomWars) {
      Iterator var2 = randomWars.getAlivePlayers().iterator();

      while(var2.hasNext()) {
         UUID uuid = (UUID)var2.next();
         Player alivePlayer = Bukkit.getPlayer(uuid);
         this.player.hidePlayer(JavaPlugin.getPlugin(me.xemor.randomwars.RandomWars.class), alivePlayer);
      }

   }

   public Player getPlayer() {
      return this.player;
   }
}
