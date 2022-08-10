package me.xemor.randomwars;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import me.xemor.randomwars.Events.BlockDrop;
import me.xemor.randomwars.Events.ComboEvent;
import me.xemor.randomwars.Events.Event;
import me.xemor.randomwars.Events.EventHandler;
import me.xemor.randomwars.Events.Hoard;
import me.xemor.randomwars.Events.InventorySwapper;
import me.xemor.randomwars.Events.Levitate;
import me.xemor.randomwars.Events.NetherInvasion;
import me.xemor.randomwars.Events.Ow;
import me.xemor.randomwars.Events.PhantomAttack;
import me.xemor.randomwars.Events.RandomTeleport;
import me.xemor.randomwars.Events.TNTRain;
import me.xemor.randomwars.Events.WitherAttack;
import me.xemor.randomwars.Features.Fireball;
import me.xemor.randomwars.Features.RandomPortal;
import me.xemor.randomwars.Spectator.TeleportCompass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomWars extends JavaPlugin {
   private Islands islands;
   private Supply supply;
   private List<UUID> alivePlayers = new ArrayList();
   private HashSet<UUID> startingPlayers = new HashSet();
   private EventHandler eventHandler;
   private HashSet<UUID> deadPlayers = new HashSet();

   public void onEnable() {
      this.supply = new Supply(this);
      this.islands = new Islands(this);
      RandomPortal randomPortal = new RandomPortal(this);
      Fireball fireball = new Fireball();
      TeleportCompass teleportCompass = new TeleportCompass(this);
      Bukkit.getPluginManager().registerEvents(this.islands, this);
      Bukkit.getPluginManager().registerEvents(teleportCompass, this);
      Bukkit.getPluginManager().registerEvents(this.supply, this);
      Bukkit.getPluginManager().registerEvents(randomPortal, this);
      Bukkit.getPluginManager().registerEvents(fireball, this);
      Event[] events = new Event[]{new BlockDrop("Anvils!", Material.ANVIL, 4, true), new ComboEvent("Levitate and Phantoms", Material.BEETROOT_SEEDS, new Event[]{new PhantomAttack("", (Material)null, 25), new Levitate("", (Material)null, 200, 1)}), new Levitate("Levitation!", Material.FEATHER, 240, 1), new PhantomAttack("Phantoms!", Material.PHANTOM_SPAWN_EGG, 30), new TNTRain("TNT Rain", Material.TNT, 30), new BlockDrop("Diamonds!", Material.DIAMOND_BLOCK, 2), new RandomTeleport("Swap!", Material.BEACON), new InventorySwapper("Where are my items?", Material.FIRE), new BlockDrop("Buried!", Material.SAND, 32, false), new NetherInvasion("Nether Invasion", Material.BLAZE_SPAWN_EGG, 30), new ComboEvent("Absolute Hell", Material.GHAST_SPAWN_EGG, new Event[]{new TNTRain("", (Material)null, 16), new NetherInvasion("", null, 20)}), new WitherAttack("Wither Attack!", Material.WITHER_SKELETON_SKULL, 3), new Hoard(EntityType.ZOMBIE, 4, Material.ZOMBIE_SPAWN_EGG), new Hoard(EntityType.SKELETON, 4, Material.ZOMBIE_SPAWN_EGG), new ComboEvent("Ultimate Swappage", Material.BOOK, new Event[]{new RandomTeleport("", (Material)null), new InventorySwapper("", (Material)null)}), new Hoard(EntityType.CAVE_SPIDER, 4, Material.CAVE_SPIDER_SPAWN_EGG), new ComboEvent("All the Mobs", Material.SKELETON_SKULL, new Event[]{new Hoard(EntityType.SKELETON, 2, (Material)null), new Hoard(EntityType.CAVE_SPIDER, 2, (Material)null), new Hoard(EntityType.ZOMBIE, 1, (Material)null)}), new Hoard(EntityType.VINDICATOR, 2, Material.VINDICATOR_SPAWN_EGG), new Ow("Ow!", Material.DIAMOND_SWORD)};
      this.eventHandler = new EventHandler();
      int length = events.length;
      for(int i = 0; i < length; ++i) {
         Event event = events[i];
         this.eventHandler.addEvent(event);
      }
   }

   public Islands getIslands() {
      return this.islands;
   }

   public Supply getSupply() {
      return this.supply;
   }

   public EventHandler getEventHandler() {
      return this.eventHandler;
   }

   public List<UUID> getAlivePlayers() {
      return this.alivePlayers;
   }

   public HashSet<UUID> getStartingPlayers() {
      return this.startingPlayers;
   }

   public HashSet<UUID> getDeadPlayers() {
      return this.deadPlayers;
   }

   public void onDisable() {
   }
}
