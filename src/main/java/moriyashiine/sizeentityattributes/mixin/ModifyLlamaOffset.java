package moriyashiine.sizeentityattributes.mixin;

import moriyashiine.sizeentityattributes.SizeEntityAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LlamaEntity.class)
public abstract class ModifyLlamaOffset extends AbstractDonkeyEntity {
	protected ModifyLlamaOffset(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@ModifyConstant(method = "updatePassengerPosition", constant = @Constant(floatValue = 0.3f))
	private float updatePassengerPositionModifyHorizontalOffset(float value) {
		EntityAttributeInstance heightMultiplier = getAttributeInstance(SizeEntityAttributes.HEIGHT_MULTIPLIER);
		if (heightMultiplier != null) {
			return (float) (value * getAttributeValue(SizeEntityAttributes.HEIGHT_MULTIPLIER));
		}
		return value;
	}
}