package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

public class GuiFlatPresets extends GuiScreen
{
	private static final List<GuiFlatPresets.LayerItem> FLAT_WORLD_PRESETS = Lists.<GuiFlatPresets.LayerItem>newArrayList();

	/** The parent GUI */
	private final GuiCreateFlatWorld parentScreen;
	private String presetsTitle;
	private String presetsShare;
	private String listText;
	private GuiFlatPresets.ListSlot list;
	private GuiButton btnSelect;
	private GuiTextField export;

	public GuiFlatPresets(GuiCreateFlatWorld p_i46318_1_)
	{
		this.parentScreen = p_i46318_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called
	 * when the GUI is displayed and when the window resizes, the buttonList is
	 * cleared beforehand.
	 */
	@Override
	public void initGui()
	{
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.presetsTitle = I18n.format("createWorld.customize.presets.title");
		this.presetsShare = I18n.format("createWorld.customize.presets.share");
		this.listText = I18n.format("createWorld.customize.presets.list");
		this.export = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
		this.list = new GuiFlatPresets.ListSlot();
		this.export.setMaxStringLength(1230);
		this.export.setText(this.parentScreen.getPreset());
		this.btnSelect = this.addButton(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select")));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
		this.updateButtonValidity();
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();
		this.list.handleMouseInput();
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		this.export.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (!this.export.textboxKeyTyped(typedChar, keyCode))
		{
			super.keyTyped(typedChar, keyCode);
		}
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed
	 * for buttons)
	 */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == 0 && this.hasValidSelection())
		{
			this.parentScreen.setPreset(this.export.getText());
			this.mc.displayGuiScreen(this.parentScreen);
		}
		else if (button.id == 1)
		{
			this.mc.displayGuiScreen(this.parentScreen);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		this.list.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(this.fontRendererObj, this.presetsTitle, this.width / 2, 8, 16777215);
		this.drawString(this.fontRendererObj, this.presetsShare, 50, 30, 10526880);
		this.drawString(this.fontRendererObj, this.listText, 50, 70, 10526880);
		this.export.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen()
	{
		this.export.updateCursorCounter();
		super.updateScreen();
	}

	public void updateButtonValidity()
	{
		this.btnSelect.enabled = this.hasValidSelection();
	}

	private boolean hasValidSelection()
	{
		return this.list.selected > -1 && this.list.selected < FLAT_WORLD_PRESETS.size() || this.export.getText().length() > 1;
	}

	private static void registerPreset(String name, Item icon, Biome biome, List<String> features, FlatLayerInfo... layers)
	{
		registerPreset(name, icon, 0, biome, features, layers);
	}

	private static void registerPreset(String name, Item icon, int iconMetadata, Biome biome, List<String> features, FlatLayerInfo... layers)
	{
		FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();

		for (int i = layers.length - 1; i >= 0; --i)
		{
			flatgeneratorinfo.getFlatLayers().add(layers[i]);
		}

		flatgeneratorinfo.setBiome(Biome.getIdForBiome(biome));
		flatgeneratorinfo.updateLayers();

		for (String s : features)
		{
			flatgeneratorinfo.getWorldFeatures().put(s, Maps.newHashMap());
		}

		FLAT_WORLD_PRESETS.add(new GuiFlatPresets.LayerItem(icon, iconMetadata, name, flatgeneratorinfo.toString()));
	}

	static
	{
		registerPreset(I18n.format("createWorld.customize.preset.classic_flat"), Item.getItemFromBlock(Blocks.getBlock(Blocks.GRASS)), Biomes.getBiome(Biomes.PLAINS), Arrays.asList("village"), new FlatLayerInfo(1, Blocks.getBlock(Blocks.GRASS)), new FlatLayerInfo(2, Blocks.getBlock(Blocks.DIRT)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.BEDROCK)));
		registerPreset(I18n.format("createWorld.customize.preset.tunnelers_dream"), Item.getItemFromBlock(Blocks.getBlock(Blocks.STONE)), Biomes.getBiome(Biomes.EXTREME_HILLS), Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.getBlock(Blocks.GRASS)), new FlatLayerInfo(5, Blocks.getBlock(Blocks.DIRT)), new FlatLayerInfo(230, Blocks.getBlock(Blocks.STONE)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.BEDROCK)));
		registerPreset(I18n.format("createWorld.customize.preset.water_world"), Items.getItem(Items.WATER_BUCKET), Biomes.getBiome(Biomes.DEEP_OCEAN), Arrays.asList("biome_1", "oceanmonument"), new FlatLayerInfo(90, Blocks.getBlock(Blocks.WATER)), new FlatLayerInfo(5, Blocks.getBlock(Blocks.SAND)), new FlatLayerInfo(5, Blocks.getBlock(Blocks.DIRT)), new FlatLayerInfo(5, Blocks.getBlock(Blocks.STONE)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.BEDROCK)));
		registerPreset(I18n.format("createWorld.customize.preset.overworld"), Item.getItemFromBlock(Blocks.getBlock(Blocks.TALLGRASS)), BlockTallGrass.EnumType.GRASS.getMeta(), Biomes.getBiome(Biomes.PLAINS), Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.getBlock(Blocks.GRASS)), new FlatLayerInfo(3, Blocks.getBlock(Blocks.DIRT)), new FlatLayerInfo(59, Blocks.getBlock(Blocks.STONE)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.BEDROCK)));
		registerPreset(I18n.format("createWorld.customize.preset.snowy_kingdom"), Item.getItemFromBlock(Blocks.getBlock(Blocks.SNOW_LAYER)), Biomes.getBiome(Biomes.ICE_PLAINS), Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.getBlock(Blocks.SNOW_LAYER)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.GRASS)), new FlatLayerInfo(3, Blocks.getBlock(Blocks.DIRT)), new FlatLayerInfo(59, Blocks.getBlock(Blocks.STONE)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.BEDROCK)));
		registerPreset(I18n.format("createWorld.customize.preset.bottomless_pit"), Items.getItem(Items.FEATHER), Biomes.getBiome(Biomes.PLAINS), Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.getBlock(Blocks.GRASS)), new FlatLayerInfo(3, Blocks.getBlock(Blocks.DIRT)), new FlatLayerInfo(2, Blocks.getBlock(Blocks.COBBLESTONE)));
		registerPreset(I18n.format("createWorld.customize.preset.desert"), Item.getItemFromBlock(Blocks.getBlock(Blocks.SAND)), Biomes.getBiome(Biomes.DESERT), Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.getBlock(Blocks.SAND)), new FlatLayerInfo(52, Blocks.getBlock(Blocks.SANDSTONE)), new FlatLayerInfo(3, Blocks.getBlock(Blocks.STONE)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.BEDROCK)));
		registerPreset(I18n.format("createWorld.customize.preset.redstone_ready"), Items.getItem(Items.REDSTONE), Biomes.getBiome(Biomes.DESERT), Collections.emptyList(), new FlatLayerInfo(52, Blocks.getBlock(Blocks.SANDSTONE)), new FlatLayerInfo(3, Blocks.getBlock(Blocks.STONE)), new FlatLayerInfo(1, Blocks.getBlock(Blocks.BEDROCK)));
		registerPreset(I18n.format("createWorld.customize.preset.the_void"), Item.getItemFromBlock(Blocks.getBlock(Blocks.BARRIER)), Biomes.getBiome(Biomes.VOID), Arrays.asList("decoration"), new FlatLayerInfo(1, Blocks.getBlock(Blocks.AIR)));
	}

	static class LayerItem
	{
		public Item icon;
		public int iconMetadata;
		public String name;
		public String generatorInfo;

		public LayerItem(Item iconIn, int iconMetadataIn, String nameIn, String generatorInfoIn)
		{
			this.icon = iconIn;
			this.iconMetadata = iconMetadataIn;
			this.name = nameIn;
			this.generatorInfo = generatorInfoIn;
		}
	}

	class ListSlot extends GuiSlot
	{
		public int selected = -1;

		public ListSlot()
		{
			super(GuiFlatPresets.this.mc, GuiFlatPresets.this.width, GuiFlatPresets.this.height, 80, GuiFlatPresets.this.height - 37, 24);
		}

		private void renderIcon(int p_178054_1_, int p_178054_2_, Item icon, int iconMetadata)
		{
			this.blitSlotBg(p_178054_1_ + 1, p_178054_2_ + 1);
			GlStateManager.enableRescaleNormal();
			RenderHelper.enableGUIStandardItemLighting();
			GuiFlatPresets.this.itemRender.renderItemIntoGUI(new ItemStack(icon, 1, icon.getHasSubtypes() ? iconMetadata : 0), p_178054_1_ + 2, p_178054_2_ + 2);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();
		}

		private void blitSlotBg(int p_148173_1_, int p_148173_2_)
		{
			this.blitSlotIcon(p_148173_1_, p_148173_2_, 0, 0);
		}

		private void blitSlotIcon(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(Gui.STAT_ICONS);
			float f = 0.0078125F;
			float f1 = 0.0078125F;
			int i = 18;
			int j = 18;
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos(p_148171_1_ + 0, p_148171_2_ + 18, GuiFlatPresets.this.zLevel).tex((p_148171_3_ + 0) * 0.0078125F, (p_148171_4_ + 18) * 0.0078125F).endVertex();
			bufferbuilder.pos(p_148171_1_ + 18, p_148171_2_ + 18, GuiFlatPresets.this.zLevel).tex((p_148171_3_ + 18) * 0.0078125F, (p_148171_4_ + 18) * 0.0078125F).endVertex();
			bufferbuilder.pos(p_148171_1_ + 18, p_148171_2_ + 0, GuiFlatPresets.this.zLevel).tex((p_148171_3_ + 18) * 0.0078125F, (p_148171_4_ + 0) * 0.0078125F).endVertex();
			bufferbuilder.pos(p_148171_1_ + 0, p_148171_2_ + 0, GuiFlatPresets.this.zLevel).tex((p_148171_3_ + 0) * 0.0078125F, (p_148171_4_ + 0) * 0.0078125F).endVertex();
			tessellator.draw();
		}

		@Override
		protected int getSize()
		{
			return GuiFlatPresets.FLAT_WORLD_PRESETS.size();
		}

		@Override
		protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
		{
			this.selected = slotIndex;
			GuiFlatPresets.this.updateButtonValidity();
			GuiFlatPresets.this.export.setText((GuiFlatPresets.FLAT_WORLD_PRESETS.get(GuiFlatPresets.this.list.selected)).generatorInfo);
		}

		@Override
		protected boolean isSelected(int slotIndex)
		{
			return slotIndex == this.selected;
		}

		@Override
		protected void drawBackground()
		{
		}

		@Override
		protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_)
		{
			GuiFlatPresets.LayerItem guiflatpresets$layeritem = GuiFlatPresets.FLAT_WORLD_PRESETS.get(p_192637_1_);
			this.renderIcon(p_192637_2_, p_192637_3_, guiflatpresets$layeritem.icon, guiflatpresets$layeritem.iconMetadata);
			GuiFlatPresets.this.fontRendererObj.drawString(guiflatpresets$layeritem.name, p_192637_2_ + 18 + 5, p_192637_3_ + 6, 16777215);
		}
	}
}
