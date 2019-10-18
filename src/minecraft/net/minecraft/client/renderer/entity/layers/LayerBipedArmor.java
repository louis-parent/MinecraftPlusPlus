package net.minecraft.client.renderer.entity.layers;

import fr.minecraftpp.inventory.EntityArmorSlot;
import fr.minecraftpp.inventory.EntityEquipmentSlot;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;

public class LayerBipedArmor extends LayerArmorBase<ModelBiped>
{
	public LayerBipedArmor(RenderLivingBase<?> rendererIn)
	{
		super(rendererIn);
	}

	protected void initArmor()
	{
		this.modelLeggings = new ModelBiped(0.5F);
		this.modelArmor = new ModelBiped(1.0F);
	}

	@SuppressWarnings("incomplete-switch")
	protected void setModelSlotVisible(ModelBiped model, EntityEquipmentSlot slot)
	{
		this.setModelVisible(model);

		if (slot instanceof EntityArmorSlot)
		{
			switch ((EntityArmorSlot) slot)
			{
				case HEAD:
					model.bipedHead.showModel = true;
					model.bipedHeadwear.showModel = true;
					break;

				case CHEST:
					model.bipedBody.showModel = true;
					model.bipedRightArm.showModel = true;
					model.bipedLeftArm.showModel = true;
					break;

				case LEGS:
					model.bipedBody.showModel = true;
					model.bipedRightLeg.showModel = true;
					model.bipedLeftLeg.showModel = true;
					break;

				case FEET:
					model.bipedRightLeg.showModel = true;
					model.bipedLeftLeg.showModel = true;
			}
		}
	}

	protected void setModelVisible(ModelBiped model)
	{
		model.setInvisible(false);
	}
}
