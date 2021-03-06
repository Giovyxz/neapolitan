package com.minecraftabnormals.neapolitan.core.other;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.neapolitan.core.Neapolitan;
import com.minecraftabnormals.neapolitan.core.registry.NeapolitanEntities;
import com.minecraftabnormals.neapolitan.core.registry.NeapolitanFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Neapolitan.MOD_ID)
public class NeapolitanBiomeFeatures {

	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		Biome.Category category = event.getCategory();
		if (biome == null) return;
		RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, biome);

		if (category.equals(Biome.Category.JUNGLE) || biome.getPath().contains("rainforest"))
			event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(NeapolitanEntities.PLANTAIN_SPIDER.get(), 120, 3, 5));

		if (category.equals(Biome.Category.PLAINS))
			event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NeapolitanFeatures.Configured.STRAWBERRY_PATCH);

		if (category.equals(Biome.Category.SAVANNA))
			event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NeapolitanFeatures.Configured.VANILLA_VINE_PATCH);

		if (event.getClimate().precipitation.equals(RainType.RAIN)) {
			if (category.equals(Biome.Category.BEACH))
				event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NeapolitanFeatures.Configured.BANANA_PLANT_BEACH);

			if (category.equals(Biome.Category.JUNGLE)) {
				if (!DataUtil.matchesKeys(biome, Biomes.BAMBOO_JUNGLE) && !DataUtil.matchesKeys(biome, Biomes.BAMBOO_JUNGLE_HILLS)) {
					if (!DataUtil.matchesKeys(biome, Biomes.JUNGLE_EDGE) && !DataUtil.matchesKeys(biome, Biomes.MODIFIED_JUNGLE_EDGE))
						event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NeapolitanFeatures.Configured.BANANA_PLANT_JUNGLE);
					else
						event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NeapolitanFeatures.Configured.BANANA_PLANT_RARE);
				}
			}

			if (biome.getPath().contains("rainforest")) {
				if (BiomeDictionary.hasType(key, BiomeDictionary.Type.FOREST))
					event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NeapolitanFeatures.Configured.BANANA_PLANT_RARE);
				else
					event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NeapolitanFeatures.Configured.BANANA_PLANT_VERY_RARE);
			}
		}
	}
}
