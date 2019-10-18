package fr.minecraftpp.inventory;

import java.util.UUID;

public enum EntityArmorSlot implements EntityEquipmentSlot
{
	FEET(0, 1, "feet", 13, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), "minecraft:items/empty_armor_slot_boots"),
    LEGS(1, 2, "legs", 15, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), "minecraft:items/empty_armor_slot_leggings"),
    CHEST(2, 3, "chest", 16, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), "minecraft:items/empty_armor_slot_chestplate"),
    HEAD(3, 4, "head", 11, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"), "minecraft:items/empty_armor_slot_helmet");

	private final int index;
    private final int slotIndex;
    private final String name;
	private final int baseDurability;
	private final UUID uuid;
	private final String emptySlotName;

    private EntityArmorSlot(int index, int slotIndex, String name, int baseDurability, UUID uuid, String emptySlotName)
    {
        this.index = index;
        this.slotIndex = slotIndex;
        this.name = name;
        this.baseDurability = baseDurability;
        this.uuid = uuid;
        this.emptySlotName = emptySlotName;
        
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
	
	public int getBaseDurability()
	{
		return this.baseDurability;
	}
	
	public UUID getModifierUUID()
	{
		return this.uuid;
	}
	
	public String getEmptySlotName()
	{
		return this.emptySlotName;
	}
}
