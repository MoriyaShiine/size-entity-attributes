package moriyashiine.sizeentityattributes.mixin.client;

import moriyashiine.sizeentityattributes.SizeEntityAttributes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
	@Inject(method = "scale", at = @At("TAIL"))
	private void scale(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, CallbackInfo callbackInfo) {
		EntityAttributeInstance widthMultiplier = abstractClientPlayerEntity.getAttributeInstance(SizeEntityAttributes.WIDTH_MULTIPLIER);
		EntityAttributeInstance heightMultiplier = abstractClientPlayerEntity.getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (widthMultiplier != null && heightMultiplier != null) {
			matrixStack.scale((float) widthMultiplier.getValue(), (float) heightMultiplier.getValue(), (float) widthMultiplier.getValue());
		}
	}
	
	@Inject(method = "setupTransforms", at = @At("TAIL"))
	private void setupTransforms(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, float g, float h, CallbackInfo callbackInfo) {
		EntityAttributeInstance heightMultiplier = abstractClientPlayerEntity.getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (heightMultiplier != null && heightMultiplier.getValue() < 1) {
			float leaningPitch = abstractClientPlayerEntity.getLeaningPitch(h);
			if (leaningPitch > 0 && abstractClientPlayerEntity.isInSwimmingPose()) {
				matrixStack.translate(0, 1, -0.3);
			}
		}
	}
}
