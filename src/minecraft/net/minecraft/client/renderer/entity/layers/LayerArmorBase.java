package net.minecraft.client.renderer.entity.layers;

import java.util.Map;

import com.google.common.collect.Maps;

import fr.minecraftpp.inventory.EntityArmorSlot;
import fr.minecraftpp.inventory.EntityEquipmentSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class LayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase>
{
	protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	protected T modelLeggings;
	protected T modelArmor;
	private final RenderLivingBase<?> renderer;
	private float alpha = 1.0F;
	private float colorR = 1.0F;
	private float colorG = 1.0F;
	private float colorB = 1.0F;
	private boolean skipRenderGlint;
	private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.<String, ResourceLocation>newHashMap();

	public LayerArmorBase(RenderLivingBase<?> rendererIn)
	{
		this.renderer = rendererIn;
		this.initArmor();
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityArmorSlot.CHEST);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityArmorSlot.LEGS);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityArmorSlot.FEET);
		this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityArmorSlot.HEAD);
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}

	private void renderArmorLayer(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slot)
	{
		ItemStack itemstack = entityLivingBase.getItemStackFromSlot(slot);

		if (itemstack.getItem() instanceof ItemArmor)
		{
			ItemArmor itemarmor = (ItemArmor) itemstack.getItem();

			if (itemarmor.getEquipmentSlot() == slot)
			{
				T t = this.getModelFromSlot(slot);
				t.setModelAttributes(this.renderer.getMainModel());
				t.setLivingAnimations(entityLivingBase, limbSwing, limbSwingAmount, partialTicks);
				this.setModelSlotVisible(t, slot);
				boolean flag = this.isLegSlot(slot);
				this.renderer.bindTexture(this.getArmorResource(itemarmor, flag));

				if (itemarmor.getArmorMaterial() instanceof ArmorMaterial)
				{
					switch ((ArmorMaterial) itemarmor.getArmorMaterial())
					{
						case LEATHER:
							renderColoredArmor(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, itemstack, itemarmor, t, flag);

						case CHAIN:
						case IRON:
						case GOLD:
						case DIAMOND:
							renderSimpleArmor(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, t);

						default:
							if (!this.skipRenderGlint && itemstack.isItemEnchanted())
							{
								renderEnchantedGlint(this.renderer, entityLivingBase, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
							}
					}
				}
				else
				{
					renderSimpleArmor(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, t);
				}
			}
		}
	}

	private void renderColoredArmor(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, ItemStack itemstack, ItemArmor itemarmor, T t, boolean flag)
	{
		int i = itemarmor.getColor(itemstack);
		float f = (i >> 16 & 255) / 255.0F;
		float f1 = (i >> 8 & 255) / 255.0F;
		float f2 = (i & 255) / 255.0F;
		GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
		t.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		this.renderer.bindTexture(this.getArmorResource(itemarmor, flag, "overlay"));
	}

	private void renderSimpleArmor(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, T t)
	{
		GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
		t.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

	public T getModelFromSlot(EntityEquipmentSlot slotIn)
	{
		return this.isLegSlot(slotIn) ? this.modelLeggings : this.modelArmor;
	}

	private boolean isLegSlot(EntityEquipmentSlot slotIn)
	{
		return slotIn == EntityArmorSlot.LEGS;
	}

	public static void renderEnchantedGlint(RenderLivingBase<?> p_188364_0_, EntityLivingBase p_188364_1_, ModelBase model, float p_188364_3_, float p_188364_4_, float p_188364_5_, float p_188364_6_, float p_188364_7_, float p_188364_8_, float p_188364_9_)
	{
		float f = p_188364_1_.ticksExisted + p_188364_5_;
		p_188364_0_.bindTexture(ENCHANTED_ITEM_GLINT_RES);
		Minecraft.getMinecraft().entityRenderer.func_191514_d(true);
		GlStateManager.enableBlend();
		GlStateManager.depthFunc(514);
		GlStateManager.depthMask(false);
		float f1 = 0.5F;
		GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);

		for (int i = 0; i < 2; ++i)
		{
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
			float f2 = 0.76F;
			GlStateManager.color(0.38F, 0.19F, 0.608F, 1.0F);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			float f3 = 0.33333334F;
			GlStateManager.scale(0.33333334F, 0.33333334F, 0.33333334F);
			GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0.0F, f * (0.001F + i * 0.003F) * 20.0F, 0.0F);
			GlStateManager.matrixMode(5888);
			model.render(p_188364_1_, p_188364_3_, p_188364_4_, p_188364_6_, p_188364_7_, p_188364_8_, p_188364_9_);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		}

		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(5888);
		GlStateManager.enableLighting();
		GlStateManager.depthMask(true);
		GlStateManager.depthFunc(515);
		GlStateManager.disableBlend();
		Minecraft.getMinecraft().entityRenderer.func_191514_d(false);
	}

	private ResourceLocation getArmorResource(ItemArmor armor, boolean p_177181_2_)
	{
		return this.getArmorResource(armor, p_177181_2_, (String) null);
	}

	private ResourceLocation getArmorResource(ItemArmor armor, boolean p_177178_2_, String p_177178_3_)
	{
		String s = String.format("textures/models/armor/%s_layer_%d%s.png", armor.getArmorMaterial().getName(), p_177178_2_ ? 2 : 1, p_177178_3_ == null ? "" : String.format("_%s", p_177178_3_));
		ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s);

		if (resourcelocation == null)
		{
			resourcelocation = new ResourceLocation(s);
			ARMOR_TEXTURE_RES_MAP.put(s, resourcelocation);
		}

		return resourcelocation;
	}

	protected abstract void initArmor();

	protected abstract void setModelSlotVisible(T p_188359_1_, EntityEquipmentSlot slotIn);
}
