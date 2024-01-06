package simpletweaker.network;

import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import simpletweaker.integration.event.IEventManagerExpansion;

import java.util.UUID;

public class PktPlayerTakeDrinkEvent implements IMessage {

    private UUID uuid;
    private ThirstEnum thirstEnum;
    private BlockPos pos;

    public PktPlayerTakeDrinkEvent(UUID uuid, ThirstEnum thirstEnum, BlockPos pos) {
        this.uuid = uuid;
        this.thirstEnum = thirstEnum;
        this.pos = pos;
    }

    public PktPlayerTakeDrinkEvent() {}

    @Override
    public void fromBytes(ByteBuf buf) {

        final long most = buf.readLong();
        final long least = buf.readLong();
        this.uuid = new UUID(most, least);

        int ordinal = buf.readByte();
        if (ordinal == 0) {
            this.thirstEnum = ThirstEnum.NORMAL;
        } else if (ordinal == 1) {
            this.thirstEnum = ThirstEnum.RAIN;
        } else if (ordinal == 2) {
            this.thirstEnum = ThirstEnum.POTION;
        } else if (ordinal == 3) {
            this.thirstEnum = ThirstEnum.PURIFIED;
        }

        this.pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeLong(this.uuid.getMostSignificantBits());
        buf.writeLong(this.uuid.getLeastSignificantBits());

        buf.writeByte(thirstEnum.ordinal());

        buf.writeLong(this.pos.toLong());

    }

    public static class Handler implements IMessageHandler<PktPlayerTakeDrinkEvent, IMessage> {
        public Handler() {}
        public IMessage onMessage(PktPlayerTakeDrinkEvent msg, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                EntityPlayerMP player = ctx.getServerHandler().player;
                if (player != null && player.getUniqueID().equals(msg.uuid)) {
                    IEventManagerExpansion.handlePlayerTakeDrinkEvent(player, msg.thirstEnum.getThirst(), msg.thirstEnum.getSaturation(), msg.thirstEnum.getThirstyChance(), CraftTweakerMC.getIBlockPos(msg.pos), null, msg.thirstEnum.getName());
                }
             }
            return null;
        }
    }

}
