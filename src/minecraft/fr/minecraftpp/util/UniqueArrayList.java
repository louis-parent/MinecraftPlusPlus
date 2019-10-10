package fr.minecraftpp.util;

import java.util.ArrayList;
import java.util.Collection;

public class UniqueArrayList<T> extends ArrayList<T>
{
	@Override
	public boolean add(T e)
	{
		if(!this.contains(e))
		{
			return super.add(e);
		}
		else
		{
			return false;
		}
	}
	@Override
	public void add(int index, T e)
	{
		if(!this.contains(e))
		{
			super.add(index, e);
		}
	}
	
	@Override
	public boolean addAll(Collection<? extends T> collection)
	{
		boolean addedAllElements = true;
		
		for(T element : collection)
		{
			addedAllElements &= this.add(element);
		}
		
		return addedAllElements;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> collection)
	{		
		for(T element : collection)
		{
			this.add(index, element);
		}
		
		return collection.size() > 0;
	}
}
