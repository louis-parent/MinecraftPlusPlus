package net.minecraft.client.renderer.tileentity;

import java.util.List;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon>
{
	public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");

	@Override
	public void func_192841_a(TileEntityBeacon p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_)
	{
		this.renderBeacon(p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_1_.shouldBeamRender(), p_192841_1_.getBeamSegments(), p_192841_1_.getWorld().getTotalWorldTime());
	}

	public void renderBeacon(double p_188206_1_, double p_188206_3_, double p_188206_5_, double p_188206_7_, double p_188206_9_, List<TileEntityBeacon.BeamSegment> p_188206_11_, double p_188206_12_)
	{
		GlStateManager.alphaFunc(516, 0.1F);
		this.bindTexture(TEXTURE_BEACON_BEAM);

		if (p_188206_9_ > 0.0D)
		{
			GlStateManager.disableFog();
			int i = 0;

			for (int j = 0; j < p_188206_11_.size(); ++j)
			{
				TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = p_188206_11_.get(j);
				renderBeamSegment(p_188206_1_, p_188206_3_, p_188206_5_, p_188206_7_, p_188206_9_, p_188206_12_, i, tileentitybeacon$beamsegment.getHeight(), tileentitybeacon$beamsegment.getColors());
				i += tileentitybeacon$beamsegment.getHeight();
			}

			GlStateManager.enableFog();
		}
	}

	public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors)
	{
		renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, 0.2D, 0.25D);
	}

	public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors, double beamRadius, double glowRadius)
	{
		int i = yOffset + height;
		GlStateManager.glTexParameteri(3553, 10242, 10497);
		GlStateManager.glTexParameteri(3553, 10243, 10497);
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		double d0 = totalWorldTime + partialTicks;
		double d1 = height < 0 ? d0 : -d0;
		double d2 = MathHelper.frac(d1 * 0.2D - MathHelper.floor(d1 * 0.1D));
		float f = colors[0];
		float f1 = colors[1];
		float f2 = colors[2];
		double d3 = d0 * 0.025D * -1.5D;
		double d4 = 0.5D + Math.cos(d3 + 2.356194490192345D) * beamRadius;
		double d5 = 0.5D + Math.sin(d3 + 2.356194490192345D) * beamRadius;
		double d6 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * beamRadius;
		double d7 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * beamRadius;
		double d8 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * beamRadius;
		double d9 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * beamRadius;
		double d10 = 0.5D + Math.cos(d3 + 5.497787143782138D) * beamRadius;
		double d11 = 0.5D + Math.sin(d3 + 5.497787143782138D) * beamRadius;
		double d12 = 0.0D;
		double d13 = 1.0D;
		double d14 = -1.0D + d2;
		double d15 = height * textureScale * (0.5D / beamRadius) + d14;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(x + d4, y + i, z + d5).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d6, y + i, z + d7).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d10, y + i, z + d11).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d8, y + i, z + d9).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d6, y + i, z + d7).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d10, y + i, z + d11).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d8, y + i, z + d9).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(x + d4, y + i, z + d5).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
		tessellator.draw();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.depthMask(false);
		d3 = 0.5D - glowRadius;
		d4 = 0.5D - glowRadius;
		d5 = 0.5D + glowRadius;
		d6 = 0.5D - glowRadius;
		d7 = 0.5D - glowRadius;
		d8 = 0.5D + glowRadius;
		d9 = 0.5D + glowRadius;
		d10 = 0.5D + glowRadius;
		d11 = 0.0D;
		d12 = 1.0D;
		d13 = -1.0D + d2;
		d14 = height * textureScale + d13;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(x + d3, y + i, z + d4).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d5, y + i, z + d6).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d9, y + i, z + d10).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d7, y + i, z + d8).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d5, y + i, z + d6).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d9, y + i, z + d10).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d7, y + i, z + d8).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(x + d3, y + i, z + d4).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
		tessellator.draw();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.depthMask(true);
	}

	@Override
	public boolean isGlobalRenderer(TileEntityBeacon te)
	{
		return true;
	}
}
