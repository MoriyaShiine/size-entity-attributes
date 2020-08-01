package moriyashiine.sizeentityattributes.mixin;

import moriyashiine.sizeentityattributes.SizeEntityAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({RavagerEntity.class, SpiderEntity.class})
public abstract class ModifyMountedHeightOffset extends LivingEntity {
	protected ModifyMountedHeightOffset(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "getMountedHeightOffset", at = @At("RETURN"), cancellable = true)
	private void getHeightOffset(CallbackInfoReturnable<Double> info) {
		EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (heightMultiplier != null) {
			double heightMultiplierValue = getAttributeValue(SizeEntityAttributes.HEIGHT_MULTIPLIER);
			info.setReturnValue(info.getReturnValue() * heightMultiplierValue);
		}
	}
}