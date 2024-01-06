package simpletweaker.integration.expansion;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import simpletweaker.util.STPlayerUtil;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ModOnly("simpledifficulty")
@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
public class IPlayerExpansionSD {

    @ZenGetter("temperature")
    @ZenMethod
    public static int getTemperature(IPlayer iPlayer) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        return STPlayerUtil.getTemperatureData(player).getTemperatureLevel();
    }

    @ZenSetter("temperature")
    @ZenMethod
    public static void setTemperature(IPlayer iPlayer, int temperatureLevel) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        STPlayerUtil.getTemperatureData(player).setTemperatureLevel(temperatureLevel);
    }

    @ZenGetter("thirst")
    @ZenMethod
    public static int getThirst(IPlayer iPlayer) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        return STPlayerUtil.getThirstData(player).getThirstLevel();
    }

    @ZenSetter("thirst")
    @ZenMethod
    public static void setThirst(IPlayer iPlayer, int thirstLevel) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        STPlayerUtil.getThirstData(player).setThirstLevel(thirstLevel);
    }

    @ZenGetter("thirstSaturation")
    @ZenMethod
    public static float getThirstSaturation(IPlayer iPlayer) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        return STPlayerUtil.getThirstData(player).getThirstSaturation();
    }

    @ZenSetter("thirstSaturation")
    @ZenMethod
    public static void setThirstSaturation(IPlayer iPlayer, float saturation) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        STPlayerUtil.getThirstData(player).setThirstSaturation(saturation);
    }

    @ZenGetter
    @ZenMethod
    public static boolean isThirsty(IPlayer iPlayer) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        return STPlayerUtil.getThirstData(player).isThirsty();
    }

}
