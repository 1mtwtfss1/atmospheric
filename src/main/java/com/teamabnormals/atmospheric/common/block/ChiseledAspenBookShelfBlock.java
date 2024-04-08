package com.teamabnormals.atmospheric.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintChiseledBookShelfBlock;
import net.minecraft.world.phys.Vec2;

public class ChiseledAspenBookShelfBlock extends BlueprintChiseledBookShelfBlock {

	public ChiseledAspenBookShelfBlock(Properties properties) {
		super(properties);
	}

	@Override
	public int getHitSlot(Vec2 vec2) {
		if (vec2.y < 0.625F && vec2.y > 0.3125F)
			return vec2.x < 0.4375F ? 2 : 3;
		return getSection(vec2.x, vec2.y);
	}

	public static int getSection(float x, float y) {
		int section = x < 0.5F ? 0 : 1;
		if (y < 0.625) {
			section += 4;
		}

		return section;
	}
}
