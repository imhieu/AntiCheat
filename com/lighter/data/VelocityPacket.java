package com.lighter.data;

public class VelocityPacket
{
    private final int z;
    private final int entityId;
    private final int x;
    private final int y;
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public VelocityPacket(final int entityId, final int x, final int y, final int z) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public int getY() {
        return this.y;
    }
}
