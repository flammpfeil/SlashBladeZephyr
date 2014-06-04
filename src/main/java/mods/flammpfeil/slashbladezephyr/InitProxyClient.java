package mods.flammpfeil.slashbladezephyr;

import mods.flammpfeil.slashblade.*;
import net.minecraftforge.client.MinecraftForgeClient;

public class InitProxyClient extends InitProxy{
	@Override
	public void initializeItemRenderer() {
		ItemRendererBaseWeapon renderer = new ItemRendererBaseWeapon();
		MinecraftForgeClient.registerItemRenderer(SlashBladeZephyr.bladeOfZephyr, renderer);
	}
}
