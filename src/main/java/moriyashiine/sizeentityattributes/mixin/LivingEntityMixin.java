package moriyashiine.sizeentityattributes.mixin;

import jdk.internal.jline.internal.Nullable;
import moriyashiine.sizeentityattributes.SizeEntityAttributes;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	@Nullable
	public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);
	
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo callbackInfo) {
		if (age == 1 || world.isClient) {
			calculateDimensions();
		}
	}
	
	@Inject(method = "getActiveEyeHeight", at = @At("RETURN"), cancellable = true)
	private void getActiveEyeHeight(CallbackInfoReturnable<Float> callbackInfo) {
		if (age > 0) {
			EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
			if (heightMultiplier != null) {
				callbackInfo.setReturnValue((float) (callbackInfo.getReturnValue() * heightMultiplier.getValue()));
			}
		}
	}
	
	@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
	private void getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> callbackInfo) {
		EntityAttributeInstance widthMultiplier = getAttributeInstance(SizeEntityAttributes.WIDTH_MULTIPLIER);
		EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (widthMultiplier != null && heightMultiplier != null) {
			callbackInfo.setReturnValue(callbackInfo.getReturnValue().scaled((float) widthMultiplier.getValue(), (float) heightMultiplier.getValue()));
		}
	}
	
	@Inject(method = "createLivingAttributes", at = @At("RETURN"))
	private static void createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> builder) {
		builder.getReturnValue().add(SizeEntityAttributes.WIDTH_MULTIPLIER);
		builder.getReturnValue().add(SizeEntityAttributes.HEIGHT_MULTIPLIER);
	}
}
