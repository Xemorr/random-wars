package me.xemor.randomwars;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Supply implements Listener {
   private final Material[] bannedMaterials;
   private final String[] bannedStrings;
   private final String actionbar20sMessage;
   private final String actionbarSneakMessage;
   private final List<ItemStack> items;
   private final RandomWars randomBlock;
   private final Random random;

   public Supply(RandomWars pl) {
      this.bannedMaterials = new Material[]{Material.FIRE, Material.REDSTONE_WALL_TORCH, Material.JIGSAW, Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID, Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.WALL_TORCH, Material.END_GATEWAY, Material.BEETROOTS, Material.POTATOES, Material.COCOA, Material.MOVING_PISTON, Material.PISTON_HEAD, Material.CARROTS, Material.END_PORTAL, Material.WATER, Material.ATTACHED_MELON_STEM, Material.ATTACHED_PUMPKIN_STEM, Material.LAVA, Material.NETHER_PORTAL, Material.REDSTONE_WIRE, Material.AIR, Material.CAVE_AIR, Material.VOID_AIR};
      this.bannedStrings = new String[]{"POTTED", "LEGACY", "WALL_SIGN", "WALL_FAN", "WALL_BANNER", "CANDLE"};
      this.actionbar20sMessage = ChatColor.translateAlternateColorCodes('&', "&8&l[&d&lE&7&lS&8&l] &7You have received the 1s random item!");
      this.actionbarSneakMessage = ChatColor.translateAlternateColorCodes('&', "&8&l[&d&lE&7&lS&8&l] &7You have received the sneaking a lot random item!");
      this.random = new Random();
      this.randomBlock = pl;
      Material[] material = Material.values();
      this.items = new ArrayList<>();

      for(int i = 0; i < material.length; ++i) {
         if (!this.isBanned(material[i])) {
            this.items.add(new ItemStack(material[i]));
         }
      }

      this.addExtraItems();
      (new BukkitRunnable() {
         public void run() {

            for (Player player : Bukkit.getOnlinePlayers()) {
               if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                  Inventory inventory = player.getInventory();
                  inventory.addItem(getRandomItem());
                  player.sendActionBar(actionbar20sMessage);
               }
            }

         }
      }).runTaskTimer(pl, 0L, 200L);
   }

   @EventHandler
   public void onSneak(final PlayerToggleSneakEvent e) {
      if (e.isSneaking()) {
         (new BukkitRunnable() {
            public void run() {
               Player player = e.getPlayer();
               if (player.isSneaking()) {
                  int rng = Supply.this.random.nextInt(22);
                  if (rng == 16) {
                     player.getInventory().addItem(new ItemStack[]{Supply.this.getRandomItem()});
                     player.sendActionBar(Supply.this.actionbarSneakMessage);
                  }
               } else {
                  this.cancel();
               }

            }
         }).runTaskTimer(this.randomBlock, 3L, 3L);
      }

   }

   public void addExtraItems() {
      this.items.add(new ItemStack(Material.COBBLESTONE, 4));
      this.items.add(new ItemStack(Material.OAK_LOG, 4));
      this.items.add(new ItemStack(Material.SPRUCE_LOG, 4));
      this.items.add(new ItemStack(Material.DIAMOND_PICKAXE));
      this.items.add(new ItemStack(Material.DIAMOND_SWORD));
      this.items.add(new ItemStack(Material.OBSIDIAN, 5));
      this.items.add(new ItemStack(Material.ENDER_EYE, 5));
      this.items.add(new ItemStack(Material.END_PORTAL, 4));
      this.items.add(new ItemStack(Material.STONE, 5));
      this.items.add(new ItemStack(Material.FLINT_AND_STEEL, 1));
      this.items.add(new ItemStack(Material.ELYTRA, 1));
      this.items.add(new ItemStack(Material.FIREWORK_ROCKET, 2));
      this.items.add(new ItemStack(Material.ARROW, 4));
      this.items.add(new ItemStack(Material.BOW, 1));
      this.items.add(new ItemStack(Material.CROSSBOW, 1));
      this.items.add(new ItemStack(Material.SPECTRAL_ARROW, 2));
      this.items.add(new ItemStack(Material.OAK_PLANKS, 3));
      this.items.add(new ItemStack(Material.COOKED_BEEF, 2));
   }

   public ItemStack getRandomItem() {
      Random random = new Random();
      ItemStack item = this.items.get(random.nextInt(this.items.size())).clone();
      item.setAmount(Math.min(item.getAmount() * random.nextInt(5) + 1, item.getMaxStackSize()));
      return item;
   }

   public boolean isBanned(Material material) {
      Material[] var2 = this.bannedMaterials;
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         Material mat = var2[var4];
         if (material.equals(mat)) {
            return true;
         }
      }

      String[] var6 = this.bannedStrings;
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         String str = var6[var4];
         if (material.name().contains(str)) {
            return true;
         }
      }

      return false;
   }
}
