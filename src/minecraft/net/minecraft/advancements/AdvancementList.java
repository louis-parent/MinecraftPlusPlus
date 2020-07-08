package net.minecraft.advancements;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class AdvancementList
{
	private static final Logger LOGGER = LogManager.getLogger();
	private final Map<ResourceLocation, Advancement> advancementsList = Maps.<ResourceLocation, Advancement>newHashMap();
	private final Set<Advancement> field_192093_c = Sets.<Advancement>newLinkedHashSet();
	private final Set<Advancement> field_192094_d = Sets.<Advancement>newLinkedHashSet();
	private AdvancementList.Listener listener;

	private void func_192090_a(Advancement p_192090_1_)
	{
		for (Advancement advancement : p_192090_1_.func_192069_e())
		{
			this.func_192090_a(advancement);
		}

		LOGGER.info("Forgot about advancement " + p_192090_1_.func_192067_g());
		this.advancementsList.remove(p_192090_1_.func_192067_g());

		if (p_192090_1_.func_192070_b() == null)
		{
			this.field_192093_c.remove(p_192090_1_);

			if (this.listener != null)
			{
				this.listener.func_191928_b(p_192090_1_);
			}
		}
		else
		{
			this.field_192094_d.remove(p_192090_1_);

			if (this.listener != null)
			{
				this.listener.func_191929_d(p_192090_1_);
			}
		}
	}

	public void func_192085_a(Set<ResourceLocation> p_192085_1_)
	{
		for (ResourceLocation resourcelocation : p_192085_1_)
		{
			Advancement advancement = this.advancementsList.get(resourcelocation);

			if (advancement == null)
			{
				LOGGER.warn("Told to remove advancement " + resourcelocation + " but I don't know what that is");
			}
			else
			{
				this.func_192090_a(advancement);
			}
		}
	}

	public void func_192083_a(Map<ResourceLocation, Advancement.Builder> p_192083_1_)
	{
		Function<ResourceLocation, Advancement> function = Functions.<ResourceLocation, Advancement>forMap(this.advancementsList, null);
		label42:

		while (!p_192083_1_.isEmpty())
		{
			boolean flag = false;
			Iterator<Entry<ResourceLocation, Advancement.Builder>> iterator = p_192083_1_.entrySet().iterator();

			while (iterator.hasNext())
			{
				Entry<ResourceLocation, Advancement.Builder> entry = iterator.next();
				ResourceLocation resourcelocation = entry.getKey();
				Advancement.Builder advancement$builder = entry.getValue();

				if (advancement$builder.func_192058_a(function))
				{
					Advancement advancement = advancement$builder.func_192056_a(resourcelocation);
					this.advancementsList.put(resourcelocation, advancement);
					flag = true;
					iterator.remove();

					if (advancement.func_192070_b() == null)
					{
						this.field_192093_c.add(advancement);

						if (this.listener != null)
						{
							this.listener.func_191931_a(advancement);
						}
					}
					else
					{
						this.field_192094_d.add(advancement);

						if (this.listener != null)
						{
							this.listener.func_191932_c(advancement);
						}
					}
				}
			}

			if (!flag)
			{
				iterator = p_192083_1_.entrySet().iterator();

				while (true)
				{
					if (!iterator.hasNext())
					{
						break label42;
					}

					Entry<ResourceLocation, Advancement.Builder> entry1 = iterator.next();
					LOGGER.error("Couldn't load advancement " + entry1.getKey() + ": " + entry1.getValue());
				}
			}
		}

		LOGGER.info("Loaded " + this.advancementsList.size() + " advancements");
	}

	public void func_192087_a()
	{
		this.advancementsList.clear();
		this.field_192093_c.clear();
		this.field_192094_d.clear();

		if (this.listener != null)
		{
			this.listener.func_191930_a();
		}
	}

	public Iterable<Advancement> func_192088_b()
	{
		return this.field_192093_c;
	}

	public Iterable<Advancement> func_192089_c()
	{
		return this.advancementsList.values();
	}

	@Nullable
	public Advancement func_192084_a(ResourceLocation p_192084_1_)
	{
		return this.advancementsList.get(p_192084_1_);
	}

	public void func_192086_a(@Nullable AdvancementList.Listener p_192086_1_)
	{
		this.listener = p_192086_1_;

		if (p_192086_1_ != null)
		{
			for (Advancement advancement : this.field_192093_c)
			{
				p_192086_1_.func_191931_a(advancement);
			}

			for (Advancement advancement1 : this.field_192094_d)
			{
				p_192086_1_.func_191932_c(advancement1);
			}
		}
	}

	public interface Listener
	{
		void func_191931_a(Advancement p_191931_1_);

		void func_191928_b(Advancement p_191928_1_);

		void func_191932_c(Advancement p_191932_1_);

		void func_191929_d(Advancement p_191929_1_);

		void func_191930_a();
	}
}
