package simpletweaker.integration.expansion;

import com.asx.lbm.common.PotionHandler;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.potion.PotionEffect;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import static com.asx.lbm.common.capabilities.IBleedableCapability.Provider.CAPABILITY;

@ModOnly("lbm")
@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
public class IPlayerExpansionLBM {

    @ZenGetter("bloodAmount")
    @ZenMethod
    public static int getBloodAmount(IPlayer iPlayer) {
        return CraftTweakerMC.getPlayer(iPlayer).getCapability(CAPABILITY, null).getBloodCount();
    }

    @ZenSetter("bloodAmount")
    @ZenMethod
    public static void setBloodAmount(IPlayer iPlayer, int blood) {
        CraftTweakerMC.getPlayer(iPlayer).getCapability(CAPABILITY, null).setBloodCount(blood);
    }

    @ZenGetter("maxBloodAmount")
    @ZenMethod
    public static int getMaxBloodAmount(IPlayer iPlayer) {
        return CraftTweakerMC.getPlayer(iPlayer).getCapability(CAPABILITY, null).getMaxBloodCount();
    }

    @ZenGetter("heartRate")
    @ZenMethod
    public static int getHeartRate(IPlayer iPlayer) {
        return CraftTweakerMC.getPlayer(iPlayer).getCapability(CAPABILITY, null).getHeartRate();
    }

    @ZenSetter("heartRate")
    @ZenMethod
    public static void setHeartRate(IPlayer iPlayer, int rate) {
        CraftTweakerMC.getPlayer(iPlayer).getCapability(CAPABILITY, null).setHeartRate(rate);
    }

    @ZenGetter("bodyMass")
    @ZenMethod
    public static double getBodyMass(IPlayer iPlayer) {
        return CraftTweakerMC.getPlayer(iPlayer).getCapability(CAPABILITY, null).getBodyMass();
    }

    @ZenSetter("bodyMass")
    @ZenMethod
    public static void setBodyMass(IPlayer iPlayer, double mass) {
        CraftTweakerMC.getPlayer(iPlayer).getCapability(CAPABILITY, null).setBodyMass(mass);
    }

    @ZenGetter
    @ZenMethod
    public static boolean isHeavyBleed(IPlayer iPlayer) {
        return CraftTweakerMC.getPlayer(iPlayer).getActivePotionEffects().contains(new PotionEffect(PotionHandler.HEAVY_BLEED));
    }

    @ZenGetter
    @ZenMethod
    public static boolean isLightBleed(IPlayer iPlayer) {
        return CraftTweakerMC.getPlayer(iPlayer).getActivePotionEffects().contains(new PotionEffect(PotionHandler.LIGHT_BLEED));
    }

}
