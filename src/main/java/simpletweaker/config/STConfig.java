package simpletweaker.config;

import net.minecraftforge.common.config.Config;

@Config(modid = "simpletweaker")
public class STConfig {

    @Config.Name("Cauldron")
    @Config.Comment("Whether to lower the level of the cauldron when the player drinks water from it. [default=true]")
    public static boolean cauldron = true;

}
