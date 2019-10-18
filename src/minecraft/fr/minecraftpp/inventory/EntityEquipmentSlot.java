package fr.minecraftpp.inventory;

import java.util.ArrayList;

public interface EntityEquipmentSlot
{
	public static final ArrayList<EntityEquipmentSlot> SLOTS = new ArrayList<EntityEquipmentSlot>();
	
	public int getIndex();
	
	public int getSlotIndex();
	
	public String getName();

    public static EntityEquipmentSlot fromString(String targetName)
    {
        for (EntityEquipmentSlot entityequipmentslot : SLOTS)
        {
            if (entityequipmentslot.getName().equals(targetName))
            {
                return entityequipmentslot;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + targetName + "'");
    }
    
    public static void register(EntityEquipmentSlot slot)
    {
    	SLOTS.add(slot);
    }
    
    public static EntityEquipmentSlot[] values()
    {
    	EntityEquipmentSlot[] slots = new EntityEquipmentSlot[SLOTS.size()];
    	
    	for (int i = 0; i < SLOTS.size(); i++)
		{
			slots[i] = SLOTS.get(i);
		}
    	
    	return slots;
    }
    
    public static int getNameMaxLength()
    {
    	int length = 0;
    	
    	for (EntityEquipmentSlot slot : SLOTS)
		{
			if(slot.getName().length() > length)
			{
				length = slot.getName().length();
			}
		}
    	
    	return length;
    }
}
