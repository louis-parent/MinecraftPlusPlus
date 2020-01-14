package fr.minecraftpp.crafting.blueprint;

import java.security.InvalidParameterException;
import java.util.Arrays;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.anotation.Todo;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

@Mod("Minecraftpp")
public class Blueprint
{
	private Item[][] matrix;
	private Item[][] symmetryMatrix;

	private int width;
	private int height;

	public Blueprint(int matrixWidth, int matrixHeight)
	{
		setAttributes(matrixWidth, matrixHeight);

		this.setAllItems(Items.getItem(Items.AIR));
	}

	public Blueprint(Item[][] itemMatrix)
	{
		this.setItems(itemMatrix);
	}

	private void setAttributes(int matrixWidth, int matrixHeight)
	{
		this.matrix = new Item[matrixWidth][matrixHeight];
		this.symmetryMatrix = new Item[matrixWidth][matrixHeight];

		this.width = matrixWidth;
		this.height = matrixHeight;
	}

	public void setItem(int i, int j, Item item)
	{
		this.matrix[i][j] = item;
	}

	public void setItem(int i, Item item)
	{
		this.matrix[i / this.width][i % this.width] = item;
	}

	@Todo("Do Shrink")
	public void setItems(Item[][] itemMatrix)
	{
		testIfItemMatrixIsCorrect(itemMatrix);

		this.setAttributes(itemMatrix.length, itemMatrix[0].length);

		int i, j = 0;

		for (i = 0; i < this.width; i++)
		{
			for (j = 0; j < this.height; j++)
			{
				Item item = itemMatrix[i][j];
				if (item != null)
				{
					this.matrix[i][j] = item;
					this.symmetryMatrix[i][this.height - j - 1] = item;
				}
				else
				{
					this.matrix[i][j] = Items.getItem(Items.AIR);
					this.symmetryMatrix[i][this.height - j - 1] = Items.getItem(Items.AIR);
				}
			}
		}

		Item[][] temp = this.symmetryMatrix;
	}

	protected void testIfItemMatrixIsCorrect(Item[][] itemMatrix)
	{
		boolean isCorrect = true;

		if (!(itemMatrix.length > 0 && itemMatrix.length < 4))
		{
			isCorrect = false;
		}
		else
		{
			int i = 0;
			do
			{
				int length = itemMatrix[i].length;
				i++;

				if (itemMatrix.length > 1)
				{
					isCorrect &= length == itemMatrix[i].length && length > 0 && length < 4;
				}
			} while (i < (itemMatrix.length - 1));
		}

		if (!isCorrect)
		{
			throw new InvalidParameterException("Array is incorrect size, should be rectangular");
		}
	}

	public void setAllItems(Item generalItem)
	{
		for (Item[] items : this.matrix)
		{
			for (Item item : items)
			{
				item = generalItem;
			}
		}
	}

	public Item getItem(int i, int j)
	{
		return this.matrix[i][j];
	}

	public Item getItem(int i)
	{
		return this.matrix[i / this.matrix.length][i % this.matrix.length];
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public boolean matches(InventoryCrafting craftShape)
	{
		Item[][] backupArray = this.createBackupArrayFrom(craftShape);

		int iterationOverWidth = craftShape.getWidth() - this.width;
		int iterationOverHeight = craftShape.getHeight() - this.height;

		return this.matchBlueprint(this.matrix, backupArray, iterationOverWidth, iterationOverHeight) || this.matchBlueprint(this.symmetryMatrix, backupArray, iterationOverWidth, iterationOverHeight);

	}

	private boolean matchBlueprint(Item[][] itemMatrix, Item[][] backupArray, int iterationOverWidth, int iterationOverHeight)
	{
		boolean match = false;

		for (int i = 0; i <= iterationOverWidth; i++)
		{
			for (int j = 0; j <= iterationOverHeight; j++)
			{
				match |= this.matchBlueprintEmplacement(itemMatrix, this.copy2DArray(backupArray), i, j);
			}
		}
		return match;
	}

	private Item[][] createBackupArrayFrom(InventoryCrafting craftShape)
	{
		Item[][] backup = new Item[craftShape.getWidth()][craftShape.getHeight()];

		for (int i = 0; i < backup.length; i++)
		{
			for (int j = 0; j < backup[i].length; j++)
			{
				backup[i][j] = craftShape.getStackInRowAndColumn(j, i).getItem();
			}
		}

		return backup;
	}

	private Item[][] copy2DArray(Item[][] backupArray)
	{
		if (backupArray == null)
		{
			return null;
		}

		Item[][] result = new Item[backupArray.length][];

		for (int i = 0; i < backupArray.length; i++)
		{
			result[i] = Arrays.copyOf(backupArray[i], backupArray[i].length);
		}

		return result;
	}

	private boolean matchBlueprintEmplacement(Item[][] matrix, Item[][] craftShape, int originI, int originJ)
	{
		boolean match = true;

		for (int i = originI; i < originI + this.width; i++)
		{
			for (int j = originJ; j < originJ + this.height; j++)
			{
				match &= craftShape[i][j] == matrix[i - originI][j - originJ];
				craftShape[i][j] = Items.getItem(Items.AIR);
			}
		}

		return match && this.checkOutterBlueprint(craftShape);
	}

	private boolean checkOutterBlueprint(Item[][] craftShape)
	{
		boolean isEmpty = true;

		for (int i = 0; i < craftShape.length; i++)
		{
			for (int j = 0; j < craftShape[i].length; j++)
			{
				isEmpty &= craftShape[i][j] == Items.getItem(Items.AIR);
			}
		}

		return isEmpty;
	}

	public Ingredient[] toIngredients()
	{
		Ingredient[] ingredients = new Ingredient[9];
		int next = 0;

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (i < this.matrix.length && j < this.matrix[i].length)
				{
					ingredients[next] = Ingredient.getIngredientFromItemStack(this.matrix[i][j].getAsStack());
				}
				else
				{
					ingredients[next] = Ingredient.INGREDIENT_AIR;
				}
				next++;
			}
		}

		return ingredients;
	}
}
