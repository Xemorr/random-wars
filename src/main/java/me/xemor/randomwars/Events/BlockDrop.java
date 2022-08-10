package me.xemor.randomwars.Events;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockDrop extends Event {
   private int numberOfBlocks;
   private Material blockToDrop;
   private String name;
   private boolean hurtsEntities = false;
   private ItemStack icon;

   public BlockDrop(String name, ItemStack icon, Material blockToDrop, int numberOfBlocks, boolean hurtsEntities) {
      super(name, icon);
      this.numberOfBlocks = numberOfBlocks;
      this.blockToDrop = blockToDrop;
      this.name = name;
      this.hurtsEntities = hurtsEntities;
      if (this.icon != null) {
         this.icon = new ItemStack(this.icon);
      }

   }

   public BlockDrop(String name, ItemStack icon, Material blockToDrop, int numberOfBlocks) {
      this(name, icon, blockToDrop, numberOfBlocks, false);
   }

   public void start(World world, me.xemor.randomwars.RandomWars randomWars) {
      for (UUID uuid : randomWars.getAlivePlayers()) {
         final Player player = Bukkit.getPlayer(uuid);

         for (int i = 0; i < this.numberOfBlocks; ++i) {
            (new BukkitRunnable() {
               public void run() {
                  Location location = player.getLocation();
                  location.setY(255.0D);
                  FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(location, BlockDrop.this.blockToDrop.createBlockData());
                  fallingBlock.setHurtEntities(BlockDrop.this.hurtsEntities);
               }
            }).runTaskLater(randomWars, (long) (10 * i));
         }
      }

   }

   public String getName() {
      return this.name;
   }

   public boolean playBossMusic() {
      return false;
   }

   public ItemStack getIcon() {
      return this.icon;
   }
}
