package simpletweaker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import simpletweaker.integration.event.handler.PlayerTakeDrinkHandler;
import simpletweaker.network.PktPlayerTakeDrinkEvent;

@Mod(modid = "simpletweaker", name = "SimpleTweaker")
public class SimpleTweaker {

    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("simpletweaker");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerTakeDrinkHandler());
        CHANNEL.registerMessage(PktPlayerTakeDrinkEvent.Handler.class, PktPlayerTakeDrinkEvent.class, 0, Side.SERVER);
    }

}