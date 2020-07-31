package moriyashiine.sizeentityattributes.mixin;

import moriyashiine.sizeentityattributes.SizeEntityAttributes;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
	private void getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> callbackInfo) {
		EntityAttributeInstance widthMultiplier = getAttributeInstance(SizeEntityAttributes.WIDTH_MULTIPLIER);
		EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (widthMultiplier != null && heightMultiplier != null) {
			callbackInfo.setReturnValue(callbackInfo.getReturnValue().scaled((float) widthMultiplier.getValue(), (float) heightMultiplier.getValue()));
		}
	}
	
	@Inject(method = "getActiveEyeHeight", at = @At("RETURN"), cancellable = true)
	public void getActiveEyeHeight(CallbackInfoReturnable<Float> callbackInfo) {
		if (age > 0) {
			EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
			if (heightMultiplier != null) {
				callbackInfo.setReturnValue((float) (callbackInfo.getReturnValue() * heightMultiplier.getValue() - 1 / 128f + (getPose() == EntityPose.SWIMMING && heightMultiplier.getValue() < 1 ? heightMultiplier.getValue() / 8 : 0)));
			}
		}
	}
}