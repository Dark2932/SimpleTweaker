#reloadable

import crafttweaker.player.IPlayer;
import crafttweaker.world.IBlockPos;
import crafttweaker.world.IWorld;
import crafttweaker.event.PlayerTickEvent;
import crafttweaker.event.PlayerRightClickItemEvent;

import mods.simpletweaker.util.ThirstUtil;
import mods.simpletweaker.event.PlayerTakeDrinkEvent;

import mods.simpletweaker.util.CaptionUtil;
import mods.simpletweaker.type.ICaption;
import mods.simpletweaker.type.ICaptionType;
import mods.simpletweaker.type.IDisplaySideType;
import mods.simpletweaker.type.IDisplayStyle;
import mods.simpletweaker.type.IImage;
import mods.simpletweaker.type.ISound;
import mods.simpletweaker.type.IText;
import mods.simpletweaker.type.ITimer;

events.onPlayerTick(function(event as PlayerTickEvent) {
    val player as IPlayer = event.player;
    val world as IWorld = player.world;
    if (!world.remote && event.phase == "START") {
        //player.health = 20;
        player.thirst -= 1;
        player.thirstSaturation -= 1;
        //player.thirst -= 1;
        //print(player.nbt);
    }
});


events.onPlayerTakeDrink(function(event as PlayerTakeDrinkEvent) {
    event.player.sendMessage(event.thirst);
    event.player.sendMessage(event.saturation);
    event.player.sendMessage(event.type);
    event.player.sendMessage(event.pos.x ~ " " ~ event.pos.y ~ " " ~ event.pos.z);
});

events.onPlayerRightClickItem(function(event as PlayerRightClickItemEvent) {
    val player as IPlayer = event.player;
    val caption as ICaption = ICaption.createSimple(IText.create("你右键了§a"~event.item.displayName), IText.create(""), ITimer.create(20), ITimer.create(0), ICaptionType.getMain(), IDisplaySideType.getRight(), IDisplayStyle.getDisplayStyle(2), false);
    CaptionUtil.addCaption(player, caption);
    player.sendMessage(player.hasCurrentCaption(ICaptionType.getMain()));
});