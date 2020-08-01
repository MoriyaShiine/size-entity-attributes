package moriyashiine.sizeentityattributes.mixin;

import moriyashiine.sizeentityattributes.SizeEntityAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HorseBaseEntity.class)
public abstract class ModifyHorseBaseOffset extends AnimalEntity {
	protected ModifyHorseBaseOffset(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@ModifyConstant(method = "updatePassengerPosition", constant = @Constant(floatValue = 0.7f))
	private float updatePassengerPositionModifyHorizontalOffset(float value) {
		EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (heightMultiplier != null) {
			return (float) (value * getAttributeValue(SizeEntityAttributes.HEIGHT_MULTIPLIER));
		}
		return value;
	}
	
	@ModifyConstant(method = "updatePassengerPosition", constant = @Constant(floatValue = 0.15f))
	private float updatePassengerPositionModifyVerticalOffset(float value) {
		EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (heightMultiplier != null) {
			return (float) (value * getAttributeValue(SizeEntityAttributes.HEIGHT_MULTIPLIER));
		}
		return value;
	}
}