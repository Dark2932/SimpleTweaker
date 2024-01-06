package simpletweaker.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class STSoundUtil {

    public static SoundEvent getSoundEvent(String name) {
        return new SoundEvent(new ResourceLocation(name));
    }

}
