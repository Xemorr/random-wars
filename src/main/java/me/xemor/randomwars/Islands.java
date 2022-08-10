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
import me.xemor.randomwars.Spectator.SpectatorPlayer;
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
   private Random random;
   int currentEventNumber;
   String currentMessage;
   RandomWars randomBlock;

   public Islands(RandomWars randomBlock) {
      this.bossBar = Bukkit.createBossBar("Grace Period.", BarColor.BLUE, BarStyle.SOLID, new BarFlag[0]);
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
            Long currentTime = System.currentTimeMillis();
            Islands.this.bossBar.setTitle(String.format(Islands.this.currentMessage, Islands.this.randomBlock.getAlivePlayers().size()));
            if (startMillis + minute * 3L <= currentTime && Islands.this.gracePeriod) {
               Islands.this.gracePeriod = false;
               Islands.this.currentMessage = "Grace Period is over! %s players are alive!";
               Islands.this.bossBar.setColor(BarColor.PURPLE);
               Islands.this.sendTitleToAll(new Title(gracePeriodOver, String.format(Islands.this.playersRemainingSubtitle, Islands.this.randomBlock.getAlivePlayers().size())));
            }

            if (startMillis + minute * 3L + minute * currentEventNumber <= currentTime) {
               currentEventNumber++;
               Event event = randomBlock.getEventHandler().decideEvent();
               event.start(world.getBukkitWorld(), randomBlock);
               Islands.this.bossBar.setColor(BarColor.RED);
               Islands.this.currentMessage = event.getName() + " %s players are alive!";
               Islands.this.sendTitleToAll(new Title(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + event.getName(), String.format(Islands.this.playersRemainingSubtitle, Islands.this.randomBlock.getAlivePlayers().size())));
            }

         }
      }).runTaskTimer(this.randomBlock, 20L, 20L);
   }

   public void sendTitleToAll(Title title) {
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player player = (Player)var2.next();
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
      Iterator var3 = Bukkit.getOnlinePlayers().iterator();

      while(var3.hasNext()) {
         Player player = (Player)var3.next();
         this.bossBar.addPlayer(player);
         SpectatorPlayer spectatorPlayer = new SpectatorPlayer(player);
         spectatorPlayer.unsetSpectator(this.randomBlock);
         this.randomBlock.getAlivePlayers().add(player.getUniqueId());
         this.randomBlock.getStartingPlayers().add(player.getUniqueId());
      }

      this.respawnAllPlayers();
      this.start();
   }

   public void respawnAllPlayers() {
      Iterator var1 = Bukkit.getOnlinePlayers().iterator();

      while(var1.hasNext()) {
         Player player = (Player)var1.next();
         this.spawn(player);
         player.setGameMode(GameMode.SURVIVAL);
         player.getInventory().clear();
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
         player.teleportAsync(location);
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
               winnerPlayer = Bukkit.getPlayer((UUID)this.randomBlock.getAlivePlayers().get(0));
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
         SpectatorPlayer spectatorPlayer = new SpectatorPlayer(player);
         spectatorPlayer.setSpectator(this.randomBlock);
      }

   }

   public void spawn(final Player player) {
      (new BukkitRunnable() {
         public void run() {
            if (Islands.this.world.getBukkitWorld() == null) {
               (new BukkitRunnable() {
                  public void run() {
                     if (Islands.this.world.getBukkitWorld() != null) {
                        Islands.this.dangerousSpawn(player);
                        this.cancel();
                     }

                  }
               }).runTaskTimer(Islands.this.randomBlock, 5L, 5L);
            } else {
               Islands.this.dangerousSpawn(player);
            }

         }
      }).runTaskLater(this.randomBlock, 30L);
   }

   public void dangerousSpawn(Player player) {
      player.getInventory().clear();
      int x = this.randomLocation();
      int z = this.randomLocation();
      Location location = new Location(this.world.getBukkitWorld(), (double)x, 52.0D, (double)z);
      Location locationOfBedrock = new Location(this.world.getBukkitWorld(), (double)x, 50.0D, (double)z);
      this.world.getBukkitWorld().getBlockAt(locationOfBedrock).setType(Material.BEDROCK);
      player.teleportAsync(location);
   }

   public int randomLocation() {
      Random random = new Random();
      int range = (int)Math.ceil(Math.sqrt((double)this.randomBlock.getStartingPlayers().size()) * 24.0D);
      int rng = random.nextInt(range);
      return (int)((double)(rng * 2) - Math.ceil(Math.sqrt((double)(this.randomBlock.getStartingPlayers().size() * 24))));
   }
}
