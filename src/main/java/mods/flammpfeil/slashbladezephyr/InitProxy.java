package mods.flammpfeil.slashbladezephyr;

import cpw.mods.fml.common.SidedProxy;

public class InitProxy {
	@SidedProxy(clientSide = "mods.flammpfeil.slashbladezephyr.InitProxyClient", serverSide = "mods.flammpfeil.slashbladezephyr.InitProxy")
	public static InitProxy proxy;


	public void initializeItemRenderer() {}

}
