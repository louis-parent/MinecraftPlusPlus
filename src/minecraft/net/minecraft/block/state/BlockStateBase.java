package net.minecraft.block.state;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

public abstract class BlockStateBase implements IBlockState
{
	private static final Joiner COMMA_JOINER = Joiner.on(',');
	private static final Function<Entry<IProperty<?>, Comparable<?>>, String> MAP_ENTRY_TO_STRING = new Function<Entry<IProperty<?>, Comparable<?>>, String>()
	{
		@Override
		@Nullable
		public String apply(@Nullable Entry<IProperty<?>, Comparable<?>> p_apply_1_)
		{
			if (p_apply_1_ == null)
			{
				return "<NULL>";
			}
			else
			{
				IProperty<?> iproperty = p_apply_1_.getKey();
				return iproperty.getName() + "=" + this.getPropertyName(iproperty, p_apply_1_.getValue());
			}
		}

		private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> entry)
		{
			return property.getName((T) entry);
		}
	};

	@Override
	public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property)
	{
		return this.withProperty(property, cyclePropertyValue(property.getAllowedValues(), this.getValue(property)));
	}

	protected static <T> T cyclePropertyValue(Collection<T> values, T currentValue)
	{
		Iterator<T> iterator = values.iterator();

		while (iterator.hasNext())
		{
			if (iterator.next().equals(currentValue))
			{
				if (iterator.hasNext())
				{
					return iterator.next();
				}

				return values.iterator().next();
			}
		}

		return iterator.next();
	}

	@Override
	public String toString()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(Block.REGISTRY.getNameForObject(this.getBlock()));

		if (!this.getProperties().isEmpty())
		{
			stringbuilder.append("[");
			COMMA_JOINER.appendTo(stringbuilder, Iterables.transform(this.getProperties().entrySet(), MAP_ENTRY_TO_STRING));
			stringbuilder.append("]");
		}

		return stringbuilder.toString();
	}
}
