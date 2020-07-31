package moriyashiine.sizeentityattributes.mixin;

import moriyashiine.sizeentityattributes.SizeEntityAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ArmorStandEntity.class, AbstractSkeletonEntity.class, EndermiteEntity.class, PatrolEntity.class, PiglinEntity.class, SilverfishEntity.class, ZombieEntity.class, ZombifiedPiglinEntity.class, AnimalEntity.class, PlayerEntity.class})
public abstract class ModifyHeightOffset extends LivingEntity {
	protected ModifyHeightOffset(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "getHeightOffset", at = @At("RETURN"), cancellable = true)
	private void getHeightOffset(CallbackInfoReturnable<Double> info) {
		EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (heightMultiplier != null) {
			double heightMultiplierValue = getAttributeValue(SizeEntityAttributes.HEIGHT_MULTIPLIER);
			info.setReturnValue(info.getReturnValue() * heightMultiplierValue + (heightMultiplierValue < 0.5 ? 0.175 : 0));
		}
	}
}