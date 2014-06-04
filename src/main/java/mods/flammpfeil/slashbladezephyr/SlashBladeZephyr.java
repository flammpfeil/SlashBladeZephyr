package mods.flammpfeil.slashbladezephyr;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

@Mod(name=SlashBladeZephyr.modname, modid=SlashBladeZephyr.modid, version="1.7.2 r1.2", dependencies = "required-after:flammpfeil.slashblade;required-after:Thaumcraft")
public class SlashBladeZephyr implements IRepairable{

	public static final String modname = "SlashBladeZephyr";
	public static final String modid = "flammpfeil.slashblade.zephyr";

    public static Item bladeOfZephyr;
    public static Item swordOfZephyr = null;

	public static Configuration mainConfiguration;

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt){
		/*
            mainConfiguration = new Configuration(evt.getSuggestedConfigurationFile());

            try{
                mainConfiguration.load();
            }
            finally
            {
                mainConfiguration.save();
            }
        */



        bladeOfZephyr = (ItemSlashBladeZephyr)(new ItemSlashBladeZephyr(ThaumcraftApi.toolMatElemental, 4 + ThaumcraftApi.toolMatElemental.getDamageVsEntity()))
                .setMaxDamage(40)
                .setUnlocalizedName("flammpfeil.slashblade.zephyr")
                .setTextureName("flammpfeil.slashblade:proudsoul")
                .setCreativeTab(SlashBlade.tab);
        GameRegistry.registerItem(bladeOfZephyr, "zephyr");


        String zephyrName = "flammpfeil.slashblade.zephyr";
        {
            ItemStack customblade = new ItemStack(bladeOfZephyr,1,0);
            NBTTagCompound tag = new NBTTagCompound();
            customblade.setTagCompound(tag);

            customblade.addEnchantment(Enchantment.power,5);
            customblade.addEnchantment(Enchantment.featherFalling,5);
            ItemSlashBladeNamed.IsDefaultBewitched.set(tag,true);
            ItemSlashBladeNamed.CustomMaxDamage.set(tag, 70);
            ItemSlashBlade.TextureName.set(tag,"zephyr/tex");
            ItemSlashBlade.ModelName.set(tag,"zephyr/model");
            ItemSlashBlade.SpecialAttackType.set(tag, 1);
            ItemSlashBlade.StandbyRenderType.set(tag, 3);

            GameRegistry.registerCustomItemStack(zephyrName, customblade);
            ItemSlashBladeNamed.NamedBlades.add(String.format("%s:%s",modid,zephyrName));
        }

        String windEater = "flammpfeil.slashblade.windeater";
        {
            ItemStack customblade = new ItemStack(bladeOfZephyr,1,0);
            NBTTagCompound tag = new NBTTagCompound();
            customblade.setTagCompound(tag);

            ItemSlashBladeNamed.CustomMaxDamage.set(tag, 70);
            ItemSlashBlade.TextureName.set(tag,"zephyr/tex");
            ItemSlashBlade.ModelName.set(tag,"zephyr/model");
            ItemSlashBlade.SpecialAttackType.set(tag, 1);
            ItemSlashBlade.StandbyRenderType.set(tag, 3);
            ItemSlashBlade.IsSealed.set(tag,true);
            ItemSlashBladeNamed.TrueItemName.set(tag,String.format("%s:%s",modid,zephyrName));
            ItemSlashBladeNamed.CurrentItemName.set(tag,windEater);

            GameRegistry.registerCustomItemStack(windEater, customblade);
            ItemSlashBladeNamed.NamedBlades.add(String.format("%s:%s",modid,windEater));
        }
    }
    @EventHandler
    public void init(FMLInitializationEvent evt){
        swordOfZephyr = GameRegistry.findItem("Thaumcraft","ItemSwordElemental");

        InitProxy.proxy.initializeItemRenderer();
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent evt)
    {

        ItemStack soul = GameRegistry.findItemStack(SlashBlade.modid,SlashBlade.ProudSoulStr,1);
        ThaumcraftApi.registerObjectTag(soul, new AspectList()
                .add(Aspect.METAL, 1)
                .add(Aspect.SOUL, 2)
                .add(Aspect.FIRE, 2)
        );

        ItemStack tinySoul = GameRegistry.findItemStack(SlashBlade.modid,SlashBlade.TinyBladeSoulStr,1);
        ThaumcraftApi.registerObjectTag(tinySoul, new AspectList()
                .add(Aspect.METAL, 1)
                .add(Aspect.SOUL, 1)
                .add(Aspect.FIRE, 1)
        );

        ItemStack ingot = GameRegistry.findItemStack(SlashBlade.modid,SlashBlade.IngotBladeSoulStr,1);
        ThaumcraftApi.registerObjectTag(ingot, new AspectList()
                .add(Aspect.METAL, 3)
                .add(Aspect.SOUL, 8)
        );

        ItemStack sphere = GameRegistry.findItemStack(SlashBlade.modid,SlashBlade.SphereBladeSoulStr,1);
        ThaumcraftApi.registerObjectTag(sphere, new AspectList()
                .add(Aspect.METAL, 2)
                .add(Aspect.SOUL, 8)
                .add(Aspect.CRYSTAL, 1)
        );

        {
            //================================================================================================================
            ItemStack blade = SlashBlade.getCustomBlade(modid,"flammpfeil.slashblade.windeater");

            Item crystal = GameRegistry.findItem("Thaumcraft","blockCrystal");
                    //"ElementalSword",

            InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe("BLADEOFTHEZEPHYR",
                blade,
                1,
                new AspectList().add(Aspect.AIR, 8).add(Aspect.WEAPON, 8).add(Aspect.ENERGY, 8),
                new ItemStack(swordOfZephyr),
                new ItemStack[]{
                          new ItemStack(crystal, 1, 0)
                        , soul
                        , new ItemStack(Blocks.diamond_ore)
                        , soul
                        , sphere
                        , soul
                        , new ItemStack(Items.bone, 1, 0)
                        , soul });

            //================================================================================================================
            ItemStack reqiredBlade = blade.copy();
            {
                reqiredBlade.setItemDamage(OreDictionary.WILDCARD_VALUE);
                NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(reqiredBlade);
                ItemSlashBlade.KillCount.set(tag,100);
                String name="flammpfeil.slashblade.zephyr.reqired";
                GameRegistry.registerCustomItemStack(name,reqiredBlade);
                ItemSlashBladeNamed.NamedBlades.add(String.format("%s:%s",modid,name));
            }

            ItemStack trueBlade = SlashBlade.getCustomBlade(modid,"flammpfeil.slashblade.zephyr");
            IRecipe wakeup = new RecipeAwakeBlade(trueBlade,
                    reqiredBlade,
                    " X ",
                    "XBX",
                    " X ",
                    'X',soul,
                    'B',reqiredBlade);
            GameRegistry.addRecipe(wakeup);//================================================================================================================
            ItemStack reqiredBladeBroken = reqiredBlade.copy();
            {
                NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(reqiredBladeBroken);
                ItemSlashBlade.IsBroken.set(tag,true);
            }

            IRecipe wakeup2 = new RecipeAwakeBlade(trueBlade,
                    reqiredBladeBroken,
                    " X ",
                    "XBX",
                    " X ",
                    'X',soul,
                    'B',reqiredBladeBroken);
            GameRegistry.addRecipe(wakeup2);
            //================================================================================================================

            new ResearchItem("BLADEOFTHEZEPHYR", "ARTIFICE",
                    new AspectList().add(Aspect.WEAPON, 5).add(Aspect.AIR, 5).add(Aspect.ENERGY, 3),
                    -10, 5, 1, blade)
                .setPages(new ResearchPage("tc.research_page.BLADEOFTHEZEPHYR.1"),
                        new ResearchPage(recipe),
                        new ResearchPage(wakeup),
                        new ResearchPage(wakeup2))
                .setParents(new String[]{"ELEMENTALSWORD"})
                .setHidden()
                .setItemTriggers(soul, tinySoul, ingot, sphere)
                .registerResearchItem();


        }


    }
}
