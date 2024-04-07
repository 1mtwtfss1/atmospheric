package com.teamabnormals.atmospheric.core.data.client;

import com.teamabnormals.atmospheric.core.Atmospheric;
import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import static com.teamabnormals.atmospheric.core.registry.AtmosphericItems.*;

public class AtmosphericItemModelProvider extends ItemModelProvider {

	public AtmosphericItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Atmospheric.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		this.generatedItem(
				ROSEWOOD_BOAT.getFirst().get(), ROSEWOOD_BOAT.getSecond().get(), ROSEWOOD_FURNACE_BOAT.get(), LARGE_ROSEWOOD_BOAT.get(),
				MORADO_BOAT.getFirst().get(), MORADO_BOAT.getSecond().get(), MORADO_FURNACE_BOAT.get(), LARGE_MORADO_BOAT.get(),
				YUCCA_BOAT.getFirst().get(), YUCCA_BOAT.getSecond().get(), YUCCA_FURNACE_BOAT.get(), LARGE_YUCCA_BOAT.get(),
				ASPEN_BOAT.getFirst().get(), ASPEN_BOAT.getSecond().get(), ASPEN_FURNACE_BOAT.get(), LARGE_ASPEN_BOAT.get(),
				LAUREL_BOAT.getFirst().get(), LAUREL_BOAT.getSecond().get(), LAUREL_FURNACE_BOAT.get(), LARGE_LAUREL_BOAT.get(),
				KOUSA_BOAT.getFirst().get(), KOUSA_BOAT.getSecond().get(), KOUSA_FURNACE_BOAT.get(), LARGE_KOUSA_BOAT.get(),
				GRIMWOOD_BOAT.getFirst().get(), GRIMWOOD_BOAT.getSecond().get(), GRIMWOOD_FURNACE_BOAT.get(), LARGE_GRIMWOOD_BOAT.get()
		);

		this.generatedItem(AtmosphericBlocks.YUCCA_GATEAU.get());
		this.handheldItem(AtmosphericBlocks.YUCCA_BRANCH.get());
		this.generatedItem(
				PASSION_FRUIT.get(), SHIMMERING_PASSION_FRUIT.get(), PASSION_FRUIT_SORBET.get(), PASSION_FRUIT_TART.get(), PASSION_VINE_COIL.get(),
				ALOE_GEL_BOTTLE.get(), ALOE_KERNELS.get(), ALOE_LEAVES.get(), YELLOW_BLOSSOMS.get(), YUCCA_FRUIT.get(), ROASTED_YUCCA_FRUIT.get(),
				CURRANT.get(), CURRANT_MUFFIN.get(),
				CARMINE_HUSK.get(), COCHINEAL_BANNER_PATTERN.get(), AtmosphericBlocks.DRAGON_ROOTS.get().asItem(), DRAGON_FRUIT.get(), GOLDEN_DRAGON_FRUIT.get(), ENDER_DRAGON_FRUIT.get(),
				ORANGE_PUDDING.get(), ORANGE_SORBET.get(), CANDIED_ORANGE_SLICES.get(), BLOOD_ORANGE.get(),
				SCYTHE_POTTERY_SHERD.get(), SUCCULENT_POTTERY_SHERD.get(), SUN_POTTERY_SHERD.get(),
				APOSTLE_ARMOR_TRIM_SMITHING_TEMPLATE.get(), DRUID_ARMOR_TRIM_SMITHING_TEMPLATE.get(), PETRIFIED_ARMOR_TRIM_SMITHING_TEMPLATE.get()
		);

		this.spawnEggItem(COCHINEAL_SPAWN_EGG.get());

		ModelFile annoying = this.item(new ResourceLocation(Atmospheric.MOD_ID, "annoying_orange"), "generated");
		this.item(ORANGE.get(), "generated").override().model(annoying).predicate(new ResourceLocation(Atmospheric.MOD_ID, "hey_apple"), 1.0F);
	}

	private void generatedItem(ItemLike... items) {
		for (ItemLike item : items)
			item(item, "generated");
	}

	private void handheldItem(ItemLike... items) {
		for (ItemLike item : items)
			item(item, "handheld");
	}

	private ItemModelBuilder item(ItemLike item, String type) {
		return item(ForgeRegistries.ITEMS.getKey(item.asItem()), type);
	}

	private ItemModelBuilder item(ResourceLocation itemName, String type) {
		return withExistingParent(itemName.getPath(), "item/" + type).texture("layer0", new ResourceLocation(this.modid, "item/" + itemName.getPath()));
	}

	private void spawnEggItem(ItemLike... items) {
		for (ItemLike item : items) {
			ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(item.asItem());
			withExistingParent(itemName.getPath(), "item/template_spawn_egg");
		}
	}

	private void blockItem(Block block) {
		ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);
		this.getBuilder(name.getPath()).parent(new UncheckedModelFile(new ResourceLocation(this.modid, "block/" + name.getPath())));
	}
}