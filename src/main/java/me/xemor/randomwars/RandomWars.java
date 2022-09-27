package me.xemor.randomwars;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import me.xemor.randomwars.Commands.VoteCommand;
import me.xemor.randomwars.Events.*;
import me.xemor.randomwars.Features.Fireball;
import me.xemor.randomwars.Features.RandomPortal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomWars extends JavaPlugin {

   private static RandomWars randomWars;
   private Islands islands;
   private Supply supply;
   private List<UUID> alivePlayers = new ArrayList<>();
   private HashSet<UUID> startingPlayers = new HashSet<>();
   private EventHandler eventHandler;
   private HashSet<UUID> deadPlayers = new HashSet<>();

   public void onEnable() {
      randomWars = this;
      this.supply = new Supply(this);
      this.islands = new Islands(this);
      RandomPortal randomPortal = new RandomPortal(this);
      Fireball fireball = new Fireball();
      Bukkit.getPluginManager().registerEvents(this.islands, this);
      Bukkit.getPluginManager().registerEvents(this.supply, this);
      Bukkit.getPluginManager().registerEvents(randomPortal, this);
      Bukkit.getPluginManager().registerEvents(fireball, this);
      VoteCommand voteCommand = new VoteCommand();
      PluginCommand command = this.getCommand("vote");
      command.setExecutor(voteCommand);
      command.setTabCompleter(voteCommand);
      Event[] events = new Event[]{
              new BlockDrop("Anvils!", new ItemStack(Material.ANVIL), Material.ANVIL, 4, true),
              new ComboEvent("Levitate and Phantoms", new ItemStack(Material.BEETROOT_SEEDS), new PhantomAttack("", null, 25), new Levitate("", null, 200, 1)),
              new Levitate("Levitation!", new ItemStack(Material.FEATHER), 240, 1),
              new PhantomAttack("Phantoms!", new ItemStack(Material.PHANTOM_SPAWN_EGG), 30),
              new TNTRain("TNT Rain", new ItemStack(Material.TNT), 30),
              new BlockDrop("Diamonds!", new ItemStack(Material.DIAMOND_BLOCK), Material.DIAMOND_BLOCK, 2),
              new RandomTeleport("Swap!", new ItemStack(Material.BEACON)),
              new InventorySwapper("Where are my items?", new ItemStack(Material.FIRE)),
              new BlockDrop("Buried!", new ItemStack(Material.SAND), Material.SAND, 32, false),
              new NetherInvasion("Nether Invasion", new ItemStack(Material.BLAZE_SPAWN_EGG), 30),
              new ComboEvent("Absolute Hell", new ItemStack(Material.GHAST_SPAWN_EGG), new TNTRain("", null, 16), new NetherInvasion("", null, 20)),
              new WitherAttack("Wither Attack!", new ItemStack(Material.WITHER_SKELETON_SKULL), 3),
              new Hoard("Zombie Hoard!", new ItemStack(Material.ZOMBIE_SPAWN_EGG), EntityType.ZOMBIE, 4),
              new Hoard("Skeleton Hoard", new ItemStack(Material.SKELETON_SPAWN_EGG), EntityType.SKELETON, 4),
              new ComboEvent("Ultimate Swappage", new ItemStack(Material.BOOK), new RandomTeleport("", null), new InventorySwapper("", null)),
              new Hoard("Cavespider Hoard!", new ItemStack(Material.CAVE_SPIDER_SPAWN_EGG), EntityType.CAVE_SPIDER, 4),
              new ComboEvent("All the Mobs", new ItemStack(Material.SKELETON_SKULL), new Hoard("", null, EntityType.SKELETON, 2), new Hoard("", null, EntityType.CAVE_SPIDER, 2), new Hoard("", null, EntityType.ZOMBIE, 1)),
              new Hoard("Vindicator Hoard", new ItemStack(Material.VINDICATOR_SPAWN_EGG), EntityType.VINDICATOR, 2),
              new Ow("Ow!", new ItemStack(Material.DIAMOND_SWORD)),
              new GiveItemEvent("Elytra Madness", new ItemStack(Material.ELYTRA), new ItemStack(Material.ELYTRA), new ItemStack(Material.FIREWORK_ROCKET, 10)),
              new ComboEvent("Encherf Or Nuffin", new ItemStack(Material.ENCHANTING_TABLE), new ClearInventoryEvent("", null, 0.5), new EnchantEvent("", null)),
              new Hoard("Creeper!", new ItemStack(Material.CREEPER_HEAD), EntityType.CREEPER, 1)};
      eventHandler = new EventHandler(events);
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

   public static RandomWars getRandomWars() {
      return randomWars;
   }

   public void onDisable() {
   }
}
