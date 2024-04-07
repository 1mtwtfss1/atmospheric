package com.teamabnormals.atmospheric.core.registry.builtin;

import com.teamabnormals.atmospheric.core.Atmospheric;
import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraftforge.registries.ForgeRegistries;

public class AtmosphericTrimPatterns {
	public static final ResourceKey<TrimPattern> APOSTLE = createKey("apostle");
	public static final ResourceKey<TrimPattern> DRUID = createKey("druid");
	public static final ResourceKey<TrimPattern> PETRIFIED = createKey("petrified");

	public static void bootstrap(BootstapContext<TrimPattern> context) {
		register(context, APOSTLE, AtmosphericItems.APOSTLE_ARMOR_TRIM_SMITHING_TEMPLATE.get());
		register(context, DRUID, AtmosphericItems.DRUID_ARMOR_TRIM_SMITHING_TEMPLATE.get());
		register(context, PETRIFIED, AtmosphericItems.PETRIFIED_ARMOR_TRIM_SMITHING_TEMPLATE.get());
	}

	public static ResourceKey<TrimPattern> createKey(String name) {
		return ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation(Atmospheric.MOD_ID, name));
	}

	private static void register(BootstapContext<TrimPattern> context, ResourceKey<TrimPattern> key, Item item) {
		context.register(key, new TrimPattern(key.location(), ForgeRegistries.ITEMS.getHolder(item).get(), Component.translatable(Util.makeDescriptionId("trim_pattern", key.location()))));
	}
}