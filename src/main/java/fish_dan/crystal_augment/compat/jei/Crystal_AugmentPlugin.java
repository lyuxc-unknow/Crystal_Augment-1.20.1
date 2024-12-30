package fish_dan.crystal_augment.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import fish_dan.crystal_augment.Crystal_Augment;
import fish_dan.crystal_augment.client.screens.DynamoFrostScreen;
import fish_dan.crystal_augment.client.screens.machine.*;
import fish_dan.crystal_augment.compat.jei.category.*;
import fish_dan.crystal_augment.init.Crystal_AugmentBlocks;
import fish_dan.crystal_augment.init.Crystal_AugmentRecipeTypes;
import fish_dan.crystal_augment.recipe.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class Crystal_AugmentPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(Crystal_Augment.MOD_ID, "extra");
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        RecipeManager recipeManager = getRecipeManager();
        if (recipeManager == null) {
            // TODO: Log an error.
            return;
        }

        registration.addRecipes(COLD_FUEL_TYPE, recipeManager.getAllRecipesFor(Crystal_AugmentRecipeTypes.COLD_FUEL.get()));
        registration.addRecipes(ADVANCED_REFINERY_TYPE, recipeManager.getAllRecipesFor(Crystal_AugmentRecipeTypes.ADVANCED_REFINERY.get()));
        registration.addRecipes(NITRATIC_IGNITER_TYPE, recipeManager.getAllRecipesFor(Crystal_AugmentRecipeTypes.NITRATIC_IGNITER.get()));
        registration.addRecipes(NITRATIC_IGNITER_CATALYST_TYPE, recipeManager.getAllRecipesFor(Crystal_AugmentRecipeTypes.NITRATIC_IGNITER_CATALYST.get()));
        registration.addRecipes(FLUID_MIXER_TYPE, recipeManager.getAllRecipesFor(Crystal_AugmentRecipeTypes.FLUID_MIXER.get()));
        registration.addRecipes(COMPONENT_ASSEMBLY_TYPE, recipeManager.getAllRecipesFor(Crystal_AugmentRecipeTypes.COMPONENT_ASSEMBLY.get()));
        registration.addRecipes(ENDOTHERMIC_DEHYDRATOR_TYPE, recipeManager.getAllRecipesFor(Crystal_AugmentRecipeTypes.ENDOTHERMIC_DEHYDRATOR.get()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ColdFuelCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(Crystal_AugmentBlocks.DYNAMO_COLD.get()), COLD_FUEL_TYPE));
        registration.addRecipeCategories(new AdvancedRefineryRecipeCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(Crystal_AugmentBlocks.ADVANCED_REFINERY.get()), ADVANCED_REFINERY_TYPE));
        registration.addRecipeCategories(new NitraticIgniterRecipeCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(Crystal_AugmentBlocks.NITRATIC_IGNITER.get()), NITRATIC_IGNITER_TYPE));
        registration.addRecipeCategories(new NitraticIgniterCatalystCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(Crystal_AugmentBlocks.NITRATIC_IGNITER.get()), NITRATIC_IGNITER_CATALYST_TYPE));
        registration.addRecipeCategories(new FluidMixerRecipeCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(Crystal_AugmentBlocks.FLUID_MIXER.get()), FLUID_MIXER_TYPE));
        registration.addRecipeCategories(new ComponentAssemblyRecipeCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(Crystal_AugmentBlocks.COMPONENT_ASSEMBLY.get()), COMPONENT_ASSEMBLY_TYPE));
        registration.addRecipeCategories(new EndothermicDehydratorRecipeCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(Crystal_AugmentBlocks.ENDOTHERMIC_DEHYDRATOR.get()), ENDOTHERMIC_DEHYDRATOR_TYPE));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        int progressY = 35;
        int progressW = 24;
        int progressH = 16;

        registration.addRecipeClickArea(DynamoFrostScreen.class, 80, progressY, progressH, progressH, COLD_FUEL_TYPE);
        registration.addRecipeClickArea(MachineAdvancedRefineryScreen.class, 65, progressY, progressW, progressH, ADVANCED_REFINERY_TYPE);
        registration.addRecipeClickArea(MachineNitraticIgniterScreen.class, 94, progressY, progressW, progressH, NITRATIC_IGNITER_TYPE);
        registration.addRecipeClickArea(MachineFluidMixerScreen.class, 80, progressY, progressW, progressH, FLUID_MIXER_TYPE);
        registration.addRecipeClickArea(MachineComponentAssemblyScreen.class, 92, progressY, progressW, progressH, COMPONENT_ASSEMBLY_TYPE);
        registration.addRecipeClickArea(MachineEndothermicDehydratorScreen.class, 88, progressY, progressW, progressH, ENDOTHERMIC_DEHYDRATOR_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Crystal_AugmentBlocks.DYNAMO_COLD.get()), COLD_FUEL_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Crystal_AugmentBlocks.ADVANCED_REFINERY.get()), ADVANCED_REFINERY_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Crystal_AugmentBlocks.NITRATIC_IGNITER.get()), NITRATIC_IGNITER_TYPE, NITRATIC_IGNITER_CATALYST_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Crystal_AugmentBlocks.FLUID_MIXER.get()), FLUID_MIXER_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Crystal_AugmentBlocks.COMPONENT_ASSEMBLY.get()), COMPONENT_ASSEMBLY_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Crystal_AugmentBlocks.ENDOTHERMIC_DEHYDRATOR.get()), ENDOTHERMIC_DEHYDRATOR_TYPE);
    }

    private RecipeManager getRecipeManager() {

        RecipeManager recipeManager = null;
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            recipeManager = level.getRecipeManager();
        }
        return recipeManager;
    }

    public static final RecipeType<ColdFuel> COLD_FUEL_TYPE = new RecipeType<>(Crystal_AugmentRecipeTypes.COLD_FUEL.getId(), ColdFuel.class);
    public static final RecipeType<FluidMixerRecipe> FLUID_MIXER_TYPE = new RecipeType<>(Crystal_AugmentRecipeTypes.FLUID_MIXER.getId(), FluidMixerRecipe.class);
    public static final RecipeType<AdvancedRefineryRecipe> ADVANCED_REFINERY_TYPE = new RecipeType<>(Crystal_AugmentRecipeTypes.ADVANCED_REFINERY.getId(), AdvancedRefineryRecipe.class);
    public static final RecipeType<NitraticIgniterRecipe> NITRATIC_IGNITER_TYPE = new RecipeType<>(Crystal_AugmentRecipeTypes.NITRATIC_IGNITER.getId(), NitraticIgniterRecipe.class);
    public static final RecipeType<NitraticIgniterCatalyst> NITRATIC_IGNITER_CATALYST_TYPE = new RecipeType<>(Crystal_AugmentRecipeTypes.NITRATIC_IGNITER_CATALYST.getId(), NitraticIgniterCatalyst.class);
    public static final RecipeType<ComponentAssemblyRecipe> COMPONENT_ASSEMBLY_TYPE = new RecipeType<>(Crystal_AugmentRecipeTypes.COMPONENT_ASSEMBLY.getId(), ComponentAssemblyRecipe.class);
    public static final RecipeType<EndothermicDehydratorRecipe> ENDOTHERMIC_DEHYDRATOR_TYPE = new RecipeType<>(Crystal_AugmentRecipeTypes.ENDOTHERMIC_DEHYDRATOR.getId(), EndothermicDehydratorRecipe.class);
}
