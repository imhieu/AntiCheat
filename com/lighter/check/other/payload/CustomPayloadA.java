package com.lighter.check.other.payload;

import java.util.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import com.google.common.collect.*;

public class CustomPayloadA extends PacketCheck
{
    private final Map<String, String> map;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        final String s;
        if (packet instanceof PacketPlayInCustomPayload && (s = this.map.get(((PacketPlayInCustomPayload)packet).a())) != null) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("payload channel:").append(s)), false);
            if (this.violations >= this.getMaxViolation()) {
                playerData.fuckOff("PacketPlayInCustomPayload");
            }
        }
    }
    
    public Map<String, String> getMap() {
        return this.map;
    }
    
    public CustomPayloadA() {
        super(CheckType.CLIENTA, "A", "Client", CheckVersion.RELEASE);
        this.map = (Map<String, String>)new ImmutableMap.Builder().put((Object)"LOLIMAHCKER", (Object)"Cracked Vape").put((Object)"CPS_BAN_THIS_NIGGER", (Object)"Cracked Vape").put((Object)"EROUAXWASHERE", (Object)"Cracked Vape").put((Object)"#unbanearwax", (Object)"Cracked Vape").put((Object)"cock", (Object)"Reach Mod").put((Object)"lmaohax", (Object)"Reach Mod").put((Object)"reach", (Object)"Reach Mod").put((Object)"gg", (Object)"Reach Mod").put((Object)"customGuiOpenBspkrs", (Object)"Bspkrs Client").put((Object)"0SO1Lk2KASxzsd", (Object)"Bspkrs Client").put((Object)"MCnetHandler", (Object)"Misplace").put((Object)"n", (Object)"Misplace").build();
        this.violations = -1.0;
    }
}
