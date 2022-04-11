package com.lighter.util;

import org.bukkit.util.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.google.common.base.*;
import java.lang.reflect.*;

public class SafeReflection
{
    private static final Field PlayerConnection_e;
    private static final Field PacketPlayOutEntityTeleport_b;
    private static final Field PacketPlayOutEntityTeleport_c;
    private static final Field PacketPlayOutEntity_g;
    private static final Field PacketPlayOutEntityTeleport_e;
    private static final Field PacketPlayOutEntity_d;
    private static final Field PacketPlayOutEntity_a;
    private static final Field PacketPlayOutEntity_c;
    private static final Field PacketPlayOutEntityVelocity_d;
    private static final Field PacketPlayOutPosition_c;
    private static final Field PacketPlayOutEntityVelocity_c;
    private static final Field PacketPlayOutPosition_e;
    private static final Field PacketPlayOutPosition_d;
    private static final Field PacketPlayOutEntityDestroy_a;
    private static final Field PacketPlayOutPosition_a;
    private static final Field PacketPlayOutEntityTeleport_f;
    private static final Field PacketPlayInUseEntity_a;
    private static final Field PacketPlayOutKeepAlive_a;
    private static final Field PacketPlayOutEntityTeleport_d;
    private static final Field PacketPlayOutEntityVelocity_b;
    private static final Field PacketPlayOutEntity_b;
    private static final Field PacketPlayOutEntityTeleport_a;
    private static final Field PacketPlayOutPosition_b;
    private static final Field PacketHandshakingInSetProtocol_a;
    private static final Field PacketPlayInWindowClick_slot;
    private static final Field PacketPlayOutEntityTeleport_g;
    private static final Field PacketPlayOutEntityVelocity_a;
    
    private static <T> T fetch(final Field field, final Object o) {
        try {
            return (T)field.get(o);
        }
        catch (IllegalAccessException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    public static Vector getMovement(final PacketPlayOutEntity packetPlayOutEntity) {
        return new Vector(fetch(SafeReflection.PacketPlayOutEntity_b, packetPlayOutEntity) / 32.0, fetch(SafeReflection.PacketPlayOutEntity_c, packetPlayOutEntity) / 32.0, fetch(SafeReflection.PacketPlayOutEntity_d, packetPlayOutEntity) / 32.0);
    }
    
    public static int getEntityId(final PacketPlayOutEntityTeleport packetPlayOutEntityTeleport) {
        return fetch(SafeReflection.PacketPlayOutEntityTeleport_a, packetPlayOutEntityTeleport);
    }
    
    public static PlayerLocation getLocation(final long n, final int n2, final PacketPlayOutPosition packetPlayOutPosition) {
        return new PlayerLocation(n, n2, fetch(SafeReflection.PacketPlayOutPosition_a, packetPlayOutPosition), fetch(SafeReflection.PacketPlayOutPosition_b, packetPlayOutPosition), fetch(SafeReflection.PacketPlayOutPosition_c, packetPlayOutPosition), fetch(SafeReflection.PacketPlayOutPosition_d, packetPlayOutPosition), fetch(SafeReflection.PacketPlayOutPosition_e, packetPlayOutPosition), null);
    }
    
    public static void setOnGround(final PacketPlayOutEntity packetPlayOutEntity, final boolean b) {
        set(SafeReflection.PacketPlayOutEntity_g, packetPlayOutEntity, b);
    }
    
    public static void setNextKeepAliveTime(final PlayerConnection playerConnection, final int n) {
        set(SafeReflection.PlayerConnection_e, playerConnection, n);
    }
    
    private static Field access(final Class clazz, final String s) {
        try {
            final Field declaredField = clazz.getDeclaredField(s);
            declaredField.setAccessible(true);
            return declaredField;
        }
        catch (NoSuchFieldException ex) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(clazz.getSimpleName()).append(":").append(s)), ex);
        }
    }
    
    public static VelocityPacket getVelocity(final PacketPlayOutEntityVelocity packetPlayOutEntityVelocity) {
        return new VelocityPacket(fetch(SafeReflection.PacketPlayOutEntityVelocity_a, packetPlayOutEntityVelocity), fetch(SafeReflection.PacketPlayOutEntityVelocity_b, packetPlayOutEntityVelocity), fetch(SafeReflection.PacketPlayOutEntityVelocity_c, packetPlayOutEntityVelocity), fetch(SafeReflection.PacketPlayOutEntityVelocity_d, packetPlayOutEntityVelocity));
    }
    
    public static int getKeepAliveId(final PacketPlayOutKeepAlive packetPlayOutKeepAlive) {
        return fetch(SafeReflection.PacketPlayOutKeepAlive_a, packetPlayOutKeepAlive);
    }
    
    public static PlayerLocation getLocation(final long n, final int n2, final PacketPlayOutEntityTeleport packetPlayOutEntityTeleport) {
        return new PlayerLocation(n, n2, fetch(SafeReflection.PacketPlayOutEntityTeleport_b, packetPlayOutEntityTeleport) / 32.0, fetch(SafeReflection.PacketPlayOutEntityTeleport_c, packetPlayOutEntityTeleport) / 32.0, fetch(SafeReflection.PacketPlayOutEntityTeleport_d, packetPlayOutEntityTeleport) / 32.0, fetch(SafeReflection.PacketPlayOutEntityTeleport_e, packetPlayOutEntityTeleport) * 360.0f / 256.0f, fetch(SafeReflection.PacketPlayOutEntityTeleport_f, packetPlayOutEntityTeleport) * 360.0f / 256.0f, fetch(SafeReflection.PacketPlayOutEntityTeleport_g, packetPlayOutEntityTeleport));
    }
    
    private static <T> Constructor<T> constructor(final Class<T> clazz, final Class... array) {
        try {
            final Constructor<T> declaredConstructor = clazz.getDeclaredConstructor((Class<?>[])array);
            declaredConstructor.setAccessible(true);
            return declaredConstructor;
        }
        catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    public static void setOnGround(final PacketPlayOutEntityTeleport packetPlayOutEntityTeleport, final boolean b) {
        set(SafeReflection.PacketPlayOutEntityTeleport_g, packetPlayOutEntityTeleport, b);
    }
    
    static {
        PacketPlayOutPosition_a = access(PacketPlayOutPosition.class, "a", "x");
        PacketPlayOutPosition_b = access(PacketPlayOutPosition.class, "b", "y");
        PacketPlayOutPosition_c = access(PacketPlayOutPosition.class, "c", "z");
        PacketPlayOutPosition_d = access(PacketPlayOutPosition.class, "d", "yaw");
        PacketPlayOutPosition_e = access(PacketPlayOutPosition.class, "e", "pitch");
        PacketPlayOutKeepAlive_a = access(PacketPlayOutKeepAlive.class, "a");
        PacketPlayInUseEntity_a = access(PacketPlayInUseEntity.class, "a");
        PlayerConnection_e = access(PlayerConnection.class, "e");
        PacketPlayOutEntityVelocity_a = access(PacketPlayOutEntityVelocity.class, "a", "id");
        PacketPlayOutEntityVelocity_b = access(PacketPlayOutEntityVelocity.class, "b", "x");
        PacketPlayOutEntityVelocity_c = access(PacketPlayOutEntityVelocity.class, "c", "y");
        PacketPlayOutEntityVelocity_d = access(PacketPlayOutEntityVelocity.class, "d", "z");
        PacketPlayOutEntity_a = access(PacketPlayOutEntity.class, "a");
        PacketPlayOutEntity_b = access(PacketPlayOutEntity.class, "b");
        PacketPlayOutEntity_c = access(PacketPlayOutEntity.class, "c");
        PacketPlayOutEntity_d = access(PacketPlayOutEntity.class, "d");
        PacketPlayOutEntity_g = access(PacketPlayOutEntity.class, "g");
        PacketPlayOutEntityTeleport_a = access(PacketPlayOutEntityTeleport.class, "a");
        PacketPlayOutEntityTeleport_b = access(PacketPlayOutEntityTeleport.class, "b");
        PacketPlayOutEntityTeleport_c = access(PacketPlayOutEntityTeleport.class, "c");
        PacketPlayOutEntityTeleport_d = access(PacketPlayOutEntityTeleport.class, "d");
        PacketPlayOutEntityTeleport_e = access(PacketPlayOutEntityTeleport.class, "e");
        PacketPlayOutEntityTeleport_f = access(PacketPlayOutEntityTeleport.class, "f");
        PacketPlayOutEntityTeleport_g = access(PacketPlayOutEntityTeleport.class, "g");
        PacketPlayOutEntityDestroy_a = access(PacketPlayOutEntityDestroy.class, "a");
        PacketHandshakingInSetProtocol_a = access(PacketHandshakingInSetProtocol.class, "a");
        PacketPlayInWindowClick_slot = access(PacketPlayInWindowClick.class, "slot");
    }
    
    public static int getAttackedEntity(final PacketPlayInUseEntity packetPlayInUseEntity) {
        return fetch(SafeReflection.PacketPlayInUseEntity_a, packetPlayInUseEntity);
    }
    
    public static int getEntityId(final PacketPlayOutEntity packetPlayOutEntity) {
        return fetch(SafeReflection.PacketPlayOutEntity_a, packetPlayOutEntity);
    }
    
    private static Field access(final Class clazz, final String... array) {
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final String s = array[i];
            try {
                final Field declaredField = clazz.getDeclaredField(s);
                declaredField.setAccessible(true);
                return declaredField;
            }
            catch (NoSuchFieldException ex) {
                ++i;
                continue;
            }
            break;
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(clazz.getSimpleName()).append(":").append(Joiner.on(",").join((Object[])array))));
    }
    
    public static int getProtocolVersion(final PacketHandshakingInSetProtocol packetHandshakingInSetProtocol) {
        return fetch(SafeReflection.PacketHandshakingInSetProtocol_a, packetHandshakingInSetProtocol);
    }
    
    private static <T> void set(final Field field, final Object o, final T t) {
        try {
            field.set(o, t);
        }
        catch (IllegalAccessException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    private static <T> T fetchConstructor(final Constructor<T> constructor, final Object... array) {
        try {
            return constructor.newInstance(array);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            final Object o;
            throw new IllegalArgumentException((Throwable)o);
        }
    }
    
    public static int getSlot(final PacketPlayInWindowClick packetPlayInWindowClick) {
        return fetch(SafeReflection.PacketPlayInWindowClick_slot, packetPlayInWindowClick);
    }
    
    public static int getNextKeepAliveTime(final PlayerConnection playerConnection) {
        return fetch(SafeReflection.PlayerConnection_e, playerConnection);
    }
    
    public static int[] getEntities(final PacketPlayOutEntityDestroy packetPlayOutEntityDestroy) {
        return fetch(SafeReflection.PacketPlayOutEntityDestroy_a, packetPlayOutEntityDestroy);
    }
}
