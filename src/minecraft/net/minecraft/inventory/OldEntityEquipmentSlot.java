package net.minecraft.inventory;

public enum OldEntityEquipmentSlot
{
	MAINHAND(OldEntityEquipmentSlot.Type.HAND, 0, 0, "mainhand"), OFFHAND(OldEntityEquipmentSlot.Type.HAND, 1, 5, "offhand"), FEET(OldEntityEquipmentSlot.Type.ARMOR, 0, 1, "feet"), LEGS(OldEntityEquipmentSlot.Type.ARMOR, 1, 2, "legs"), CHEST(OldEntityEquipmentSlot.Type.ARMOR, 2, 3, "chest"), HEAD(OldEntityEquipmentSlot.Type.ARMOR, 3, 4, "head");

	private final OldEntityEquipmentSlot.Type slotType;
	private final int index;
	private final int slotIndex;
	private final String name;

	private OldEntityEquipmentSlot(OldEntityEquipmentSlot.Type slotTypeIn, int indexIn, int slotIndexIn, String nameIn)
	{
		this.slotType = slotTypeIn;
		this.index = indexIn;
		this.slotIndex = slotIndexIn;
		this.name = nameIn;
	}

	public OldEntityEquipmentSlot.Type getSlotType()
	{
		return this.slotType;
	}

	public int getIndex()
	{
		return this.index;
	}

	/**
	 * Gets the actual slot index.
	 */
	public int getSlotIndex()
	{
		return this.slotIndex;
	}

	public String getName()
	{
		return this.name;
	}

	public static OldEntityEquipmentSlot fromString(String targetName)
	{
		for (OldEntityEquipmentSlot entityequipmentslot : values())
		{
			if (entityequipmentslot.getName().equals(targetName))
			{
				return entityequipmentslot;
			}
		}

		throw new IllegalArgumentException("Invalid slot '" + targetName + "'");
	}

	public static enum Type
	{
		HAND, ARMOR;
	}
}
