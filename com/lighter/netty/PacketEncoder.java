package com.lighter.netty;

import com.lighter.data.*;
import io.netty.channel.*;
import com.lighter.util.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.main.*;
import java.util.logging.*;

public class PacketEncoder extends ChannelOutboundHandlerAdapter
{
    private final PlayerData playerData;
    
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        if (o instanceof PacketPlayOutEntityTeleport) {
            SafeReflection.setOnGround((PacketPlayOutEntityTeleport)o, false);
        }
        else if (o instanceof PacketPlayOutEntity) {
            SafeReflection.setOnGround((PacketPlayOutEntity)o, false);
        }
        super.write(channelHandlerContext, o, channelPromise);
        try {
            this.playerData.handle((Packet)o, false);
        }
        catch (Throwable t) {
            Main.getPlugin().getLogger().log(Level.SEVERE, "Failed to handle outgoing packet ", t);
        }
    }
    
    public PacketEncoder(final PlayerData playerData) {
        this.playerData = playerData;
    }
}
