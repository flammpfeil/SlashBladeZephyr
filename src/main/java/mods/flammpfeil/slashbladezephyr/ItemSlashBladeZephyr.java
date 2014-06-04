package mods.flammpfeil.slashbladezephyr;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by Furia on 14/05/31.
 */
public class ItemSlashBladeZephyr extends ItemSlashBladeNamed {

    ToolMaterial material;

    public ItemSlashBladeZephyr(ToolMaterial par2EnumToolMaterial, float baseAttackModifiers) {
        super(par2EnumToolMaterial, baseAttackModifiers);
        material = par2EnumToolMaterial;
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        if(getSwordType(par1ItemStack).contains(SwordType.Bewitched))
            return EnumRarity.epic;
        else
            return EnumRarity.rare;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
                                     Entity entity) {

        boolean result = super.onLeftClickEntity(stack,player,entity);

        NBTTagCompound tag = getItemTagCompound(stack);
        EnumSet<SwordType> types = getSwordType(stack);
        if(!OnClick.get(tag)){ // onClick中は rightClickなので無視
            if(!types.contains(SwordType.Broken) && SlashBladeZephyr.swordOfZephyr != null){
                SlashBladeZephyr.swordOfZephyr.onLeftClickEntity(stack,player,entity);
            }
        }

        return result;
    }


    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        super.onUsingTick(stack,player,count);
        EnumSet<SwordType> types = getSwordType(stack);
        if(!types.contains(SwordType.Broken) && SlashBladeZephyr.swordOfZephyr != null){
            SlashBladeZephyr.swordOfZephyr.onUsingTick(stack.copy(), player, count);
        }
    }

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,List par3List) {}

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return material.func_150995_f() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
