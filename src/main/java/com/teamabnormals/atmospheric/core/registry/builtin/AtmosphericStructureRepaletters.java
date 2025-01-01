package com.teamabnormals.atmospheric.core.registry.builtin;

import com.google.common.collect.Maps;
import com.teamabnormals.atmospheric.core.Atmospheric;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.blueprint.common.world.modification.structure.SimpleStructureRepaletter;
import com.teamabnormals.blueprint.common.world.modification.structure.StructureRepaletter;
import com.teamabnormals.blueprint.common.world.modification.structure.StructureRepaletterEntry;
import com.teamabnormals.blueprint.common.world.modification.structure.WeightedStructureRepaletter;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import com.teamabnormals.blueprint.core.registry.BlueprintHolderSets;
import com.teamabnormals.woodworks.core.WoodworksConfig;
import com.teamabnormals.woodworks.core.data.server.WoodworksRecipeProvider;
import com.teamabnormals.woodworks.core.registry.WoodworksBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.teamabnormals.atmospheric.core.AtmosphericConfig.COMMON;

public final class AtmosphericStructureRepaletters {
	public static final ConfigValueCondition GRIMWOOD_ANCIENT_CITIES = config(COMMON.grimwoodAncientCities, "grimwood_ancient_cities");
	public static final ConfigValueCondition YUCCA_DESERT_VILLAGES = config(COMMON.yuccaDesertVillages, "yucca_desert_villages");

	public static final ConfigValueCondition WOODEN_BOOKSHELVES_IN_VILLAGES = WoodworksRecipeProvider.config(WoodworksConfig.COMMON.woodenBookshelvesInVillages, "wooden_bookshelves_in_villages");
	public static final ConfigValueCondition WOODEN_LADDERS_IN_VILLAGES = WoodworksRecipeProvider.config(WoodworksConfig.COMMON.woodenLaddersInVillages, "wooden_ladders_in_villages");
	public static final ConfigValueCondition WOODEN_CHESTS_IN_VILLAGES = WoodworksRecipeProvider.config(WoodworksConfig.COMMON.woodenChestsInVillages, "wooden_chests_in_villages");

	public static void bootstrap(BootstapContext<StructureRepaletterEntry> context) {
		HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

		yuccaDesertVillage(context, structures, "yucca_buttons", Blocks.JUNGLE_BUTTON, AtmosphericBlocks.YUCCA_BUTTON.get());
		yuccaDesertVillage(context, structures, "yucca_doors", Blocks.JUNGLE_DOOR, AtmosphericBlocks.YUCCA_DOOR.get());
		yuccaDesertVillage(context, structures, "yucca_fences", Blocks.JUNGLE_FENCE, AtmosphericBlocks.YUCCA_FENCE.get());
		yuccaDesertVillage(context, structures, "yucca_fence_gates", Blocks.JUNGLE_FENCE_GATE, AtmosphericBlocks.YUCCA_FENCE_GATE.get());
		yuccaDesertVillage(context, structures, "yucca_trapdoors", Blocks.JUNGLE_TRAPDOOR, AtmosphericBlocks.YUCCA_TRAPDOOR.get());

		yuccaDesertVillage(context, structures, yuccaDesertVillageCondition(WoodworksRecipeProvider.WOODEN_LADDERS, WOODEN_LADDERS_IN_VILLAGES), "yucca_ladders", Blocks.LADDER, AtmosphericBlocks.YUCCA_LADDER.get(), 200);
		yuccaDesertVillage(context, structures, yuccaDesertVillageCondition(WoodworksRecipeProvider.WOODEN_CHESTS, WOODEN_CHESTS_IN_VILLAGES), "yucca_chests", Blocks.CHEST, AtmosphericBlocks.YUCCA_CHEST.get(), 200);
		yuccaDesertVillage(context, structures, yuccaDesertVillageCondition(WoodworksRecipeProvider.WOODEN_BOOKSHELVES, WOODEN_BOOKSHELVES_IN_VILLAGES), "yucca_bookshelves", Blocks.BOOKSHELF, AtmosphericBlocks.YUCCA_BOOKSHELF.get(), 200);

		grimwoodAncientCity(context, structures, "grimwood_logs", Blocks.DARK_OAK_LOG, AtmosphericBlocks.GRIMWOOD_LOG.get());
		grimwoodAncientCity(context, structures, "grimwood_fences", Blocks.DARK_OAK_FENCE, AtmosphericBlocks.GRIMWOOD_FENCE.get());
		grimwoodAncientCity(context, structures, "grimwood_planks", Blocks.DARK_OAK_PLANKS, AtmosphericBlocks.GRIMWOOD_PLANKS.get());

		grimwoodAncientCity(context, structures, grimwoodAncientCityCondition(WoodworksRecipeProvider.WOODEN_LADDERS, WOODEN_LADDERS_IN_VILLAGES), "grimwood_ladders", Blocks.LADDER, AtmosphericBlocks.GRIMWOOD_LADDER.get());
		grimwoodAncientCity(context, structures, grimwoodAncientCityCondition(WoodworksRecipeProvider.WOODEN_CHESTS, WOODEN_CHESTS_IN_VILLAGES), "grimwood_chests", Blocks.CHEST, AtmosphericBlocks.GRIMWOOD_CHEST.get());

		// TODO: Datagen structures so I can use this
		// laurelScrublandVillage(context, structures, laurelScrublandVillageCondition(WoodworksRecipeProvider.WOODEN_LADDERS, WOODEN_LADDERS_IN_VILLAGES), "laurel_ladders", Blocks.LADDER, AtmosphericBlocks.LAUREL_LADDER.get());
		// scrublandVillageChests(context, structures, laurelScrublandVillageCondition(WoodworksRecipeProvider.WOODEN_CHESTS, WOODEN_CHESTS_IN_VILLAGES), "laurel_and_morado_chests");
		// laurelScrublandVillage(context, structures, laurelScrublandVillageCondition(WoodworksRecipeProvider.WOODEN_BOOKSHELVES, WOODEN_BOOKSHELVES_IN_VILLAGES), "laurel_bookshelves", Blocks.BOOKSHELF, AtmosphericBlocks.LAUREL_BOOKSHELF.get());
	}

	private static void yuccaDesertVillage(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, ICondition condition, String name, Block replacesBlock, Block replacesWith, int priority) {
		basicRepaletter(context, structures, condition, name + "_in_desert_villages", replacesBlock, replacesWith, priority, BuiltinStructures.VILLAGE_DESERT);
	}

	private static void yuccaDesertVillage(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, String name, Block replacesBlock, Block replacesWith) {
		yuccaDesertVillage(context, structures, YUCCA_DESERT_VILLAGES, name, replacesBlock, replacesWith, 100);
	}

	private static void grimwoodAncientCity(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, ICondition condition, String name, Block replacesBlock, Block replacesWith) {
		basicRepaletter(context, structures, condition, name + "_in_ancient_cities", replacesBlock, replacesWith, BuiltinStructures.ANCIENT_CITY);
	}

	private static void grimwoodAncientCity(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, String name, Block replacesBlock, Block replacesWith) {
		grimwoodAncientCity(context, structures, GRIMWOOD_ANCIENT_CITIES, name, replacesBlock, replacesWith);
	}

	private static void laurelScrublandVillage(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, ICondition condition, String name, Block replacesBlock, Block replacesWith) {
		basicRepaletter(context, structures, condition, name + "_in_scrubland_villages", replacesBlock, replacesWith, BuiltinStructures.VILLAGE_DESERT);
	}

	private static void scrublandVillageChests(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, ICondition condition, String name) {
		repaletter(context, structures, condition, name + "_in_scrubland_villages", new WeightedStructureRepaletter(Tags.Blocks.CHESTS_WOODEN,
				WeightedRandomList.create(WeightedEntry.wrap(AtmosphericBlocks.LAUREL_CHEST.get(), 1), WeightedEntry.wrap(AtmosphericBlocks.MORADO_CHEST.get(), 1))
		), 100, BuiltinStructures.VILLAGE_DESERT);
	}

	@SafeVarargs
	private static void basicRepaletter(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, ICondition condition, String name, Block replacesBlock, Block replacesWith, ResourceKey<Structure>... selector) {
		basicRepaletter(context, structures, condition, name, replacesBlock, replacesWith, 100, selector);
	}

	@SafeVarargs
	private static void basicRepaletter(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, ICondition condition, String name, Block replacesBlock, Block replacesWith, int priority, ResourceKey<Structure>... selector) {
		repaletter(context, structures, condition, name, new SimpleStructureRepaletter(replacesBlock, replacesWith), priority, selector);
	}

	@SafeVarargs
	private static void repaletter(BootstapContext<StructureRepaletterEntry> context, HolderGetter<Structure> structures, ICondition condition, String name, StructureRepaletter repaletter, int priority, ResourceKey<Structure>... selector) {
		context.register(
				repaletterKey(name),
				new StructureRepaletterEntry(
						BlueprintHolderSets.conditional(HolderSet.direct(Stream.of(selector).map(structures::getOrThrow).collect(Collectors.toList())), condition),
						Optional.empty(), false, priority, repaletter)
		);
	}

	public static ConfigValueCondition config(ForgeConfigSpec.ConfigValue<?> value, String key, boolean inverted) {
		return new ConfigValueCondition(new ResourceLocation(Atmospheric.MOD_ID, "config"), value, key, Maps.newHashMap(), inverted);
	}

	public static ConfigValueCondition config(ForgeConfigSpec.ConfigValue<?> value, String key) {
		return config(value, key, false);
	}

	private static ICondition yuccaDesertVillageCondition(ICondition condition1, ICondition condition2) {
		return new AndCondition(YUCCA_DESERT_VILLAGES, WoodworksRecipeProvider.WOODWORKS_LOADED, condition1, condition2);
	}

	private static ICondition grimwoodAncientCityCondition(ICondition condition1, ICondition condition2) {
		return new AndCondition(GRIMWOOD_ANCIENT_CITIES, WoodworksRecipeProvider.WOODWORKS_LOADED, condition1, condition2);
	}

	private static ICondition laurelScrublandVillageCondition(ICondition condition1, ICondition condition2) {
		return new AndCondition(WoodworksRecipeProvider.WOODWORKS_LOADED, condition1, condition2);
	}

	private static ResourceKey<StructureRepaletterEntry> repaletterKey(String name) {
		return ResourceKey.create(BlueprintDataPackRegistries.STRUCTURE_REPALETTERS, new ResourceLocation(Atmospheric.MOD_ID, name));
	}
}