package com.teamabnormals.atmospheric.core.registry.helper;

import com.teamabnormals.atmospheric.common.item.MonkeyBrushItem;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AtmosphericBlockSubRegistryHelper extends BlockSubRegistryHelper {

	public AtmosphericBlockSubRegistryHelper(RegistryHelper parent) {
		super(parent, parent.getItemSubHelper().getDeferredRegister(), parent.getBlockSubHelper().getDeferredRegister());
	}

	public <B extends Block> RegistryObject<B> createWallOrVerticalBlock(String name, String wallName, Supplier<? extends B> supplier, Supplier<? extends B> wallSupplier) {
		RegistryObject<B> block = this.deferredRegister.register(wallName, wallSupplier);
		parent.getSubHelper(ForgeRegistries.ITEMS).getDeferredRegister().register(name, () -> new MonkeyBrushItem(supplier.get(), block.get(), new Item.Properties()));
		return block;
	}
}
