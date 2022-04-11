package com.lighter.netty;

import com.lighter.data.*;
import io.netty.channel.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.main.*;
import java.util.logging.*;

public class PacketDecoder extends ChannelInboundHandlerAdapter
{
    private final PlayerData playerData;
    
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        super.channelRead(channelHandlerContext, o);
        try {
            this.playerData.handle((Packet)o, true);
        }
        catch (Throwable t) {
            Main.getPlugin().getLogger().log(Level.SEVERE, "Failed to handle incoming packet ", t);
        }
    }
    
    public PacketDecoder(final PlayerData playerData) {
        this.playerData = playerData;
    }
}
