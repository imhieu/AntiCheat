package com.lighter.util;

public class CustomBoundingBox
{
    private final double centerZ;
    private final double minX;
    private final double centerX;
    private final double maxX;
    private final double maxZ;
    private final double minZ;
    
    public double getDistanceSquared(final CustomBoundingBox customBoundingBox) {
        return Math.pow(Math.min(Math.abs(customBoundingBox.centerX - this.minX), Math.abs(customBoundingBox.centerX - this.maxX)), 2.0) + Math.pow(Math.min(Math.abs(customBoundingBox.centerZ - this.minZ), Math.abs(customBoundingBox.centerZ - this.maxZ)), 2.0);
    }
    
    public CustomBoundingBox(final double centerX, final double centerZ) {
        this.minX = centerX - 0.3;
        this.centerX = centerX;
        this.maxX = centerX + 0.3;
        this.minZ = centerZ - 0.3;
        this.centerZ = centerZ;
        this.maxZ = centerZ + 0.3;
    }
}
