package simpletweaker.integration.event;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("simpledifficulty")
@ZenExpansion("crafttweaker.events.IEventManager")
@ZenRegister
public class IEventManagerExpansion {

    public static final EventList<CTPlayerTakeDrinkEvent> elPlayerTakeDrink = new EventList<>();

    @ZenMethod
    public static IEventHandle onPlayerTakeDrink(IEventManager manager, IEventHandler<CTPlayerTakeDrinkEvent> ev) {
        return elPlayerTakeDrink.add(ev);
    }

    public static void handlePlayerTakeDrinkEvent(EntityPlayer player, int thirst, float saturation, float thirstyChance, IBlockPos pos, IItemStack item, String type) {
        if (IEventManagerExpansion.elPlayerTakeDrink.hasHandlers()) {
            IEventManagerExpansion.elPlayerTakeDrink.publish(new CTPlayerTakeDrinkEvent(player, thirst, saturation, thirstyChance, pos, item, type));
        }
    }

}