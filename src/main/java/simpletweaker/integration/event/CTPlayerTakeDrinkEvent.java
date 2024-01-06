package simpletweaker.integration.event;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ModOnly("simpledifficulty")
@ZenClass("mods.simpletweaker.event.PlayerTakeDrinkEvent")
@ZenRegister
public class CTPlayerTakeDrinkEvent implements IPlayerEvent {

    private final EntityPlayer player;
    private final int thirst;
    private final float saturation;
    private final float thirstyChance;
    private final IBlockPos pos;
    private final IItemStack item;
    private final String type;

    public CTPlayerTakeDrinkEvent(EntityPlayer player, int thirst, float saturation, float thirstyChance, IBlockPos pos, IItemStack item, String type) {
        this.player = player;
        this.thirst = thirst;
        this.saturation = saturation;
        this.thirstyChance = thirstyChance;
        this.pos = pos;
        this.item = item;
        this.type = type;
    }

    @Override
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(player);
    }

    @ZenGetter("thirst")
    public int getThirst() {
        return thirst;
    }

    @ZenGetter("saturation")
    public float getSaturation() {
        return saturation;
    }

    @ZenGetter("thirstyChance")
    public float getThirstyChance() {
        return thirstyChance;
    }

    @ZenGetter("pos")
    public IBlockPos getPos() {
        return pos;
    }

    @ZenGetter("item")
    public IItemStack getItem() {
        return item;
    }

    @ZenGetter("type")
    public String getType() {
        return type;
    }

}
