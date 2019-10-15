package fr.minecraftpp.crafting.blueprint;

import java.security.InvalidParameterException;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public abstract class AbstractBlueprint
{
	private Item[][] matrix;
	
	public AbstractBlueprint(int matrixSize)
	{
		matrix = new Item[matrixSize][matrixSize];
		
		this.setAllItems(Items.EMPTY_ITEM);
	}
	
	public AbstractBlueprint(Item[][] itemMatrix)
	{
		this(itemMatrix.length);
		
		this.setItems(itemMatrix);
	}
	
	public void setItem(int x, int y, Item item)
	{
		this.matrix[x][y] = item;
	}
	
	public void setItem(int i, Item item)
	{
		this.matrix[i / this.matrix.length][i % this.matrix.length] = item;
	}
	
	public void setItems(Item[][] itemMatrix)
	{
		testIfItemMatrixIsCorrect(itemMatrix);
		
		for (int i = 0; i < itemMatrix.length; i++)
		{
			for (int j = 0; j < itemMatrix[i].length; j++)
			{
				Item item = itemMatrix[i][j];
				if(item != null)
				{
					this.matrix[i][j] = item;
				}
				else
				{
					this.matrix[i][j] = Items.EMPTY_ITEM;
				}
			}
		}
	}

	protected void testIfItemMatrixIsCorrect(Item[][] itemMatrix)
	{
		if(itemMatrix.length != this.getMatrixSize() || itemMatrix[0].length != this.getMatrixSize())
		{
			throw new InvalidParameterException("Array is incorrect size, should be [" + this.getMatrixSize() + "][" + this.getMatrixSize() + "]");
		}
	}
	
	public void setAllItems(Item generalItem)
	{
		for(Item[] items : this.matrix)
		{
			for(Item item : items)
			{
				item = generalItem;
			}
		}
	}
	
	public Item getItem(int x, int y)
	{
		return this.matrix[x][y];
	}
	
	public Item getItem(int i)
	{
		return this.matrix[i / this.matrix.length][i % this.matrix.length];
	}
	
	public abstract int getMatrixSize();
}
