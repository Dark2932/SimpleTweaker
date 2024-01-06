package simpletweaker.integration.util;

import com.charles445.simpledifficulty.api.SDPotions;
import com.charles445.simpledifficulty.config.ModConfig;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import simpletweaker.util.STPlayerUtil;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("simpledifficulty")
@ZenClass("mods.simpletweaker.util.ThirstUtil")
@ZenRegister
public class CTThirstUtil {

    @ZenMethod
    public static void takeDrink(IPlayer iPlayer, int thirst, float saturation, @Optional boolean hasSound) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        STPlayerUtil.takeDrink(player, thirst, saturation, hasSound);
    }

    @ZenMethod
    public static void takeDrinkWithThirsty(IPlayer iPlayer, int thirst, float saturation, float thirstyChance, int thirstyDuration, @Optional boolean hasSound) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        if (STPlayerUtil.takeDrink(player, thirst, saturation, hasSound) && player.world.rand.nextFloat() < thirstyChance) {
            player.addPotionEffect(new PotionEffect(SDPotions.thirsty, thirstyDuration));
        }
    }

    @ZenMethod
    public static void takeDrinkWithParasites(IPlayer iPlayer, int thirst, float saturation, @Optional boolean hasSound, @Optional float parasitesChance, @Optional int parasitesDuration, @Optional(valueBoolean = true) boolean useConfigParasites) {
        EntityPlayer player = CraftTweakerMC.getPlayer(iPlayer);
        if (STPlayerUtil.takeDrink(player, thirst, saturation, hasSound)) {
            if (useConfigParasites) {
                if (ModConfig.server.thirst.thirstParasites && player.world.rand.nextDouble() < ModConfig.server.thirst.thirstParasitesChance) {
                    player.addPotionEffect(new PotionEffect(SDPotions.parasites, ModConfig.server.thirst.thirstParasitesDuration));
                }
            } else if (player.world.rand.nextFloat() < parasitesChance) {
                player.addPotionEffect(new PotionEffect(SDPotions.parasites, parasitesDuration));
            }
        }
    }

}
