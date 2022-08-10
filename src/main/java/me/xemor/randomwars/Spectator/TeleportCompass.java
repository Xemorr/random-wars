package me.xemor.randomwars.Spectator;

import com.destroystokyo.paper.profile.PlayerProfile;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class TeleportCompass implements Listener {
   me.xemor.randomwars.RandomWars randomWars;

   public TeleportCompass(me.xemor.randomwars.RandomWars randomWars) {
      this.randomWars = randomWars;
   }

   public static ItemStack getItem() {
      ItemStack item = new ItemStack(Material.COMPASS);
      ItemMeta itemMeta = item.getItemMeta();
      itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&5&lTeleporter&8&l]"));
      item.setItemMeta(itemMeta);
      return item;
   }

   @EventHandler
   public void onInteract(PlayerInteractEvent e) {
      Player interactor = e.getPlayer();
      SpectatorPlayer spectatorPlayer = new SpectatorPlayer(interactor);
      if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && spectatorPlayer.isSpectator() && e.getItem().equals(getItem())) {
         Inventory GUI = this.createInventory();
         interactor.openInventory(GUI);
      }

   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent e) {
      ItemStack skull = e.getCurrentItem();
      if (skull != null) {
         if (skull.getType() == Material.PLAYER_HEAD && e.getWhoClicked() instanceof Player) {
            Player player = (Player)e.getWhoClicked();
            SpectatorPlayer spectatorPlayer = new SpectatorPlayer(player);
            if (spectatorPlayer.isSpectator()) {
               e.setCancelled(true);
               SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
               PlayerProfile playerProfile = skullMeta.getPlayerProfile();
               if (!playerProfile.isComplete()) {
                  boolean successful = playerProfile.complete();
                  if (!successful) {
                     return;
                  }
               }

               Player clickedOn = Bukkit.getPlayer(playerProfile.getId());
               if (clickedOn == null) {
                  return;
               }

               player.teleportAsync(clickedOn.getLocation());
            }
         }

      }
   }

   public Inventory createInventory() {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54);
      List<UUID> alivePlayers = this.randomWars.getAlivePlayers();

      for(int i = 0; i < this.randomWars.getAlivePlayers().size() && i < 54; ++i) {
         Player alivePlayer = Bukkit.getPlayer((UUID)alivePlayers.get(i));
         ItemStack playerSkull = new ItemStack(Material.PLAYER_HEAD);
         SkullMeta skullMeta = (SkullMeta)playerSkull.getItemMeta();
         skullMeta.setPlayerProfile(alivePlayer.getPlayerProfile());
         playerSkull.setItemMeta(skullMeta);
         inventory.setItem(i, playerSkull);
      }

      return inventory;
   }
}
