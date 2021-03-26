package net.planar_artifice.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.planar_artifice.blocks.AuraBasinBlockEntity;

import java.util.List;

public class ChargedItem extends Item{
	
	public ChargedItem(Settings settings){
		super(settings);
	}
	
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context){
		tooltip.add(new TranslatableText("item.planar_artifice.charge_level", stack.getOrCreateTag().getInt(AuraBasinBlockEntity.CHARGE_TAG)).styled(style -> style.withColor(Formatting.GRAY)));
	}
}