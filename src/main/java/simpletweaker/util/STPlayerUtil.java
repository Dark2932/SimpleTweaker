package simpletweaker.util;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.config.QuickConfig;
import com.charles445.simpledifficulty.api.config.ServerConfig;
import com.charles445.simpledifficulty.api.config.ServerOptions;
import com.charles445.simpledifficulty.api.temperature.ITemperatureCapability;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.api.thirst.ThirstEnumBlockPos;
import com.charles445.simpledifficulty.api.thirst.ThirstUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class STPlayerUtil {

    public static boolean takeDrink(EntityPlayer player, int thirst, float saturation, boolean hasSound) {
        if (QuickConfig.isThirstEnabled()) {
            ThirstUtil.takeDrink(player, thirst, saturation);
            if (hasSound) {
                STPlayerUtil.sendSoundPacketToPlayer("entity.generic.drink", 0.5f, 1.0f, player, player.getPosition());
            }
            return true;
        }
        return false;
    }

    public static boolean takeDrink(EntityPlayer player, ThirstEnum thirstEnum, boolean hasSound) {
        return takeDrink(player, thirstEnum.getThirst(), thirstEnum.getSaturation(), hasSound);
    }

    public static ThirstEnumBlockPos traceWaterToDrink(EntityPlayer player) {
        if (player.getHeldItemMainhand().isEmpty()) {
            if (getThirstData(player).isThirsty()) {
                ThirstEnumBlockPos traceResult = ThirstUtil.traceWater(player);

                if (traceResult == null) {
                    return null;
                }

                if (traceResult.thirstEnum == ThirstEnum.PURIFIED) {
                    if (!ServerConfig.instance.getBoolean(ServerOptions.THIRST_DRINK_BLOCKS)) {
                        return null;
                    }
                    player.world.setBlockToAir(traceResult.pos);
                    takeDrink(player, ThirstEnum.PURIFIED, true);
                } else {
                    if (traceResult.thirstEnum == ThirstEnum.RAIN && !ServerConfig.instance.getBoolean(ServerOptions.THIRST_DRINK_RAIN)) {
                        return null;
                    }

                    if (traceResult.thirstEnum == ThirstEnum.NORMAL && !ServerConfig.instance.getBoolean(ServerOptions.THIRST_DRINK_BLOCKS)) {
                        return null;
                    }
                }

                return traceResult;
            }
        }
        return null;
    }

    public static ITemperatureCapability getTemperatureData(EntityPlayer player) {
        return SDCapabilities.getTemperatureData(player);
    }

    public static IThirstCapability getThirstData(EntityPlayer player) {
        return SDCapabilities.getThirstData(player);
    }

    public static void sendSoundPacketToPlayer(String soundName, float volume, float pitch, EntityPlayer player, BlockPos pos) {
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            playerMP.connection.sendPacket(new SPacketCustomSound(soundName, SoundCategory.PLAYERS, pos.getX(), pos.getY(), pos.getZ(), volume, pitch));
        }
    }

    public static void sendMessage(EntityPlayer player, String msg) {
        player.sendMessage(new TextComponentString(msg));
    }

}
