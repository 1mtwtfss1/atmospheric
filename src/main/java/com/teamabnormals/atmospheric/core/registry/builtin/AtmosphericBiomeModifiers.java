package com.teamabnormals.atmospheric.core.registry.builtin;

import com.teamabnormals.atmospheric.core.Atmospheric;
import com.teamabnormals.atmospheric.core.registry.AtmosphericFeatures.AtmosphericPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtmosphericBiomeModifiers {

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		addFeature(context, "yucca_tree_desert", Biomes.DESERT, Decoration.VEGETAL_DECORATION, AtmosphericPlacedFeatures.DESERT_YUCCA_TREES);
		addFeature(context, "yucca_tree_windswept_savanna", Biomes.WINDSWEPT_SAVANNA, Decoration.VEGETAL_DECORATION, AtmosphericPlacedFeatures.WINDSWEPT_SAVANNA_YUCCA_TREES);
		addFeature(context, "wooded_badlands_vegetation", Biomes.WOODED_BADLANDS, Decoration.VEGETAL_DECORATION, AtmosphericPlacedFeatures.WOODED_BADLANDS_YUCCA_TREES, AtmosphericPlacedFeatures.PATCH_AGAVE_WOODED_BADLANDS);
	}

	@SafeVarargs
	private static void addFeature(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "add_feature/" + name, () -> new AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), featureSet(context, features), step));
	}

	@SafeVarargs
	private static void addFeature(BootstapContext<BiomeModifier> context, String name, ResourceKey<Biome> biome, Decoration step, ResourceKey<PlacedFeature>... features) {
		register(context, "add_feature/" + name, () -> new AddFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)), featureSet(context, features), step));
	}

	private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
		context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Atmospheric.MOD_ID, name)), modifier.get());
	}

	@SafeVarargs
	private static HolderSet<PlacedFeature> featureSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... features) {
		return HolderSet.direct(Stream.of(features).map(placedFeatureKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)).collect(Collectors.toList()));
	}
}