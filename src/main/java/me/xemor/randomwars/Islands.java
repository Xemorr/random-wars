package me.xemor.randomwars;

import com.destroystokyo.paper.Title;
import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import me.xemor.randomwars.Events.Event;
import me.xemor.randomwars.PluginEvents.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Islands implements Listener {
   private RandomWorld world;
   private long startMillis;
   private final Long minute = 60000L;
   private BossBar bossBar;
   private boolean gracePeriod;
   private BukkitTask gameTimer;
   private String gracePeriodOver;
   private String playersRemainingSubtitle;
   private String GG;
   private String GGSubtitle;
   private final Random random;
   int currentEventNumber;
   String currentMessage;
   RandomWars randomBlock;

   public Islands(RandomWars randomBlock) {
      this.bossBar = Bukkit.createBossBar("Grace Period.", BarColor.BLUE, BarStyle.SOLID);
      this.gracePeriod = true;
      this.gracePeriodOver = ChatColor.translateAlternateColorCodes('&', "&5&lGRACE PERIOD OVER!");
      this.playersRemainingSubtitle = ChatColor.translateAlternateColorCodes('&', "&7&l%s players remaining!");
      this.GG = ChatColor.translateAlternateColorCodes('&', "&2&lGG");
      this.GGSubtitle = ChatColor.translateAlternateColorCodes('&', "&7&l%s has won!");
      this.random = new Random();
      this.currentEventNumber = 1;
      this.randomBlock = randomBlock;
      this.world = new RandomWorld(randomBlock);
      this.world.setupWorld();
   }

   public void start() {
      this.startMillis = System.currentTimeMillis();
      this.currentMessage = "Grace Period. %s players are playing!";
      this.bossBar.setColor(BarColor.BLUE);
      this.startGameTimer();
   }

   public void startGameTimer() {
      this.gameTimer = (new BukkitRunnable() {
         public void run() {
            randomBlock.getEventHandler().reset();
            long currentTime = System.currentTimeMillis();
            Islands.this.bossBar.setTitle(String.format(Islands.this.currentMessage, Islands.this.randomBlock.getAlivePlayers().size()));
            if (startMillis + minute * 3L <= currentTime && Islands.this.gracePeriod) {
               gracePeriod = false;
               currentMessage = "Grace Period is over! %s players are alive!";
               bossBar.setColor(BarColor.PURPLE);
               Islands.this.sendTitleToAll(new Title(gracePeriodOver, String.format(Islands.this.playersRemainingSubtitle, Islands.this.randomBlock.getAlivePlayers().size())));
            }
            else if (startMillis + minute * 3L > currentTime) {
               bossBar.setProgress((currentTime - startMillis) / (double) (minute * 3));
            }
            else if (startMillis + minute * 3L + minute * currentEventNumber <= currentTime) {
               currentEventNumber++;
               Event event = randomBlock.getEventHandler().decideEvent();
               event.start(world.getBukkitWorld(), randomBlock);
               randomBlock.getEventHandler().reset();
               Islands.this.bossBar.setColor(BarColor.RED);
               Islands.this.currentMessage = event.getName() + " %s players are alive!";
               Islands.this.sendTitleToAll(new Title(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + event.getName(), String.format(Islands.this.playersRemainingSubtitle, Islands.this.randomBlock.getAlivePlayers().size())));
            }
            else {
               bossBar.setProgress((currentTime - (startMillis + minute * 3L + minute * (currentEventNumber - 1))) / (double) minute);
            }
         }
      }).runTaskTimer(this.randomBlock, 20L, 20L);
   }

   public void sendTitleToAll(Title title) {

      for (Player player : Bukkit.getOnlinePlayers()) {
         player.sendTitle(title);
      }

   }

   public void restart() {
      this.world.restartWorld();
      this.currentEventNumber = 1;
      this.gracePeriod = true;
      this.startMillis = System.currentTimeMillis();
      List<UUID> alivePlayers = this.randomBlock.getAlivePlayers();
      HashSet<UUID> startingPlayers = this.randomBlock.getStartingPlayers();
      alivePlayers.clear();
      startingPlayers.clear();

      for (Player player : Bukkit.getOnlinePlayers()) {
         this.bossBar.addPlayer(player);
         this.randomBlock.getAlivePlayers().add(player.getUniqueId());
         this.randomBlock.getStartingPlayers().add(player.getUniqueId());
      }

      this.respawnAllPlayers();
      this.start();
   }

   public void respawnAllPlayers() {
      for (Player player : Bukkit.getOnlinePlayers()) {
         spawn(player);
         player.setGameMode(GameMode.SURVIVAL);
         player.getInventory().clear();
         player.setHealth(20);
      }
   }

   @EventHandler
   public void playerJoin(PlayerJoinEvent e) {
      Player player = e.getPlayer();
      this.bossBar.addPlayer(player);
      if (this.randomBlock.getAlivePlayers().isEmpty()) {
         this.start();
      }

      if (this.gracePeriod) {
         this.spawn(player);
         this.randomBlock.getAlivePlayers().add(player.getUniqueId());
         this.randomBlock.getStartingPlayers().add(player.getUniqueId());
         player.setGameMode(GameMode.SURVIVAL);
         player.getInventory().clear();
      } else {
         Location location = new Location(this.world.getBukkitWorld(), 0.0D, 60.0D, 0.0D);
         player.teleport(location);
         player.setGameMode(GameMode.SPECTATOR);
      }

   }

   @EventHandler
   public void playerLeave(PlayerQuitEvent e) {
      if (this.randomBlock.getAlivePlayers().contains(e.getPlayer().getUniqueId())) {
         this.randomBlock.getAlivePlayers().remove(e.getPlayer().getUniqueId());
         if (this.gracePeriod) {
            this.randomBlock.getStartingPlayers().remove(e.getPlayer().getUniqueId());
         }
      }

      if (this.randomBlock.getAlivePlayers().size() == 0) {
         this.restart();
      }

   }

   @EventHandler
   public void onDeath(PlayerDeathEvent e) {
      Player player = e.getEntity();
      UUID uuid = player.getUniqueId();
      List<UUID> alivePlayers = this.randomBlock.getAlivePlayers();
      if (!this.gracePeriod && alivePlayers.contains(uuid)) {
         this.randomBlock.getAlivePlayers().remove(uuid);
         this.randomBlock.getDeadPlayers().add(uuid);
         if (alivePlayers.size() <= 1) {
            this.gameTimer.cancel();
            Player winnerPlayer;
            if (alivePlayers.size() == 1) {
               winnerPlayer = Bukkit.getPlayer(this.randomBlock.getAlivePlayers().get(0));
            } else {
               winnerPlayer = e.getEntity();
            }

            Bukkit.getPluginManager().callEvent(new GameEndEvent(winnerPlayer));
            this.sendTitleToAll(new Title(this.GG, String.format(this.GGSubtitle, winnerPlayer.getName())));
            this.restart();
         }
      }

   }

   @EventHandler
   public void onRespawn(PlayerPostRespawnEvent e) {
      Player player = e.getPlayer();
      if (this.gracePeriod) {
         this.spawn(player);
      } else {
         Location location = new Location(this.world.getBukkitWorld(), 0.0D, 60.0D, 0.0D);
         player.teleportAsync(location);
         player.setGameMode(GameMode.SPECTATOR);
      }
   }

   public void spawn(final Player player) {
      new BukkitRunnable() {
         public void run() {
            if (world.getBukkitWorld() != null) {
               dangerousSpawn(player);
               this.cancel();
            }

         }
      }.runTaskTimer(randomBlock, 30L, 5L);
   }

   public void dangerousSpawn(Player player) {
      player.getInventory().clear();
      Location locationOfBedrock = randomLocation();
      Location location = new Location(locationOfBedrock.getWorld(), locationOfBedrock.getX(), 82, locationOfBedrock.getZ());
      world.getBukkitWorld().getBlockAt(locationOfBedrock).setType(Material.BEDROCK);
      player.teleportAsync(location);
   }

   public Location randomLocation() {
      double angle = random.nextDouble() * 2 * Math.PI;
      double radius = 32 * Math.sqrt(randomBlock.getStartingPlayers().size()) * Math.sqrt(random.nextDouble());

      int x = (int) Math.round(radius * Math.cos(angle));
      int z = (int) Math.round(radius * Math.sin(angle));
      return new Location(world.getBukkitWorld(), x, 80, z);
   }
}
