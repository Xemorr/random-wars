package me.xemor.randomwars;

import java.io.File;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RandomWorld {
   private World world;
   RandomWars randomBlock;
   private String randomBlockWorldName = "Random-Block";
   private String randomBlock2WorldName = "Random-Block-2";
   int i = 1;

   public RandomWorld(RandomWars randomBlock) {
      this.randomBlock = randomBlock;
   }

   public World getBukkitWorld() {
      return this.world;
   }

   public void setupWorld() {
      this.world = null;
      (new BukkitRunnable() {
         public void run() {
            File worldContainer = Bukkit.getWorldContainer();
            File worldFile = new File(worldContainer, "Random-Block");
            if (worldFile.exists()) {
               RandomWorld.this.i = 0;
               RandomWorld.this.deleteDir(worldFile);
            }

            worldFile = new File(worldContainer, "Random-Block-2");
            if (worldFile.exists()) {
               RandomWorld.this.i = 1;
               RandomWorld.this.deleteDir(worldFile);
            }

            (new BukkitRunnable() {
               public void run() {
                  String worldName = RandomWorld.this.i == 0 ? RandomWorld.this.randomBlockWorldName : RandomWorld.this.randomBlock2WorldName;
                  WorldCreator worldCreator = new WorldCreator(worldName);
                  worldCreator.generator(new VoidGenerator());
                  world = worldCreator.createWorld();
                  world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
                  world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
               }
            }).runTaskLater(RandomWorld.this.randomBlock, 10L);
         }
      }).runTaskLaterAsynchronously(this.randomBlock, 10L);
   }

   public void restartWorld() {
      List<Player> players = this.world.getPlayers();
      players.forEach((player) -> {
         player.teleport(new Location(Bukkit.getWorld("world"), 0.0D, 5.0D, 0.0D));
      });
      (new BukkitRunnable() {
         public void run() {
            boolean loadedWorld = Bukkit.unloadWorld(RandomWorld.this.world, false);
            if (!loadedWorld) {
               (new BukkitRunnable() {
                  public void run() {
                     System.out.println(Bukkit.unloadWorld(RandomWorld.this.world, false));
                  }
               }).runTaskLater(RandomWorld.this.randomBlock, 2L);
            }

            setupWorld();
         }
      }).runTaskLater(this.randomBlock, 30L);
   }

   public void deleteDir(File file) {
      File[] contents = file.listFiles();
      if (contents != null) {
         File[] var3 = contents;
         int var4 = contents.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File f = var3[var5];
            this.deleteDir(f);
         }
      }

      file.delete();
   }
}
