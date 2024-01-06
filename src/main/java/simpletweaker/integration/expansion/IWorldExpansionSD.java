package simpletweaker.integration.expansion;

import com.charles445.simpledifficulty.util.internal.TemperatureUtilInternal;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("simpledifficulty")
@ZenExpansion("crafttweaker.world.IWorld")
@ZenRegister
public class IWorldExpansionSD {

    private static final TemperatureUtilInternal UTIL = new TemperatureUtilInternal();

    @ZenMethod
    public static int getTemperature(IWorld iWorld, IBlockPos iBlockPos) {
        World world = CraftTweakerMC.getWorld(iWorld);
        BlockPos pos = CraftTweakerMC.getBlockPos(iBlockPos);
        return UTIL.getWorldTemperature(world, pos);
    }

}
