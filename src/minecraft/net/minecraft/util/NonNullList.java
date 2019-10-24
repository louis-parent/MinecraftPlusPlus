package net.minecraft.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

public class NonNullList<E> extends AbstractList<E>
{
	private final List<E> list;
	private final E emptyElement;

	public static <E> NonNullList<E> getInstance()
	{
		return new NonNullList<E>();
	}

	public static <E> NonNullList<E> getInstanceFilledWith(int nbOf, E element)
	{
		Validate.notNull(element);
		Object[] aobject = new Object[nbOf];
		Arrays.fill(aobject, element);
		return new NonNullList<E>(Arrays.asList((E[]) aobject), element);
	}

	public static <E> NonNullList<E> getInstanceWith(E emptyElement, E... containingElement)
	{
		return new NonNullList<E>(Arrays.asList(containingElement), emptyElement);
	}

	protected NonNullList()
	{
		this(new ArrayList(), null);
	}

	protected NonNullList(List<E> list, @Nullable E emptyElements)
	{
		this.list = list;
		this.emptyElement = emptyElements;
	}

	@Override
	@Nonnull
	public E get(int ind)
	{
		return this.list.get(ind);
	}

	@Override
	public E set(int ind, E element)
	{
		Validate.notNull(element);
		return this.list.set(ind, element);
	}

	@Override
	public void add(int ind, E element)
	{
		Validate.notNull(element);
		this.list.add(ind, element);
	}

	@Override
	public E remove(int ind)
	{
		return this.list.remove(ind);
	}

	@Override
	public int size()
	{
		return this.list.size();
	}

	@Override
	public void clear()
	{
		if (this.emptyElement == null)
		{
			super.clear();
		}
		else
		{
			for (int i = 0; i < this.size(); ++i)
			{
				this.set(i, this.emptyElement);
			}
		}
	}
}
