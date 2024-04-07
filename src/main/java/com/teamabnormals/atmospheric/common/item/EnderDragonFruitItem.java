package com.teamabnormals.atmospheric.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class EnderDragonFruitItem extends Item {

	public EnderDragonFruitItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (entity instanceof Player) {
			if (entity instanceof ServerPlayer player) {
				CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
				player.awardStat(Stats.ITEM_USED.get(this));
			}

			stack.shrink(1);
			SoundEvent sound = SoundEvents.CHORUS_FRUIT_TELEPORT;
			level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
			entity.playSound(sound, 1.0F, 1.0F);
			entity.playSound(SoundEvents.ENDER_DRAGON_GROWL, 1.0F, 0.8F + entity.getRandom().nextFloat() * 0.3F);

			for (int i = 0; i < 8; ++i) {
				Vec3 vec3 = new Vec3(((double) entity.getRandom().nextFloat() - 0.5D), Math.random() * 0.1D + 0.1D, 0.0D);
				vec3 = vec3.xRot(-entity.getXRot() * ((float) Math.PI / 180F));
				vec3 = vec3.yRot(-entity.getYRot() * ((float) Math.PI / 180F));
				double d0 = (double) (-entity.getRandom().nextFloat()) * 0.1D - 0.3D;
				Vec3 vec31 = new Vec3(((double) entity.getRandom().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
				vec31 = vec31.xRot(-entity.getXRot() * ((float) Math.PI / 180F));
				vec31 = vec31.yRot(-entity.getYRot() * ((float) Math.PI / 180F));
				vec31 = vec31.add(entity.getX(), entity.getEyeY(), entity.getZ());
				ParticleOptions particleOptions = new DustParticleOptions(Vec3.fromRGB24(14437887).toVector3f(), 1.0F);
				if (entity.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(particleOptions, vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
				} else {
					entity.level().addParticle(particleOptions, vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
				}
			}

			return stack;
		} else {
			return super.finishUsingItem(stack, level, entity);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		for (MobEffect effect : new MobEffect[]{MobEffects.ABSORPTION, MobEffects.REGENERATION, MobEffects.DAMAGE_RESISTANCE}) {
			MutableComponent component = Component.translatable(effect.getDescriptionId());
			MobEffectInstance instance = new MobEffectInstance(effect, 12000, 9);
			component = Component.translatable("potion.withAmplifier", component, Component.translatable("potion.potency." + instance.getAmplifier()));
			component = Component.translatable("potion.withDuration", component, MobEffectUtil.formatDuration(instance, 1));
			tooltip.add(component.withStyle(effect.getCategory().getTooltipFormatting()));
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 6000;
	}
}