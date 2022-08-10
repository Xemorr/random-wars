package me.xemor.randomwars;

import java.util.Random;
import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

public class VoidGenerator extends ChunkGenerator {

   public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
      //chunkData.setRegion(0, -40, 0, 15, 320, 15, Material.AIR);
   }


}
