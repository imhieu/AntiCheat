package com.lighter.data;

public class DistanceData
{
    private final double z;
    private final double x;
    private final double dist;
    private final double y;
    
    public DistanceData(final double x, final double z, final double y, final double dist) {
        this.x = x;
        this.z = z;
        this.y = y;
        this.dist = dist;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getDist() {
        return this.dist;
    }
    
    public double getX() {
        return this.x;
    }
}
