package net.minecraft.entity.ai;

import fr.minecraftpp.inventory.EntityArmorSlot;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun extends EntityAIBase
{
	private final EntityCreature theEntity;

	public EntityAIRestrictSun(EntityCreature creature)
	{
		this.theEntity = creature;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		return this.theEntity.world.isDaytime() && this.theEntity.getItemStackFromSlot(EntityArmorSlot.HEAD).isNotValid();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		((PathNavigateGround) this.theEntity.getNavigator()).setAvoidSun(true);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		((PathNavigateGround) this.theEntity.getNavigator()).setAvoidSun(false);
	}
}
