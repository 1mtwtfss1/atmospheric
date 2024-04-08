package com.teamabnormals.atmospheric.core.mixin.client;

import com.teamabnormals.blueprint.core.api.BlueprintRabbitTypes;
import com.teamabnormals.blueprint.core.api.BlueprintRabbitTypes.BlueprintRabbitType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Rabbit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitRenderer.class)
public abstract class RabbitRendererMixin {

	@Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Rabbit;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
	private void getTextureLocation(Rabbit rabbit, CallbackInfoReturnable<ResourceLocation> cir) {
		String s = ChatFormatting.stripFormatting(rabbit.getName().getString());
		if (!"Toast".equals(s)) {
			for (BlueprintRabbitType rabbitType : BlueprintRabbitTypes.values()) {
				if (rabbit.getVariant().id() == rabbitType.id()) {
					cir.setReturnValue(rabbitType.textureLocation());
					break;
				}
			}
		}
	}
}