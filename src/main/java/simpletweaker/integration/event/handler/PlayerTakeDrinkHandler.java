package simpletweaker.integration.event.handler;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.SDItems;
import com.charles445.simpledifficulty.api.config.JsonConfig;
import com.charles445.simpledifficulty.api.config.QuickConfig;
import com.charles445.simpledifficulty.api.config.json.JsonConsumableThirst;
import com.charles445.simpledifficulty.api.config.json.JsonItemIdentity;
import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.api.thirst.ThirstEnumBlockPos;
import com.charles445.simpledifficulty.item.ItemDrinkBase;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import simpletweaker.SimpleTweaker;
import simpletweaker.config.STConfig;
import simpletweaker.integration.event.IEventManagerExpansion;
import simpletweaker.network.PktPlayerTakeDrinkEvent;
import simpletweaker.util.STPlayerUtil;

import java.util.List;
import java.util.Map;

public class PlayerTakeDrinkHandler {

    public PlayerTakeDrinkHandler() {}

    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (QuickConfig.isThirstEnabled()) {
            if (event.getEntityLiving() instanceof EntityPlayer) {

                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                BlockPos pos = player.getPosition();
                ItemStack stack = event.getItem();
                Item item = stack.getItem();
                String name = item.getRegistryName().toString();

                if (player.world.isRemote) {
                    return;
                }

                Map<String, List<JsonConsumableThirst>> consumableThirst = JsonConfig.consumableThirst;
                List<JsonConsumableThirst> consumableList = consumableThirst.get(name);
                if (consumableThirst.keySet().toString().contains(name) && consumableList != null) {
                    for (JsonConsumableThirst jct : consumableList) {
                        if (jct != null) {
                            JsonItemIdentity identity = jct.identity;
                            if (jct.matches(stack) || (identity.metadata == -1 && stack.getMetadata() == 0 && (identity.nbt == null || (stack.getTagCompound() != null && stack.getTagCompound().toString().equals(identity.nbt))))) {
                                IEventManagerExpansion.handlePlayerTakeDrinkEvent(player, jct.amount, jct.saturation, jct.thirstyChance, CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIItemStack(stack), "item");
                                return;
                            }
                        }
                    }
                }

                if (item.equals(SDItems.canteen) || item.equals(SDItems.ironCanteen) || item.equals(SDItems.juice) || item.equals(SDItems.purifiedWaterBottle)) {
                    ItemDrinkBase itemDrink = (ItemDrinkBase) item;
                    IEventManagerExpansion.handlePlayerTakeDrinkEvent(player, itemDrink.getThirstLevel(stack), itemDrink.getSaturationLevel(stack), itemDrink.getDirtyChance(stack), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIItemStack(stack), "item");
                    return;
                }

                if (item.equals(Items.POTIONITEM)) {
                    PotionType potionType = PotionUtils.getPotionFromItem(stack);
                    if (potionType.getRegistryName() != null) {
                        if (potionType.equals(PotionTypes.WATER) || potionType.equals(PotionTypes.AWKWARD) || potionType.equals(PotionTypes.MUNDANE) || potionType.equals(PotionTypes.THICK)) {
                            IEventManagerExpansion.handlePlayerTakeDrinkEvent(player, ThirstEnum.NORMAL.getThirst(), ThirstEnum.NORMAL.getSaturation(), ThirstEnum.NORMAL.getThirstyChance(), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIItemStack(stack), "item");
                            return;
                        }
                        if (!potionType.equals(PotionTypes.EMPTY)) {
                            IEventManagerExpansion.handlePlayerTakeDrinkEvent(player, ThirstEnum.POTION.getThirst(), ThirstEnum.POTION.getSaturation(), ThirstEnum.POTION.getThirstyChance(), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIItemStack(stack), "potion");
                        }
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (QuickConfig.isThirstEnabled()) {

            World world = event.getWorld();
            BlockPos pos = event.getPos();
            EnumHand hand = event.getHand();
            EntityPlayer player = event.getEntityPlayer();
            ItemStack heldItem = player.getHeldItemMainhand();
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            ThirstEnumBlockPos thirstEnumBlockPos = STPlayerUtil.traceWaterToDrink(player);

            if (!world.isRemote && player.isSneaking() && hand == EnumHand.MAIN_HAND && heldItem.isEmpty() && SDCapabilities.getThirstData(player).isThirsty()) {

                if (thirstEnumBlockPos != null) {
                    ThirstEnum thirstEnum = thirstEnumBlockPos.thirstEnum;
                    IEventManagerExpansion.handlePlayerTakeDrinkEvent(player, thirstEnum.getThirst(), thirstEnum.getSaturation(), thirstEnum.getThirstyChance(), CraftTweakerMC.getIBlockPos(pos), null, thirstEnum.getName());
                }

                if (block == Blocks.CAULDRON) {
                    int level = state.getValue(BlockCauldron.LEVEL);
                    if (level > 0) {
                        IEventManagerExpansion.handlePlayerTakeDrinkEvent(player, ThirstEnum.NORMAL.getThirst(), ThirstEnum.NORMAL.getSaturation(), ThirstEnum.NORMAL.getThirstyChance(), CraftTweakerMC.getIBlockPos(pos), null, "cauldron");
                        if (STConfig.cauldron) {
                            BlockCauldron cauldron = (BlockCauldron) block;
                            cauldron.setWaterLevel(world, pos, state, level - 1);
                            if (level == 1) {
                                STPlayerUtil.takeDrink(player, 3, 0.3f, true);
                            }
                        }
                    }
                }

            }

        }
    }

    @SubscribeEvent
    public void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        if (QuickConfig.isThirstEnabled()) {
            EntityPlayer player = event.getEntityPlayer();
            EnumHand hand = event.getHand();
            ThirstEnumBlockPos thirstEnumBlockPos = STPlayerUtil.traceWaterToDrink(player);
            if (player.isSneaking() && hand == EnumHand.MAIN_HAND && SDCapabilities.getThirstData(player).isThirsty() && thirstEnumBlockPos != null) {
                SimpleTweaker.CHANNEL.sendToServer(new PktPlayerTakeDrinkEvent(player.getUniqueID(), thirstEnumBlockPos.thirstEnum, thirstEnumBlockPos.pos));
            }
        }
    }

}