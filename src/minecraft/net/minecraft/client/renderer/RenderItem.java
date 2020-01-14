package net.minecraft.client.renderer;

import java.util.List;

import javax.annotation.Nullable;

import fr.minecraftpp.manager.renderer.ModRenderItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class RenderItem implements IResourceManagerReloadListener
{
	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	/** False when the renderer is rendering the item's effects into a GUI */
	private boolean notRenderingEffectsInGUI = true;

	/** Defines the zLevel of rendering of item on GUI. */
	public float zLevel;
	private final ItemModelMesher itemModelMesher;
	private final TextureManager textureManager;
	private final ItemColors itemColors;

	public RenderItem(TextureManager p_i46552_1_, ModelManager p_i46552_2_, ItemColors p_i46552_3_)
	{
		this.textureManager = p_i46552_1_;
		this.itemModelMesher = new ItemModelMesher(p_i46552_2_);
		this.registerItems();
		this.itemColors = p_i46552_3_;
	}

	public ItemModelMesher getItemModelMesher()
	{
		return this.itemModelMesher;
	}

	protected void registerItem(Item itm, int subType, String identifier)
	{
		this.itemModelMesher.register(itm, subType, new ModelResourceLocation(identifier, "inventory"));
	}

	protected void registerBlock(Block blk, int subType, String identifier)
	{
		this.registerItem(Item.getItemFromBlock(blk), subType, identifier);
	}

	public void registerBlock(Block blk, String identifier)
	{
		this.registerBlock(blk, 0, identifier);
	}

	public void registerItem(Item itm, String identifier)
	{
		this.registerItem(itm, 0, identifier);
	}

	private void func_191961_a(IBakedModel model, ItemStack stack)
	{
		this.func_191967_a(model, -1, stack);
	}

	private void func_191965_a(IBakedModel model, int p_191965_2_)
	{
		this.func_191967_a(model, p_191965_2_, ItemStack.EMPTY_ITEM_STACK);
	}

	private void func_191967_a(IBakedModel model, int p_191967_2_, ItemStack stack)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

		for (EnumFacing enumfacing : EnumFacing.values())
		{
			this.func_191970_a(bufferbuilder, model.getQuads((IBlockState) null, enumfacing, 0L), p_191967_2_, stack);
		}

		this.func_191970_a(bufferbuilder, model.getQuads((IBlockState) null, (EnumFacing) null, 0L), p_191967_2_, stack);
		tessellator.draw();
	}

	public void renderItem(ItemStack stack, IBakedModel model)
	{
		if (!stack.isNotValid())
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(-0.5F, -0.5F, -0.5F);

			if (model.isBuiltInRenderer())
			{
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableRescaleNormal();
				TileEntityItemStackRenderer.instance.renderByItem(stack);
			}
			else
			{
				this.func_191961_a(model, stack);

				if (stack.hasEffect())
				{
					this.func_191966_a(model);
				}
			}

			GlStateManager.popMatrix();
		}
	}

	private void func_191966_a(IBakedModel p_191966_1_)
	{
		GlStateManager.depthMask(false);
		GlStateManager.depthFunc(514);
		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
		this.textureManager.bindTexture(RES_ITEM_GLINT);
		GlStateManager.matrixMode(5890);
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f = Minecraft.getSystemTime() % 3000L / 3000.0F / 8.0F;
		GlStateManager.translate(f, 0.0F, 0.0F);
		GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
		this.func_191965_a(p_191966_1_, -8372020);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f1 = Minecraft.getSystemTime() % 4873L / 4873.0F / 8.0F;
		GlStateManager.translate(-f1, 0.0F, 0.0F);
		GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
		this.func_191965_a(p_191966_1_, -8372020);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableLighting();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}

	private void putQuadNormal(BufferBuilder renderer, BakedQuad quad)
	{
		Vec3i vec3i = quad.getFace().getDirectionVec();
		renderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
	}

	private void func_191969_a(BufferBuilder p_191969_1_, BakedQuad p_191969_2_, int p_191969_3_)
	{
		p_191969_1_.addVertexData(p_191969_2_.getVertexData());
		p_191969_1_.putColor4(p_191969_3_);
		this.putQuadNormal(p_191969_1_, p_191969_2_);
	}

	private void func_191970_a(BufferBuilder p_191970_1_, List<BakedQuad> p_191970_2_, int p_191970_3_, ItemStack p_191970_4_)
	{
		boolean flag = p_191970_3_ == -1 && !p_191970_4_.isNotValid();
		int i = 0;

		for (int j = p_191970_2_.size(); i < j; ++i)
		{
			BakedQuad bakedquad = p_191970_2_.get(i);
			int k = p_191970_3_;

			if (flag && bakedquad.hasTintIndex())
			{
				k = this.itemColors.getColorFromItemstack(p_191970_4_, bakedquad.getTintIndex());

				if (EntityRenderer.anaglyphEnable)
				{
					k = TextureUtil.anaglyphColor(k);
				}

				k = k | -16777216;
			}

			this.func_191969_a(p_191970_1_, bakedquad, k);
		}
	}

	public boolean shouldRenderItemIn3D(ItemStack stack)
	{
		IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
		return ibakedmodel == null ? false : ibakedmodel.isGui3d();
	}

	public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType cameraTransformType)
	{
		if (!stack.isNotValid())
		{
			IBakedModel ibakedmodel = this.getItemModelWithOverrides(stack, (World) null, (EntityLivingBase) null);
			this.renderItemModel(stack, ibakedmodel, cameraTransformType, false);
		}
	}

	public IBakedModel getItemModelWithOverrides(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entitylivingbaseIn)
	{
		IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
		Item item = stack.getItem();

		if (item != null && item.hasCustomProperties())
		{
			ResourceLocation resourcelocation = ibakedmodel.getOverrides().applyOverride(stack, worldIn, entitylivingbaseIn);
			return resourcelocation == null ? ibakedmodel : this.itemModelMesher.getModelManager().getModel(new ModelResourceLocation(resourcelocation, "inventory"));
		}
		else
		{
			return ibakedmodel;
		}
	}

	public void renderItem(ItemStack stack, EntityLivingBase entitylivingbaseIn, ItemCameraTransforms.TransformType transform, boolean leftHanded)
	{
		if (!stack.isNotValid() && entitylivingbaseIn != null)
		{
			IBakedModel ibakedmodel = this.getItemModelWithOverrides(stack, entitylivingbaseIn.world, entitylivingbaseIn);
			this.renderItemModel(stack, ibakedmodel, transform, leftHanded);
		}
	}

	protected void renderItemModel(ItemStack stack, IBakedModel bakedmodel, ItemCameraTransforms.TransformType transform, boolean leftHanded)
	{
		if (!stack.isNotValid())
		{
			this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			GlStateManager.alphaFunc(516, 0.1F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.pushMatrix();
			ItemCameraTransforms itemcameratransforms = bakedmodel.getItemCameraTransforms();
			ItemCameraTransforms.applyTransformSide(itemcameratransforms.getTransform(transform), leftHanded);

			if (this.isThereOneNegativeScale(itemcameratransforms.getTransform(transform)))
			{
				GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
			}

			this.renderItem(stack, bakedmodel);
			GlStateManager.cullFace(GlStateManager.CullFace.BACK);
			GlStateManager.popMatrix();
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
			this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
		}
	}

	/**
	 * Return true if only one scale is negative
	 */
	private boolean isThereOneNegativeScale(ItemTransformVec3f itemTranformVec)
	{
		return itemTranformVec.scale.x < 0.0F ^ itemTranformVec.scale.y < 0.0F ^ itemTranformVec.scale.z < 0.0F;
	}

	public void renderItemIntoGUI(ItemStack stack, int x, int y)
	{
		this.renderItemWithModel(stack, x, y, this.getItemModelWithOverrides(stack, (World) null, (EntityLivingBase) null));
	}

	protected void renderItemWithModel(ItemStack item, int x, int y, IBakedModel model)
	{
		GlStateManager.pushMatrix();
		this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		this.setupGuiTransform(x, y, model.isGui3d());
		model.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
		this.renderItem(item, model);
		GlStateManager.disableAlpha();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
		this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		this.textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	}

	private void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d)
	{
		GlStateManager.translate(xPosition, yPosition, 100.0F + this.zLevel);
		GlStateManager.translate(8.0F, 8.0F, 0.0F);
		GlStateManager.scale(1.0F, -1.0F, 1.0F);
		GlStateManager.scale(16.0F, 16.0F, 16.0F);

		if (isGui3d)
		{
			GlStateManager.enableLighting();
		}
		else
		{
			GlStateManager.disableLighting();
		}
	}

	public void renderItemAndEffectIntoGUI(ItemStack stack, int xPosition, int yPosition)
	{
		this.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().player, stack, xPosition, yPosition);
	}

	public void renderItemAndEffectIntoGUI(@Nullable EntityLivingBase entity, final ItemStack stack, int xPosition, int yPosition)
	{
		if (!stack.isNotValid())
		{
			this.zLevel += 50.0F;

			try
			{
				this.renderItemWithModel(stack, xPosition, yPosition, this.getItemModelWithOverrides(stack, (World) null, entity));
			}
			catch (Throwable throwable)
			{
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
				crashreportcategory.setDetail("Item Type", new ICrashReportDetail<String>()
				{
					@Override
					public String call() throws Exception
					{
						return String.valueOf(stack.getItem());
					}
				});
				crashreportcategory.setDetail("Item Aux", new ICrashReportDetail<String>()
				{
					@Override
					public String call() throws Exception
					{
						return String.valueOf(stack.getMetadata());
					}
				});
				crashreportcategory.setDetail("Item NBT", new ICrashReportDetail<String>()
				{
					@Override
					public String call() throws Exception
					{
						return String.valueOf(stack.getTagCompound());
					}
				});
				crashreportcategory.setDetail("Item Foil", new ICrashReportDetail<String>()
				{
					@Override
					public String call() throws Exception
					{
						return String.valueOf(stack.hasEffect());
					}
				});
				throw new ReportedException(crashreport);
			}

			this.zLevel -= 50.0F;
		}
	}

	public void renderItemOverlays(FontRenderer fr, ItemStack stack, int xPosition, int yPosition)
	{
		this.renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, (String) null);
	}

	/**
	 * Renders the stack size and/or damage bar for the given ItemStack.
	 */
	public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text)
	{
		if (!stack.isNotValid())
		{
			if (stack.getStackSize() != 1 || text != null)
			{
				String s = text == null ? String.valueOf(stack.getStackSize()) : text;
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();
				GlStateManager.disableBlend();
				fr.drawStringWithShadow(s, xPosition + 19 - 2 - fr.getStringWidth(s), yPosition + 6 + 3, 16777215);
				GlStateManager.enableLighting();
				GlStateManager.enableDepth();
			}

			if (stack.isItemDamaged())
			{
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();
				GlStateManager.disableTexture2D();
				GlStateManager.disableAlpha();
				GlStateManager.disableBlend();
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				float f = stack.getItemDamage();
				float f1 = stack.getMaxDamage();
				float f2 = Math.max(0.0F, (f1 - f) / f1);
				int i = Math.round(13.0F - f * 13.0F / f1);
				int j = MathHelper.hsvToRGB(f2 / 3.0F, 1.0F, 1.0F);
				this.draw(bufferbuilder, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
				this.draw(bufferbuilder, xPosition + 2, yPosition + 13, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
				GlStateManager.enableBlend();
				GlStateManager.enableAlpha();
				GlStateManager.enableTexture2D();
				GlStateManager.enableLighting();
				GlStateManager.enableDepth();
			}

			EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
			float f3 = entityplayersp == null ? 0.0F : entityplayersp.getCooldownTracker().getCooldown(stack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());

			if (f3 > 0.0F)
			{
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();
				GlStateManager.disableTexture2D();
				Tessellator tessellator1 = Tessellator.getInstance();
				BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
				this.draw(bufferbuilder1, xPosition, yPosition + MathHelper.floor(16.0F * (1.0F - f3)), 16, MathHelper.ceil(16.0F * f3), 255, 255, 255, 127);
				GlStateManager.enableTexture2D();
				GlStateManager.enableLighting();
				GlStateManager.enableDepth();
			}
		}
	}

	/**
	 * Draw with the WorldRenderer
	 */
	private void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
	{
		renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		renderer.pos(x + 0, y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(x + 0, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(x + width, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(x + width, y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
		Tessellator.getInstance().draw();
	}

	private void registerItems()
	{
		this.registerBlock(Blocks.getBlock(Blocks.ANVIL), "anvil_intact");
		this.registerBlock(Blocks.getBlock(Blocks.ANVIL), 1, "anvil_slightly_damaged");
		this.registerBlock(Blocks.getBlock(Blocks.ANVIL), 2, "anvil_very_damaged");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.BLACK.getMetadata(), "black_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.BLUE.getMetadata(), "blue_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.BROWN.getMetadata(), "brown_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.CYAN.getMetadata(), "cyan_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.GRAY.getMetadata(), "gray_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.GREEN.getMetadata(), "green_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.LIME.getMetadata(), "lime_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.MAGENTA.getMetadata(), "magenta_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.ORANGE.getMetadata(), "orange_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.PINK.getMetadata(), "pink_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.PURPLE.getMetadata(), "purple_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.RED.getMetadata(), "red_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.SILVER.getMetadata(), "silver_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.WHITE.getMetadata(), "white_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.CARPET), EnumDyeColor.YELLOW.getMetadata(), "yellow_carpet");
		this.registerBlock(Blocks.getBlock(Blocks.COBBLESTONE_WALL), BlockWall.EnumType.MOSSY.getMetadata(), "mossy_cobblestone_wall");
		this.registerBlock(Blocks.getBlock(Blocks.COBBLESTONE_WALL), BlockWall.EnumType.NORMAL.getMetadata(), "cobblestone_wall");
		this.registerBlock(Blocks.getBlock(Blocks.DIRT), BlockDirt.DirtType.COARSE_DIRT.getMetadata(), "coarse_dirt");
		this.registerBlock(Blocks.getBlock(Blocks.DIRT), BlockDirt.DirtType.DIRT.getMetadata(), "dirt");
		this.registerBlock(Blocks.getBlock(Blocks.DIRT), BlockDirt.DirtType.PODZOL.getMetadata(), "podzol");
		this.registerBlock(Blocks.getBlock(Blocks.DOUBLE_PLANT), BlockDoublePlant.EnumPlantType.FERN.getMeta(), "double_fern");
		this.registerBlock(Blocks.getBlock(Blocks.DOUBLE_PLANT), BlockDoublePlant.EnumPlantType.GRASS.getMeta(), "double_grass");
		this.registerBlock(Blocks.getBlock(Blocks.DOUBLE_PLANT), BlockDoublePlant.EnumPlantType.PAEONIA.getMeta(), "paeonia");
		this.registerBlock(Blocks.getBlock(Blocks.DOUBLE_PLANT), BlockDoublePlant.EnumPlantType.ROSE.getMeta(), "double_rose");
		this.registerBlock(Blocks.getBlock(Blocks.DOUBLE_PLANT), BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta(), "sunflower");
		this.registerBlock(Blocks.getBlock(Blocks.DOUBLE_PLANT), BlockDoublePlant.EnumPlantType.SYRINGA.getMeta(), "syringa");
		this.registerBlock(Blocks.getBlock(Blocks.LEAVES), BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_leaves");
		this.registerBlock(Blocks.getBlock(Blocks.LEAVES), BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_leaves");
		this.registerBlock(Blocks.getBlock(Blocks.LEAVES), BlockPlanks.EnumType.OAK.getMetadata(), "oak_leaves");
		this.registerBlock(Blocks.getBlock(Blocks.LEAVES), BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_leaves");
		this.registerBlock(Blocks.getBlock(Blocks.LEAVES2), BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_leaves");
		this.registerBlock(Blocks.getBlock(Blocks.LEAVES2), BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_leaves");
		this.registerBlock(Blocks.getBlock(Blocks.LOG), BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_log");
		this.registerBlock(Blocks.getBlock(Blocks.LOG), BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_log");
		this.registerBlock(Blocks.getBlock(Blocks.LOG), BlockPlanks.EnumType.OAK.getMetadata(), "oak_log");
		this.registerBlock(Blocks.getBlock(Blocks.LOG), BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_log");
		this.registerBlock(Blocks.getBlock(Blocks.LOG2), BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_log");
		this.registerBlock(Blocks.getBlock(Blocks.LOG2), BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_log");
		this.registerBlock(Blocks.getBlock(Blocks.MONSTER_EGG), BlockSilverfish.EnumType.CHISELED_STONEBRICK.getMetadata(), "chiseled_brick_monster_egg");
		this.registerBlock(Blocks.getBlock(Blocks.MONSTER_EGG), BlockSilverfish.EnumType.COBBLESTONE.getMetadata(), "cobblestone_monster_egg");
		this.registerBlock(Blocks.getBlock(Blocks.MONSTER_EGG), BlockSilverfish.EnumType.CRACKED_STONEBRICK.getMetadata(), "cracked_brick_monster_egg");
		this.registerBlock(Blocks.getBlock(Blocks.MONSTER_EGG), BlockSilverfish.EnumType.MOSSY_STONEBRICK.getMetadata(), "mossy_brick_monster_egg");
		this.registerBlock(Blocks.getBlock(Blocks.MONSTER_EGG), BlockSilverfish.EnumType.STONE.getMetadata(), "stone_monster_egg");
		this.registerBlock(Blocks.getBlock(Blocks.MONSTER_EGG), BlockSilverfish.EnumType.STONEBRICK.getMetadata(), "stone_brick_monster_egg");
		this.registerBlock(Blocks.getBlock(Blocks.PLANKS), BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_planks");
		this.registerBlock(Blocks.getBlock(Blocks.PLANKS), BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_planks");
		this.registerBlock(Blocks.getBlock(Blocks.PLANKS), BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_planks");
		this.registerBlock(Blocks.getBlock(Blocks.PLANKS), BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_planks");
		this.registerBlock(Blocks.getBlock(Blocks.PLANKS), BlockPlanks.EnumType.OAK.getMetadata(), "oak_planks");
		this.registerBlock(Blocks.getBlock(Blocks.PLANKS), BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_planks");
		this.registerBlock(Blocks.getBlock(Blocks.PRISMARINE), BlockPrismarine.EnumType.BRICKS.getMetadata(), "prismarine_bricks");
		this.registerBlock(Blocks.getBlock(Blocks.PRISMARINE), BlockPrismarine.EnumType.DARK.getMetadata(), "dark_prismarine");
		this.registerBlock(Blocks.getBlock(Blocks.PRISMARINE), BlockPrismarine.EnumType.ROUGH.getMetadata(), "prismarine");
		this.registerBlock(Blocks.getBlock(Blocks.QUARTZ_BLOCK), BlockQuartz.EnumType.CHISELED.getMetadata(), "chiseled_quartz_block");
		this.registerBlock(Blocks.getBlock(Blocks.QUARTZ_BLOCK), BlockQuartz.EnumType.DEFAULT.getMetadata(), "quartz_block");
		this.registerBlock(Blocks.getBlock(Blocks.QUARTZ_BLOCK), BlockQuartz.EnumType.LINES_Y.getMetadata(), "quartz_column");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.ALLIUM.getMeta(), "allium");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta(), "blue_orchid");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.HOUSTONIA.getMeta(), "houstonia");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta(), "orange_tulip");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta(), "oxeye_daisy");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.PINK_TULIP.getMeta(), "pink_tulip");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.POPPY.getMeta(), "poppy");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.RED_TULIP.getMeta(), "red_tulip");
		this.registerBlock(Blocks.getBlock(Blocks.RED_FLOWER), BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta(), "white_tulip");
		this.registerBlock(Blocks.getBlock(Blocks.SAND), BlockSand.EnumType.RED_SAND.getMetadata(), "red_sand");
		this.registerBlock(Blocks.getBlock(Blocks.SAND), BlockSand.EnumType.SAND.getMetadata(), "sand");
		this.registerBlock(Blocks.getBlock(Blocks.SANDSTONE), BlockSandStone.EnumType.CHISELED.getMetadata(), "chiseled_sandstone");
		this.registerBlock(Blocks.getBlock(Blocks.SANDSTONE), BlockSandStone.EnumType.DEFAULT.getMetadata(), "sandstone");
		this.registerBlock(Blocks.getBlock(Blocks.SANDSTONE), BlockSandStone.EnumType.SMOOTH.getMetadata(), "smooth_sandstone");
		this.registerBlock(Blocks.getBlock(Blocks.RED_SANDSTONE), BlockRedSandstone.EnumType.CHISELED.getMetadata(), "chiseled_red_sandstone");
		this.registerBlock(Blocks.getBlock(Blocks.RED_SANDSTONE), BlockRedSandstone.EnumType.DEFAULT.getMetadata(), "red_sandstone");
		this.registerBlock(Blocks.getBlock(Blocks.RED_SANDSTONE), BlockRedSandstone.EnumType.SMOOTH.getMetadata(), "smooth_red_sandstone");
		this.registerBlock(Blocks.getBlock(Blocks.SAPLING), BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_sapling");
		this.registerBlock(Blocks.getBlock(Blocks.SAPLING), BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_sapling");
		this.registerBlock(Blocks.getBlock(Blocks.SAPLING), BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_sapling");
		this.registerBlock(Blocks.getBlock(Blocks.SAPLING), BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_sapling");
		this.registerBlock(Blocks.getBlock(Blocks.SAPLING), BlockPlanks.EnumType.OAK.getMetadata(), "oak_sapling");
		this.registerBlock(Blocks.getBlock(Blocks.SAPLING), BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_sapling");
		this.registerBlock(Blocks.getBlock(Blocks.SPONGE), 0, "sponge");
		this.registerBlock(Blocks.getBlock(Blocks.SPONGE), 1, "sponge_wet");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.BLACK.getMetadata(), "black_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.GREEN.getMetadata(), "green_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.LIME.getMetadata(), "lime_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.PINK.getMetadata(), "pink_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.RED.getMetadata(), "red_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.WHITE.getMetadata(), "white_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS), EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.BLACK.getMetadata(), "black_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.GREEN.getMetadata(), "green_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.LIME.getMetadata(), "lime_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.PINK.getMetadata(), "pink_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.RED.getMetadata(), "red_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.WHITE.getMetadata(), "white_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.BLACK.getMetadata(), "black_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.BLUE.getMetadata(), "blue_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.BROWN.getMetadata(), "brown_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.CYAN.getMetadata(), "cyan_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.GRAY.getMetadata(), "gray_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.GREEN.getMetadata(), "green_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.LIME.getMetadata(), "lime_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.ORANGE.getMetadata(), "orange_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.PINK.getMetadata(), "pink_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.PURPLE.getMetadata(), "purple_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.RED.getMetadata(), "red_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.SILVER.getMetadata(), "silver_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.WHITE.getMetadata(), "white_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.STONE), BlockStone.EnumType.ANDESITE.getMetadata(), "andesite");
		this.registerBlock(Blocks.getBlock(Blocks.STONE), BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata(), "andesite_smooth");
		this.registerBlock(Blocks.getBlock(Blocks.STONE), BlockStone.EnumType.DIORITE.getMetadata(), "diorite");
		this.registerBlock(Blocks.getBlock(Blocks.STONE), BlockStone.EnumType.DIORITE_SMOOTH.getMetadata(), "diorite_smooth");
		this.registerBlock(Blocks.getBlock(Blocks.STONE), BlockStone.EnumType.GRANITE.getMetadata(), "granite");
		this.registerBlock(Blocks.getBlock(Blocks.STONE), BlockStone.EnumType.GRANITE_SMOOTH.getMetadata(), "granite_smooth");
		this.registerBlock(Blocks.getBlock(Blocks.STONE), BlockStone.EnumType.STONE.getMetadata(), "stone");
		this.registerBlock(Blocks.getBlock(Blocks.STONEBRICK), BlockStoneBrick.EnumType.CRACKED.getMetadata(), "cracked_stonebrick");
		this.registerBlock(Blocks.getBlock(Blocks.STONEBRICK), BlockStoneBrick.EnumType.DEFAULT.getMetadata(), "stonebrick");
		this.registerBlock(Blocks.getBlock(Blocks.STONEBRICK), BlockStoneBrick.EnumType.CHISELED.getMetadata(), "chiseled_stonebrick");
		this.registerBlock(Blocks.getBlock(Blocks.STONEBRICK), BlockStoneBrick.EnumType.MOSSY.getMetadata(), "mossy_stonebrick");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.BRICK.getMetadata(), "brick_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.COBBLESTONE.getMetadata(), "cobblestone_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.WOOD.getMetadata(), "old_wood_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.NETHERBRICK.getMetadata(), "nether_brick_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.QUARTZ.getMetadata(), "quartz_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.SAND.getMetadata(), "sandstone_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata(), "stone_brick_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB), BlockStoneSlab.EnumType.STONE.getMetadata(), "stone_slab");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_SLAB2), BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata(), "red_sandstone_slab");
		this.registerBlock(Blocks.getBlock(Blocks.TALLGRASS), BlockTallGrass.EnumType.DEAD_BUSH.getMeta(), "dead_bush");
		this.registerBlock(Blocks.getBlock(Blocks.TALLGRASS), BlockTallGrass.EnumType.FERN.getMeta(), "fern");
		this.registerBlock(Blocks.getBlock(Blocks.TALLGRASS), BlockTallGrass.EnumType.GRASS.getMeta(), "tall_grass");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_SLAB), BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_slab");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_SLAB), BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_slab");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_SLAB), BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_slab");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_SLAB), BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_slab");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_SLAB), BlockPlanks.EnumType.OAK.getMetadata(), "oak_slab");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_SLAB), BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_slab");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.BLACK.getMetadata(), "black_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.BLUE.getMetadata(), "blue_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.BROWN.getMetadata(), "brown_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.CYAN.getMetadata(), "cyan_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.GRAY.getMetadata(), "gray_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.GREEN.getMetadata(), "green_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.LIME.getMetadata(), "lime_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.MAGENTA.getMetadata(), "magenta_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.ORANGE.getMetadata(), "orange_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.PINK.getMetadata(), "pink_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.PURPLE.getMetadata(), "purple_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.RED.getMetadata(), "red_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.SILVER.getMetadata(), "silver_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.WHITE.getMetadata(), "white_wool");
		this.registerBlock(Blocks.getBlock(Blocks.WOOL), EnumDyeColor.YELLOW.getMetadata(), "yellow_wool");
		this.registerBlock(Blocks.getBlock(Blocks.FARMLAND), "farmland");
		this.registerBlock(Blocks.getBlock(Blocks.ACACIA_STAIRS), "acacia_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.ACTIVATOR_RAIL), "activator_rail");
		this.registerBlock(Blocks.getBlock(Blocks.BEACON), "beacon");
		this.registerBlock(Blocks.getBlock(Blocks.BEDROCK), "bedrock");
		this.registerBlock(Blocks.getBlock(Blocks.BIRCH_STAIRS), "birch_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.BOOKSHELF), "bookshelf");
		this.registerBlock(Blocks.getBlock(Blocks.BRICK_BLOCK), "brick_block");
		this.registerBlock(Blocks.getBlock(Blocks.BRICK_BLOCK), "brick_block");
		this.registerBlock(Blocks.getBlock(Blocks.BRICK_STAIRS), "brick_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.BROWN_MUSHROOM), "brown_mushroom");
		this.registerBlock(Blocks.getBlock(Blocks.CACTUS), "cactus");
		this.registerBlock(Blocks.getBlock(Blocks.CLAY), "clay");
		this.registerBlock(Blocks.getBlock(Blocks.COAL_BLOCK), "coal_block");
		this.registerBlock(Blocks.getBlock(Blocks.COAL_ORE), "coal_ore");
		this.registerBlock(Blocks.getBlock(Blocks.COBBLESTONE), "cobblestone");
		this.registerBlock(Blocks.getBlock(Blocks.CRAFTING_TABLE), "crafting_table");
		this.registerBlock(Blocks.getBlock(Blocks.DARK_OAK_STAIRS), "dark_oak_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.DAYLIGHT_DETECTOR), "daylight_detector");
		this.registerBlock(Blocks.getBlock(Blocks.DEADBUSH), "dead_bush");
		this.registerBlock(Blocks.getBlock(Blocks.DETECTOR_RAIL), "detector_rail");
		this.registerBlock(Blocks.getBlock(Blocks.DIAMOND_BLOCK), "diamond_block");
		this.registerBlock(Blocks.getBlock(Blocks.DIAMOND_ORE), "diamond_ore");
		this.registerBlock(Blocks.getBlock(Blocks.DISPENSER), "dispenser");
		this.registerBlock(Blocks.getBlock(Blocks.DROPPER), "dropper");
		this.registerBlock(Blocks.getBlock(Blocks.EMERALD_BLOCK), "emerald_block");
		this.registerBlock(Blocks.getBlock(Blocks.EMERALD_ORE), "emerald_ore");
		this.registerBlock(Blocks.getBlock(Blocks.ENCHANTING_TABLE), "enchanting_table");
		this.registerBlock(Blocks.getBlock(Blocks.END_PORTAL_FRAME), "end_portal_frame");
		this.registerBlock(Blocks.getBlock(Blocks.END_STONE), "end_stone");
		this.registerBlock(Blocks.getBlock(Blocks.OAK_FENCE), "oak_fence");
		this.registerBlock(Blocks.getBlock(Blocks.SPRUCE_FENCE), "spruce_fence");
		this.registerBlock(Blocks.getBlock(Blocks.BIRCH_FENCE), "birch_fence");
		this.registerBlock(Blocks.getBlock(Blocks.JUNGLE_FENCE), "jungle_fence");
		this.registerBlock(Blocks.getBlock(Blocks.DARK_OAK_FENCE), "dark_oak_fence");
		this.registerBlock(Blocks.getBlock(Blocks.ACACIA_FENCE), "acacia_fence");
		this.registerBlock(Blocks.getBlock(Blocks.OAK_FENCE_GATE), "oak_fence_gate");
		this.registerBlock(Blocks.getBlock(Blocks.SPRUCE_FENCE_GATE), "spruce_fence_gate");
		this.registerBlock(Blocks.getBlock(Blocks.BIRCH_FENCE_GATE), "birch_fence_gate");
		this.registerBlock(Blocks.getBlock(Blocks.JUNGLE_FENCE_GATE), "jungle_fence_gate");
		this.registerBlock(Blocks.getBlock(Blocks.DARK_OAK_FENCE_GATE), "dark_oak_fence_gate");
		this.registerBlock(Blocks.getBlock(Blocks.ACACIA_FENCE_GATE), "acacia_fence_gate");
		this.registerBlock(Blocks.getBlock(Blocks.FURNACE), "furnace");
		this.registerBlock(Blocks.getBlock(Blocks.GLASS), "glass");
		this.registerBlock(Blocks.getBlock(Blocks.GLASS_PANE), "glass_pane");
		this.registerBlock(Blocks.getBlock(Blocks.GLOWSTONE), "glowstone");
		this.registerBlock(Blocks.getBlock(Blocks.GOLDEN_RAIL), "golden_rail");
		this.registerBlock(Blocks.getBlock(Blocks.GOLD_BLOCK), "gold_block");
		this.registerBlock(Blocks.getBlock(Blocks.GOLD_ORE), "gold_ore");
		this.registerBlock(Blocks.getBlock(Blocks.GRASS), "grass");
		this.registerBlock(Blocks.getBlock(Blocks.GRASS_PATH), "grass_path");
		this.registerBlock(Blocks.getBlock(Blocks.GRAVEL), "gravel");
		this.registerBlock(Blocks.getBlock(Blocks.HARDENED_CLAY), "hardened_clay");
		this.registerBlock(Blocks.getBlock(Blocks.HAY_BLOCK), "hay_block");
		this.registerBlock(Blocks.getBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), "heavy_weighted_pressure_plate");
		this.registerBlock(Blocks.getBlock(Blocks.HOPPER), "hopper");
		this.registerBlock(Blocks.getBlock(Blocks.ICE), "ice");
		this.registerBlock(Blocks.getBlock(Blocks.IRON_BARS), "iron_bars");
		this.registerBlock(Blocks.getBlock(Blocks.IRON_BLOCK), "iron_block");
		this.registerBlock(Blocks.getBlock(Blocks.IRON_ORE), "iron_ore");
		this.registerBlock(Blocks.getBlock(Blocks.IRON_TRAPDOOR), "iron_trapdoor");
		this.registerBlock(Blocks.getBlock(Blocks.JUKEBOX), "jukebox");
		this.registerBlock(Blocks.getBlock(Blocks.JUNGLE_STAIRS), "jungle_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.LADDER), "ladder");
		this.registerBlock(Blocks.getBlock(Blocks.LAPIS_BLOCK), "lapis_block");
		this.registerBlock(Blocks.getBlock(Blocks.LAPIS_ORE), "lapis_ore");
		this.registerBlock(Blocks.getBlock(Blocks.LEVER), "lever");
		this.registerBlock(Blocks.getBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), "light_weighted_pressure_plate");
		this.registerBlock(Blocks.getBlock(Blocks.LIT_PUMPKIN), "lit_pumpkin");
		this.registerBlock(Blocks.getBlock(Blocks.MELON_BLOCK), "melon_block");
		this.registerBlock(Blocks.getBlock(Blocks.MOSSY_COBBLESTONE), "mossy_cobblestone");
		this.registerBlock(Blocks.getBlock(Blocks.MYCELIUM), "mycelium");
		this.registerBlock(Blocks.getBlock(Blocks.NETHERRACK), "netherrack");
		this.registerBlock(Blocks.getBlock(Blocks.NETHER_BRICK), "nether_brick");
		this.registerBlock(Blocks.getBlock(Blocks.NETHER_BRICK_FENCE), "nether_brick_fence");
		this.registerBlock(Blocks.getBlock(Blocks.NETHER_BRICK_STAIRS), "nether_brick_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.NOTEBLOCK), "noteblock");
		this.registerBlock(Blocks.getBlock(Blocks.OAK_STAIRS), "oak_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.OBSIDIAN), "obsidian");
		this.registerBlock(Blocks.getBlock(Blocks.PACKED_ICE), "packed_ice");
		this.registerBlock(Blocks.getBlock(Blocks.PISTON), "piston");
		this.registerBlock(Blocks.getBlock(Blocks.PUMPKIN), "pumpkin");
		this.registerBlock(Blocks.getBlock(Blocks.QUARTZ_ORE), "quartz_ore");
		this.registerBlock(Blocks.getBlock(Blocks.QUARTZ_STAIRS), "quartz_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.RAIL), "rail");
		this.registerBlock(Blocks.getBlock(Blocks.REDSTONE_BLOCK), "redstone_block");
		this.registerBlock(Blocks.getBlock(Blocks.REDSTONE_LAMP), "redstone_lamp");
		this.registerBlock(Blocks.getBlock(Blocks.REDSTONE_ORE), "redstone_ore");
		this.registerBlock(Blocks.getBlock(Blocks.REDSTONE_TORCH), "redstone_torch");
		this.registerBlock(Blocks.getBlock(Blocks.RED_MUSHROOM), "red_mushroom");
		this.registerBlock(Blocks.getBlock(Blocks.SANDSTONE_STAIRS), "sandstone_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.RED_SANDSTONE_STAIRS), "red_sandstone_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.SEA_LANTERN), "sea_lantern");
		this.registerBlock(Blocks.getBlock(Blocks.SLIME_BLOCK), "slime");
		this.registerBlock(Blocks.getBlock(Blocks.SNOW), "snow");
		this.registerBlock(Blocks.getBlock(Blocks.SNOW_LAYER), "snow_layer");
		this.registerBlock(Blocks.getBlock(Blocks.SOUL_SAND), "soul_sand");
		this.registerBlock(Blocks.getBlock(Blocks.SPRUCE_STAIRS), "spruce_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.STICKY_PISTON), "sticky_piston");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_BRICK_STAIRS), "stone_brick_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_BUTTON), "stone_button");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_PRESSURE_PLATE), "stone_pressure_plate");
		this.registerBlock(Blocks.getBlock(Blocks.STONE_STAIRS), "stone_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.TNT), "tnt");
		this.registerBlock(Blocks.getBlock(Blocks.TORCH), "torch");
		this.registerBlock(Blocks.getBlock(Blocks.TRAPDOOR), "trapdoor");
		this.registerBlock(Blocks.getBlock(Blocks.TRIPWIRE_HOOK), "tripwire_hook");
		this.registerBlock(Blocks.getBlock(Blocks.VINE), "vine");
		this.registerBlock(Blocks.getBlock(Blocks.WATERLILY), "waterlily");
		this.registerBlock(Blocks.getBlock(Blocks.WEB), "web");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_BUTTON), "wooden_button");
		this.registerBlock(Blocks.getBlock(Blocks.WOODEN_PRESSURE_PLATE), "wooden_pressure_plate");
		this.registerBlock(Blocks.getBlock(Blocks.YELLOW_FLOWER), BlockFlower.EnumFlowerType.DANDELION.getMeta(), "dandelion");
		this.registerBlock(Blocks.getBlock(Blocks.END_ROD), "end_rod");
		this.registerBlock(Blocks.getBlock(Blocks.CHORUS_PLANT), "chorus_plant");
		this.registerBlock(Blocks.getBlock(Blocks.CHORUS_FLOWER), "chorus_flower");
		this.registerBlock(Blocks.getBlock(Blocks.PURPUR_BLOCK), "purpur_block");
		this.registerBlock(Blocks.getBlock(Blocks.PURPUR_PILLAR), "purpur_pillar");
		this.registerBlock(Blocks.getBlock(Blocks.PURPUR_STAIRS), "purpur_stairs");
		this.registerBlock(Blocks.getBlock(Blocks.PURPUR_SLAB), "purpur_slab");
		this.registerBlock(Blocks.getBlock(Blocks.PURPUR_DOUBLE_SLAB), "purpur_double_slab");
		this.registerBlock(Blocks.getBlock(Blocks.END_BRICKS), "end_bricks");
		this.registerBlock(Blocks.getBlock(Blocks.MAGMA), "magma");
		this.registerBlock(Blocks.getBlock(Blocks.NETHER_WART_BLOCK), "nether_wart_block");
		this.registerBlock(Blocks.getBlock(Blocks.RED_NETHER_BRICK), "red_nether_brick");
		this.registerBlock(Blocks.getBlock(Blocks.BONE_BLOCK), "bone_block");
		this.registerBlock(Blocks.getBlock(Blocks.STRUCTURE_VOID), "structure_void");
		this.registerBlock(Blocks.getBlock(Blocks.field_190976_dk), "observer");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_WHITE), "white_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_ORANGE), "orange_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_MAGENTA), "magenta_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_LIGHT_BLUE), "light_blue_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_YELLOW), "yellow_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_LIME), "lime_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_PINK), "pink_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_GRAY), "gray_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_SILVER), "silver_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_CYAN), "cyan_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX), "purple_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_BLUE), "blue_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_BROWN), "brown_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_GREEN), "green_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_RED), "red_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.SHULKER_BOX_BLACK), "black_shulker_box");
		this.registerBlock(Blocks.getBlock(Blocks.field_192427_dB), "white_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192428_dC), "orange_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192429_dD), "magenta_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192430_dE), "light_blue_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192431_dF), "yellow_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192432_dG), "lime_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192433_dH), "pink_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192434_dI), "gray_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192435_dJ), "silver_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192436_dK), "cyan_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192437_dL), "purple_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192438_dM), "blue_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192439_dN), "brown_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192440_dO), "green_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192441_dP), "red_glazed_terracotta");
		this.registerBlock(Blocks.getBlock(Blocks.field_192442_dQ), "black_glazed_terracotta");

		/**
		 * MOD BLOCK RENDER
		 */
		ModRenderItem.registerBlockItemRender(this);

		for (EnumDyeColor enumdyecolor : EnumDyeColor.values())
		{
			this.registerBlock(Blocks.getBlock(Blocks.CONCRETE), enumdyecolor.getMetadata(), enumdyecolor.getName() + "_concrete");
			this.registerBlock(Blocks.getBlock(Blocks.CONCRETE_POWDER), enumdyecolor.getMetadata(), enumdyecolor.getName() + "_concrete_powder");
		}

		this.registerBlock(Blocks.getBlock(Blocks.CHEST), "chest");
		this.registerBlock(Blocks.getBlock(Blocks.TRAPPED_CHEST), "trapped_chest");
		this.registerBlock(Blocks.getBlock(Blocks.ENDER_CHEST), "ender_chest");
		this.registerItem(Items.getItem(Items.IRON_SHOVEL), "iron_shovel");
		this.registerItem(Items.getItem(Items.IRON_PICKAXE), "iron_pickaxe");
		this.registerItem(Items.getItem(Items.IRON_AXE), "iron_axe");
		this.registerItem(Items.getItem(Items.FLINT_AND_STEEL), "flint_and_steel");
		this.registerItem(Items.getItem(Items.APPLE), "apple");
		this.registerItem(Items.getItem(Items.BOW), "bow");
		this.registerItem(Items.getItem(Items.ARROW), "arrow");
		this.registerItem(Items.getItem(Items.SPECTRAL_ARROW), "spectral_arrow");
		this.registerItem(Items.getItem(Items.TIPPED_ARROW), "tipped_arrow");
		this.registerItem(Items.getItem(Items.OLD_COAL), 0, "coal");
		this.registerItem(Items.getItem(Items.OLD_COAL), 1, "charcoal");
		this.registerItem(Items.getItem(Items.OLD_DIAMOND), "diamond");
		this.registerItem(Items.getItem(Items.OLD_IRON_INGOT), "iron_ingot");
		this.registerItem(Items.getItem(Items.OLD_GOLD_INGOT), "gold_ingot");
		this.registerItem(Items.getItem(Items.IRON_SWORD), "iron_sword");
		this.registerItem(Items.getItem(Items.WOODEN_SWORD), "wooden_sword");
		this.registerItem(Items.getItem(Items.WOODEN_SHOVEL), "wooden_shovel");
		this.registerItem(Items.getItem(Items.WOODEN_PICKAXE), "wooden_pickaxe");
		this.registerItem(Items.getItem(Items.WOODEN_AXE), "wooden_axe");
		this.registerItem(Items.getItem(Items.STONE_SWORD), "stone_sword");
		this.registerItem(Items.getItem(Items.STONE_SHOVEL), "stone_shovel");
		this.registerItem(Items.getItem(Items.STONE_PICKAXE), "stone_pickaxe");
		this.registerItem(Items.getItem(Items.STONE_AXE), "stone_axe");
		this.registerItem(Items.getItem(Items.DIAMOND_SWORD), "diamond_sword");
		this.registerItem(Items.getItem(Items.DIAMOND_SHOVEL), "diamond_shovel");
		this.registerItem(Items.getItem(Items.DIAMOND_PICKAXE), "diamond_pickaxe");
		this.registerItem(Items.getItem(Items.DIAMOND_AXE), "diamond_axe");
		this.registerItem(Items.getItem(Items.STICK), "stick");
		this.registerItem(Items.getItem(Items.BOWL), "bowl");
		this.registerItem(Items.getItem(Items.MUSHROOM_STEW), "mushroom_stew");
		this.registerItem(Items.getItem(Items.GOLDEN_SWORD), "golden_sword");
		this.registerItem(Items.getItem(Items.GOLDEN_SHOVEL), "golden_shovel");
		this.registerItem(Items.getItem(Items.GOLDEN_PICKAXE), "golden_pickaxe");
		this.registerItem(Items.getItem(Items.GOLDEN_AXE), "golden_axe");
		this.registerItem(Items.getItem(Items.STRING), "string");
		this.registerItem(Items.getItem(Items.FEATHER), "feather");
		this.registerItem(Items.getItem(Items.GUNPOWDER), "gunpowder");
		this.registerItem(Items.getItem(Items.WOODEN_HOE), "wooden_hoe");
		this.registerItem(Items.getItem(Items.STONE_HOE), "stone_hoe");
		this.registerItem(Items.getItem(Items.IRON_HOE), "iron_hoe");
		this.registerItem(Items.getItem(Items.DIAMOND_HOE), "diamond_hoe");
		this.registerItem(Items.getItem(Items.GOLDEN_HOE), "golden_hoe");
		this.registerItem(Items.getItem(Items.WHEAT_SEEDS), "wheat_seeds");
		this.registerItem(Items.getItem(Items.WHEAT), "wheat");
		this.registerItem(Items.getItem(Items.BREAD), "bread");
		this.registerItem(Items.getItem(Items.LEATHER_HELMET), "leather_helmet");
		this.registerItem(Items.getItem(Items.LEATHER_CHESTPLATE), "leather_chestplate");
		this.registerItem(Items.getItem(Items.LEATHER_LEGGINGS), "leather_leggings");
		this.registerItem(Items.getItem(Items.LEATHER_BOOTS), "leather_boots");
		this.registerItem(Items.getItem(Items.CHAINMAIL_HELMET), "chainmail_helmet");
		this.registerItem(Items.getItem(Items.CHAINMAIL_CHESTPLATE), "chainmail_chestplate");
		this.registerItem(Items.getItem(Items.CHAINMAIL_LEGGINGS), "chainmail_leggings");
		this.registerItem(Items.getItem(Items.CHAINMAIL_BOOTS), "chainmail_boots");
		this.registerItem(Items.getItem(Items.IRON_HELMET), "iron_helmet");
		this.registerItem(Items.getItem(Items.IRON_CHESTPLATE), "iron_chestplate");
		this.registerItem(Items.getItem(Items.IRON_LEGGINGS), "iron_leggings");
		this.registerItem(Items.getItem(Items.IRON_BOOTS), "iron_boots");
		this.registerItem(Items.getItem(Items.DIAMOND_HELMET), "diamond_helmet");
		this.registerItem(Items.getItem(Items.DIAMOND_CHESTPLATE), "diamond_chestplate");
		this.registerItem(Items.getItem(Items.DIAMOND_LEGGINGS), "diamond_leggings");
		this.registerItem(Items.getItem(Items.DIAMOND_BOOTS), "diamond_boots");
		this.registerItem(Items.getItem(Items.GOLDEN_HELMET), "golden_helmet");
		this.registerItem(Items.getItem(Items.GOLDEN_CHESTPLATE), "golden_chestplate");
		this.registerItem(Items.getItem(Items.GOLDEN_LEGGINGS), "golden_leggings");
		this.registerItem(Items.getItem(Items.GOLDEN_BOOTS), "golden_boots");
		this.registerItem(Items.getItem(Items.FLINT), "flint");
		this.registerItem(Items.getItem(Items.PORKCHOP), "porkchop");
		this.registerItem(Items.getItem(Items.COOKED_PORKCHOP), "cooked_porkchop");
		this.registerItem(Items.getItem(Items.PAINTING), "painting");
		this.registerItem(Items.getItem(Items.GOLDEN_APPLE), "golden_apple");
		this.registerItem(Items.getItem(Items.GOLDEN_APPLE), 1, "golden_apple");
		this.registerItem(Items.getItem(Items.SIGN), "sign");
		this.registerItem(Items.getItem(Items.OAK_DOOR), "oak_door");
		this.registerItem(Items.getItem(Items.SPRUCE_DOOR), "spruce_door");
		this.registerItem(Items.getItem(Items.BIRCH_DOOR), "birch_door");
		this.registerItem(Items.getItem(Items.JUNGLE_DOOR), "jungle_door");
		this.registerItem(Items.getItem(Items.ACACIA_DOOR), "acacia_door");
		this.registerItem(Items.getItem(Items.DARK_OAK_DOOR), "dark_oak_door");
		this.registerItem(Items.getItem(Items.BUCKET), "bucket");
		this.registerItem(Items.getItem(Items.WATER_BUCKET), "water_bucket");
		this.registerItem(Items.getItem(Items.LAVA_BUCKET), "lava_bucket");
		this.registerItem(Items.getItem(Items.MINECART), "minecart");
		this.registerItem(Items.getItem(Items.SADDLE), "saddle");
		this.registerItem(Items.getItem(Items.IRON_DOOR), "iron_door");
		this.registerItem(Items.getItem(Items.REDSTONE), "redstone");
		this.registerItem(Items.getItem(Items.SNOWBALL), "snowball");
		this.registerItem(Items.getItem(Items.BOAT), "oak_boat");
		this.registerItem(Items.getItem(Items.SPRUCE_BOAT), "spruce_boat");
		this.registerItem(Items.getItem(Items.BIRCH_BOAT), "birch_boat");
		this.registerItem(Items.getItem(Items.JUNGLE_BOAT), "jungle_boat");
		this.registerItem(Items.getItem(Items.ACACIA_BOAT), "acacia_boat");
		this.registerItem(Items.getItem(Items.DARK_OAK_BOAT), "dark_oak_boat");
		this.registerItem(Items.getItem(Items.LEATHER), "leather");
		this.registerItem(Items.getItem(Items.MILK_BUCKET), "milk_bucket");
		this.registerItem(Items.getItem(Items.BRICK), "brick");
		this.registerItem(Items.getItem(Items.CLAY_BALL), "clay_ball");
		this.registerItem(Items.getItem(Items.REEDS), "reeds");
		this.registerItem(Items.getItem(Items.PAPER), "paper");
		this.registerItem(Items.getItem(Items.BOOK), "book");
		this.registerItem(Items.getItem(Items.SLIME_BALL), "slime_ball");
		this.registerItem(Items.getItem(Items.CHEST_MINECART), "chest_minecart");
		this.registerItem(Items.getItem(Items.FURNACE_MINECART), "furnace_minecart");
		this.registerItem(Items.getItem(Items.EGG), "egg");
		this.registerItem(Items.getItem(Items.COMPASS), "compass");
		this.registerItem(Items.getItem(Items.FISHING_ROD), "fishing_rod");
		this.registerItem(Items.getItem(Items.CLOCK), "clock");
		this.registerItem(Items.getItem(Items.GLOWSTONE_DUST), "glowstone_dust");
		this.registerItem(Items.getItem(Items.FISH), ItemFishFood.FishType.COD.getMetadata(), "cod");
		this.registerItem(Items.getItem(Items.FISH), ItemFishFood.FishType.SALMON.getMetadata(), "salmon");
		this.registerItem(Items.getItem(Items.FISH), ItemFishFood.FishType.CLOWNFISH.getMetadata(), "clownfish");
		this.registerItem(Items.getItem(Items.FISH), ItemFishFood.FishType.PUFFERFISH.getMetadata(), "pufferfish");
		this.registerItem(Items.getItem(Items.COOKED_FISH), ItemFishFood.FishType.COD.getMetadata(), "cooked_cod");
		this.registerItem(Items.getItem(Items.COOKED_FISH), ItemFishFood.FishType.SALMON.getMetadata(), "cooked_salmon");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.BLACK.getDyeDamage(), "dye_black");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.RED.getDyeDamage(), "dye_red");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.GREEN.getDyeDamage(), "dye_green");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.BROWN.getDyeDamage(), "dye_brown");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.BLUE.getDyeDamage(), "dye_blue");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.PURPLE.getDyeDamage(), "dye_purple");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.CYAN.getDyeDamage(), "dye_cyan");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.SILVER.getDyeDamage(), "dye_silver");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.GRAY.getDyeDamage(), "dye_gray");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.PINK.getDyeDamage(), "dye_pink");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.LIME.getDyeDamage(), "dye_lime");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.YELLOW.getDyeDamage(), "dye_yellow");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.LIGHT_BLUE.getDyeDamage(), "dye_light_blue");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.MAGENTA.getDyeDamage(), "dye_magenta");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.ORANGE.getDyeDamage(), "dye_orange");
		this.registerItem(Items.getItem(Items.DYE), EnumDyeColor.WHITE.getDyeDamage(), "dye_white");
		this.registerItem(Items.getItem(Items.BONE), "bone");
		this.registerItem(Items.getItem(Items.SUGAR), "sugar");
		this.registerItem(Items.getItem(Items.CAKE), "cake");
		this.registerItem(Items.getItem(Items.REPEATER), "repeater");
		this.registerItem(Items.getItem(Items.COOKIE), "cookie");
		this.registerItem(Items.getItem(Items.SHEARS), "shears");
		this.registerItem(Items.getItem(Items.MELON), "melon");
		this.registerItem(Items.getItem(Items.PUMPKIN_SEEDS), "pumpkin_seeds");
		this.registerItem(Items.getItem(Items.MELON_SEEDS), "melon_seeds");
		this.registerItem(Items.getItem(Items.BEEF), "beef");
		this.registerItem(Items.getItem(Items.COOKED_BEEF), "cooked_beef");
		this.registerItem(Items.getItem(Items.CHICKEN), "chicken");
		this.registerItem(Items.getItem(Items.COOKED_CHICKEN), "cooked_chicken");
		this.registerItem(Items.getItem(Items.RABBIT), "rabbit");
		this.registerItem(Items.getItem(Items.COOKED_RABBIT), "cooked_rabbit");
		this.registerItem(Items.getItem(Items.MUTTON), "mutton");
		this.registerItem(Items.getItem(Items.COOKED_MUTTON), "cooked_mutton");
		this.registerItem(Items.getItem(Items.RABBIT_FOOT), "rabbit_foot");
		this.registerItem(Items.getItem(Items.RABBIT_HIDE), "rabbit_hide");
		this.registerItem(Items.getItem(Items.RABBIT_STEW), "rabbit_stew");
		this.registerItem(Items.getItem(Items.ROTTEN_FLESH), "rotten_flesh");
		this.registerItem(Items.getItem(Items.ENDER_PEARL), "ender_pearl");
		this.registerItem(Items.getItem(Items.BLAZE_ROD), "blaze_rod");
		this.registerItem(Items.getItem(Items.GHAST_TEAR), "ghast_tear");
		this.registerItem(Items.getItem(Items.GOLD_NUGGET), "gold_nugget");
		this.registerItem(Items.getItem(Items.NETHER_WART), "nether_wart");
		this.registerItem(Items.getItem(Items.BEETROOT), "beetroot");
		this.registerItem(Items.getItem(Items.BEETROOT_SEEDS), "beetroot_seeds");
		this.registerItem(Items.getItem(Items.BEETROOT_SOUP), "beetroot_soup");
		this.registerItem(Items.getItem(Items.TOTEM), "totem");
		this.registerItem(Items.getItem(Items.POTIONITEM), "bottle_drinkable");
		this.registerItem(Items.getItem(Items.SPLASH_POTION), "bottle_splash");
		this.registerItem(Items.getItem(Items.LINGERING_POTION), "bottle_lingering");
		this.registerItem(Items.getItem(Items.GLASS_BOTTLE), "glass_bottle");
		this.registerItem(Items.getItem(Items.DRAGON_BREATH), "dragon_breath");
		this.registerItem(Items.getItem(Items.SPIDER_EYE), "spider_eye");
		this.registerItem(Items.getItem(Items.FERMENTED_SPIDER_EYE), "fermented_spider_eye");
		this.registerItem(Items.getItem(Items.BLAZE_POWDER), "blaze_powder");
		this.registerItem(Items.getItem(Items.MAGMA_CREAM), "magma_cream");
		this.registerItem(Items.getItem(Items.BREWING_STAND), "brewing_stand");
		this.registerItem(Items.getItem(Items.CAULDRON), "cauldron");
		this.registerItem(Items.getItem(Items.ENDER_EYE), "ender_eye");
		this.registerItem(Items.getItem(Items.SPECKLED_MELON), "speckled_melon");
		this.itemModelMesher.register(Items.getItem(Items.SPAWN_EGG), new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation("spawn_egg", "inventory");
			}
		});
		this.registerItem(Items.getItem(Items.EXPERIENCE_BOTTLE), "experience_bottle");
		this.registerItem(Items.getItem(Items.FIRE_CHARGE), "fire_charge");
		this.registerItem(Items.getItem(Items.WRITABLE_BOOK), "writable_book");
		this.registerItem(Items.getItem(Items.EMERALD), "emerald");
		this.registerItem(Items.getItem(Items.ITEM_FRAME), "item_frame");
		this.registerItem(Items.getItem(Items.FLOWER_POT), "flower_pot");
		this.registerItem(Items.getItem(Items.CARROT), "carrot");
		this.registerItem(Items.getItem(Items.POTATO), "potato");
		this.registerItem(Items.getItem(Items.BAKED_POTATO), "baked_potato");
		this.registerItem(Items.getItem(Items.POISONOUS_POTATO), "poisonous_potato");
		this.registerItem(Items.getItem(Items.MAP), "map");
		this.registerItem(Items.getItem(Items.GOLDEN_CARROT), "golden_carrot");
		this.registerItem(Items.getItem(Items.SKULL), 0, "skull_skeleton");
		this.registerItem(Items.getItem(Items.SKULL), 1, "skull_wither");
		this.registerItem(Items.getItem(Items.SKULL), 2, "skull_zombie");
		this.registerItem(Items.getItem(Items.SKULL), 3, "skull_char");
		this.registerItem(Items.getItem(Items.SKULL), 4, "skull_creeper");
		this.registerItem(Items.getItem(Items.SKULL), 5, "skull_dragon");
		this.registerItem(Items.getItem(Items.CARROT_ON_A_STICK), "carrot_on_a_stick");
		this.registerItem(Items.getItem(Items.NETHER_STAR), "nether_star");
		this.registerItem(Items.getItem(Items.END_CRYSTAL), "end_crystal");
		this.registerItem(Items.getItem(Items.PUMPKIN_PIE), "pumpkin_pie");
		this.registerItem(Items.getItem(Items.FIREWORK_CHARGE), "firework_charge");
		this.registerItem(Items.getItem(Items.COMPARATOR), "comparator");
		this.registerItem(Items.getItem(Items.NETHERBRICK), "netherbrick");
		this.registerItem(Items.getItem(Items.QUARTZ), "quartz");
		this.registerItem(Items.getItem(Items.TNT_MINECART), "tnt_minecart");
		this.registerItem(Items.getItem(Items.HOPPER_MINECART), "hopper_minecart");
		this.registerItem(Items.getItem(Items.ARMOR_STAND), "armor_stand");
		this.registerItem(Items.getItem(Items.IRON_HORSE_ARMOR), "iron_horse_armor");
		this.registerItem(Items.getItem(Items.GOLDEN_HORSE_ARMOR), "golden_horse_armor");
		this.registerItem(Items.getItem(Items.DIAMOND_HORSE_ARMOR), "diamond_horse_armor");
		this.registerItem(Items.getItem(Items.LEAD), "lead");
		this.registerItem(Items.getItem(Items.NAME_TAG), "name_tag");
		this.itemModelMesher.register(Items.getItem(Items.BANNER), new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation("banner", "inventory");
			}
		});
		this.itemModelMesher.register(Items.getItem(Items.BED), new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation("bed", "inventory");
			}
		});
		this.itemModelMesher.register(Items.getItem(Items.SHIELD), new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation("shield", "inventory");
			}
		});
		this.registerItem(Items.getItem(Items.ELYTRA), "elytra");
		this.registerItem(Items.getItem(Items.CHORUS_FRUIT), "chorus_fruit");
		this.registerItem(Items.getItem(Items.CHORUS_FRUIT_POPPED), "chorus_fruit_popped");
		this.registerItem(Items.getItem(Items.SHULKER_SHELL), "shulker_shell");
		this.registerItem(Items.getItem(Items.IRON_NUGGET), "iron_nugget");
		this.registerItem(Items.getItem(Items.RECORD_13), "record_13");
		this.registerItem(Items.getItem(Items.RECORD_CAT), "record_cat");
		this.registerItem(Items.getItem(Items.RECORD_BLOCKS), "record_blocks");
		this.registerItem(Items.getItem(Items.RECORD_CHIRP), "record_chirp");
		this.registerItem(Items.getItem(Items.RECORD_FAR), "record_far");
		this.registerItem(Items.getItem(Items.RECORD_MALL), "record_mall");
		this.registerItem(Items.getItem(Items.RECORD_MELLOHI), "record_mellohi");
		this.registerItem(Items.getItem(Items.RECORD_STAL), "record_stal");
		this.registerItem(Items.getItem(Items.RECORD_STRAD), "record_strad");
		this.registerItem(Items.getItem(Items.RECORD_WARD), "record_ward");
		this.registerItem(Items.getItem(Items.RECORD_11), "record_11");
		this.registerItem(Items.getItem(Items.RECORD_WAIT), "record_wait");
		this.registerItem(Items.getItem(Items.PRISMARINE_SHARD), "prismarine_shard");
		this.registerItem(Items.getItem(Items.PRISMARINE_CRYSTALS), "prismarine_crystals");
		this.registerItem(Items.getItem(Items.KNOWLEDGE_BOOK), "knowledge_book");
		this.itemModelMesher.register(Items.getItem(Items.ENCHANTED_BOOK), new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation("enchanted_book", "inventory");
			}
		});
		this.itemModelMesher.register(Items.getItem(Items.FILLED_MAP), new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				return new ModelResourceLocation("filled_map", "inventory");
			}
		});
		this.registerBlock(Blocks.getBlock(Blocks.COMMAND_BLOCK), "command_block");
		this.registerItem(Items.getItem(Items.FIREWORKS), "fireworks");
		this.registerItem(Items.getItem(Items.COMMAND_BLOCK_MINECART), "command_block_minecart");
		this.registerBlock(Blocks.getBlock(Blocks.BARRIER), "barrier");
		this.registerBlock(Blocks.getBlock(Blocks.MOB_SPAWNER), "mob_spawner");
		this.registerItem(Items.getItem(Items.WRITTEN_BOOK), "written_book");
		this.registerBlock(Blocks.getBlock(Blocks.BROWN_MUSHROOM_BLOCK), BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "brown_mushroom_block");
		this.registerBlock(Blocks.getBlock(Blocks.RED_MUSHROOM_BLOCK), BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "red_mushroom_block");
		this.registerBlock(Blocks.getBlock(Blocks.DRAGON_EGG), "dragon_egg");
		this.registerBlock(Blocks.getBlock(Blocks.REPEATING_COMMAND_BLOCK), "repeating_command_block");
		this.registerBlock(Blocks.getBlock(Blocks.CHAIN_COMMAND_BLOCK), "chain_command_block");
		this.registerBlock(Blocks.getBlock(Blocks.STRUCTURE_BLOCK), TileEntityStructure.Mode.SAVE.getModeId(), "structure_block");
		this.registerBlock(Blocks.getBlock(Blocks.STRUCTURE_BLOCK), TileEntityStructure.Mode.LOAD.getModeId(), "structure_block");
		this.registerBlock(Blocks.getBlock(Blocks.STRUCTURE_BLOCK), TileEntityStructure.Mode.CORNER.getModeId(), "structure_block");
		this.registerBlock(Blocks.getBlock(Blocks.STRUCTURE_BLOCK), TileEntityStructure.Mode.DATA.getModeId(), "structure_block");

		/**
		 * MOD ITEM
		 */
		ModRenderItem.registerItemRender(this);
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		this.itemModelMesher.rebuildCache();
	}
}
