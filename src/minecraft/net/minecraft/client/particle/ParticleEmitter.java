package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ParticleEmitter extends Particle
{
	private final Entity attachedEntity;
	private int age;
	private final int lifetime;
	private final EnumParticleTypes particleTypes;

	public ParticleEmitter(World worldIn, Entity p_i46279_2_, EnumParticleTypes particleTypesIn)
	{
		this(worldIn, p_i46279_2_, particleTypesIn, 3);
	}

	public ParticleEmitter(World p_i47219_1_, Entity p_i47219_2_, EnumParticleTypes p_i47219_3_, int p_i47219_4_)
	{
		super(p_i47219_1_, p_i47219_2_.posX, p_i47219_2_.getEntityBoundingBox().minY + p_i47219_2_.height / 2.0F, p_i47219_2_.posZ, p_i47219_2_.motionX, p_i47219_2_.motionY, p_i47219_2_.motionZ);
		this.attachedEntity = p_i47219_2_;
		this.lifetime = p_i47219_4_;
		this.particleTypes = p_i47219_3_;
		this.onUpdate();
	}

	/**
	 * Renders the particle
	 */
	@Override
	public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
	}

	@Override
	public void onUpdate()
	{
		for (int i = 0; i < 16; ++i)
		{
			double d0 = this.rand.nextFloat() * 2.0F - 1.0F;
			double d1 = this.rand.nextFloat() * 2.0F - 1.0F;
			double d2 = this.rand.nextFloat() * 2.0F - 1.0F;

			if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D)
			{
				double d3 = this.attachedEntity.posX + d0 * this.attachedEntity.width / 4.0D;
				double d4 = this.attachedEntity.getEntityBoundingBox().minY + this.attachedEntity.height / 2.0F + d1 * this.attachedEntity.height / 4.0D;
				double d5 = this.attachedEntity.posZ + d2 * this.attachedEntity.width / 4.0D;
				this.worldObj.spawnParticle(this.particleTypes, false, d3, d4, d5, d0, d1 + 0.2D, d2);
			}
		}

		++this.age;

		if (this.age >= this.lifetime)
		{
			this.setExpired();
		}
	}

	/**
	 * Retrieve what effect layer (what texture) the particle should be rendered
	 * with. 0 for the particle sprite sheet, 1 for the main Texture atlas, and
	 * 3 for a custom texture
	 */
	@Override
	public int getFXLayer()
	{
		return 3;
	}
}
