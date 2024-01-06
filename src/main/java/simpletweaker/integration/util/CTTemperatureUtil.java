package simpletweaker.integration.util;

import com.charles445.simpledifficulty.api.temperature.TemperatureEnum;
import com.charles445.simpledifficulty.util.internal.TemperatureUtilInternal;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("simpledifficulty")
@ZenClass("mods.simpletweaker.util.TemperatureUtil")
@ZenRegister
public class CTTemperatureUtil {

    private static final TemperatureUtilInternal UTIL = new TemperatureUtilInternal();

    @ZenMethod
    public static int clampTemperature(int temp) {
        return UTIL.clampTemperature(temp);
    }

    @ZenMethod
    public static String getTemperatureState(int temp) {
        TemperatureEnum state = UTIL.getTemperatureEnum(temp);
        switch (state) {
            case HOT:
                return "hot";
            case COLD:
                return "cold";
            case NORMAL:
                return "normal";
            case BURNING:
                return "burning";
            case FREEZING:
                return "freezing";
            default:
                return "unknown";
        }
    }

    @ZenMethod
    public static void setArmorTemperatureTag(IItemStack iItemStack, float temp) {
        ItemStack stack = CraftTweakerMC.getItemStack(iItemStack);
        UTIL.setArmorTemperatureTag(stack, temp);
    }

    @ZenMethod
    public static float getArmorTemperatureTag(IItemStack iItemStack) {
        ItemStack stack = CraftTweakerMC.getItemStack(iItemStack);
        return UTIL.getArmorTemperatureTag(stack);
    }

    @ZenMethod
    public static void removeArmorTemperatureTag(IItemStack iItemStack) {
        ItemStack stack = CraftTweakerMC.getItemStack(iItemStack);
        UTIL.removeArmorTemperatureTag(stack);
    }

}
