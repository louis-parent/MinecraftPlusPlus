package net.minecraft.item.crafting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.crafting.GenericRecipe;
import fr.minecraftpp.crafting.RecipeBlock;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CraftingManager
{
    private static final Logger logger = LogManager.getLogger();
    private static int numberOfRecipes;
    public static final RegistryNamespaced<ResourceLocation, IRecipe> recipeMap = new RegistryNamespaced<ResourceLocation, IRecipe>();

    public static boolean buildAllRecipes()
    {
        try
        {
            registerRecipe("armordye", new RecipesArmorDyes());
            registerRecipe("bookcloning", new RecipeBookCloning());
            registerRecipe("mapcloning", new RecipesMapCloning());
            registerRecipe("mapextending", new RecipesMapExtending());
            registerRecipe("fireworks", new RecipeFireworks());
            registerRecipe("repairitem", new RecipeRepairItem());
            registerRecipe("tippedarrow", new RecipeTippedArrow());
            registerRecipe("bannerduplicate", new RecipesBanners.RecipeDuplicatePattern());
            registerRecipe("banneraddpattern", new RecipesBanners.RecipeAddPattern());
            registerRecipe("shielddecoration", new ShieldRecipes.Decoration());
            registerRecipe("shulkerboxcoloring", new ShulkerBoxRecipes.ShulkerBoxColoring());
            
            /**
             * MOD Minecraftpp
             */
            new RecipeBlock(Items.CLOCK, ModBlocks.SCENARITE_ORE);
            GenericRecipe.registerRecipes();
            
            return buildAllRecipesFromFiles();
        }
        catch (Throwable var1)
        {
            return false;
        }
    }

    private static boolean buildAllRecipesFromFiles()
    {
        FileSystem filesystem = null;
        Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        boolean flag1;

        try
        {
            URL url = CraftingManager.class.getResource("/assets/.mcassetsroot");

            if (url != null)
            {
                URI uri = url.toURI();
                Path path;

                if ("file".equals(uri.getScheme()))
                {
                    path = Paths.get(CraftingManager.class.getResource("/assets/minecraft/recipes").toURI());
                }
                else
                {
                    if (!"jar".equals(uri.getScheme()))
                    {
                        logger.error("Unsupported scheme " + uri + " trying to list all recipes");
                        boolean flag2 = false;
                        return flag2;
                    }

                    filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    path = filesystem.getPath("/assets/minecraft/recipes");
                }

                Iterator<Path> iterator = Files.walk(path).iterator();

                while (iterator.hasNext())
                {
                    Path path1 = iterator.next();

                    if ("json".equals(FilenameUtils.getExtension(path1.toString())))
                    {
                        Path path2 = path.relativize(path1);
                        String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
                        ResourceLocation resourcelocation = new ResourceLocation(s);
                        BufferedReader bufferedreader = null;

                        try
                        {
                            boolean flag;

                            try
                            {
                                bufferedreader = Files.newBufferedReader(path1);
                                registerRecipe(s, buildRecipe((JsonObject)JsonUtils.func_193839_a(gson, bufferedreader, JsonObject.class)));
                            }
                            catch (JsonParseException jsonparseexception)
                            {
                                logger.error("Parsing error loading recipe " + resourcelocation, (Throwable)jsonparseexception);
                                flag = false;
                                return flag;
                            }
                            catch (IOException ioexception)
                            {
                                logger.error("Couldn't read recipe " + resourcelocation + " from " + path1, (Throwable)ioexception);
                                flag = false;
                                return flag;
                            }
                        }
                        finally
                        {
                            IOUtils.closeQuietly((Reader)bufferedreader);
                        }
                    }
                }

                return true;
            }

            logger.error("Couldn't find .mcassetsroot");
            flag1 = false;
        }
        catch (IOException | URISyntaxException urisyntaxexception)
        {
            logger.error("Couldn't get a list of all recipe files", (Throwable)urisyntaxexception);
            flag1 = false;
            return flag1;
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)filesystem);
        }

        return flag1;
    }

    private static IRecipe buildRecipe(JsonObject jsonContent)
    {
        String s = JsonUtils.getString(jsonContent, "type");

        if ("crafting_shaped".equals(s))
        {
            return ShapedRecipes.buildRecipe(jsonContent);
        }
        else if ("crafting_shapeless".equals(s))
        {
            return ShapelessRecipes.buildRecipe(jsonContent);
        }
        else
        {
            throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
        }
    }

    public static void registerRecipe(String location, IRecipe recipe)
    {
        registerRecipeFromLocation(new ResourceLocation(location), recipe);
    }

    public static void registerRecipeFromLocation(ResourceLocation location, IRecipe recipe)
    {
        if (recipeMap.containsKey(location))
        {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + location);
        }
        else
        {
            recipeMap.register(numberOfRecipes++, location, recipe);
        }
    }

    /**
     * Retrieves an ItemStack that has multiple recipes for it.
     */
    public static ItemStack findItemStackOfMatchingRecipe(InventoryCrafting craftingMatrix, World world)
    {
        for (IRecipe irecipe : recipeMap)
        {
            if (irecipe.matches(craftingMatrix, world))
            {
                return irecipe.getCraftingResult(craftingMatrix);
            }
        }

        return ItemStack.EMPTY_ITEM_STACK;
    }

    @Nullable
    public static IRecipe findMatchingRecipe(InventoryCrafting craftingMatrix, World world)
    {
        for (IRecipe irecipe : recipeMap)
        {
            if (irecipe.matches(craftingMatrix, world))
            {
                return irecipe;
            }
        }

        return null;
    }

    public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftingMatrix, World world)
    {
        for (IRecipe irecipe : recipeMap)
        {
            if (irecipe.matches(craftingMatrix, world))
            {
                return irecipe.getRemainingItems(craftingMatrix);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>getInstanceFilledWith(craftingMatrix.getSizeInventory(), ItemStack.EMPTY_ITEM_STACK);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            nonnulllist.set(i, craftingMatrix.getStackInSlot(i));
        }

        return nonnulllist;
    }

    @Nullable
    public static IRecipe getRecipeByLocation(ResourceLocation location)
    {
        return recipeMap.getObject(location);
    }

    public static int getIdByRecipe(IRecipe recipe)
    {
        return recipeMap.getIDForObject(recipe);
    }

    @Nullable
    public static IRecipe getRecipeById(int id)
    {
        return recipeMap.getObjectById(id);
    }
}
