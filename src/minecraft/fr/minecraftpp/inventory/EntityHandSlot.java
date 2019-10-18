package fr.minecraftpp.inventory;

public enum EntityHandSlot implements EntityEquipmentSlot
{
	MAINHAND(0, 0, "mainhand"), OFFHAND(1, 5, "offhand");

	private final int index;
	private final int slotIndex;
	private final String name;

	private EntityHandSlot(int index, int slotIndex, String name)
	{
		this.index = index;
		this.slotIndex = slotIndex;
		this.name = name;

		EntityEquipmentSlot.register(this);
	}

	@Override
	public int getIndex()
	{
		return index;
	}

	@Override
	public int getSlotIndex()
	{
		return slotIndex;
	}

	@Override
	public String getName()
	{
		return name;
	}

}
