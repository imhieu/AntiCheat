package com.lighter.util;

import java.util.function.*;
import org.bukkit.*;
import org.bukkit.block.*;
import java.util.*;
import com.lighter.data.*;

public class Cuboid
{
    private double z2;
    private double y1;
    private double x2;
    private double z1;
    private double x1;
    private double y2;
    
    public Cuboid(final double n, final double n2, final double n3) {
        this(n, n, n2, n2, n3, n3);
    }
    
    public Cuboid expand(final double n, final double n2, final double n3) {
        this.x1 -= n;
        this.x2 += n;
        this.y1 -= n2;
        this.y2 += n2;
        this.z1 -= n3;
        this.z2 += n3;
        return this;
    }
    
    public Cuboid add(final Cuboid cuboid) {
        this.x1 += cuboid.x1;
        this.x2 += cuboid.x2;
        this.y1 += cuboid.y1;
        this.y2 += cuboid.y2;
        this.z1 += cuboid.z1;
        this.z2 += cuboid.z2;
        return this;
    }
    
    public double cX() {
        return (this.x1 + this.x2) * 0.5;
    }
    
    public boolean checkBlocks(final World world, final Predicate<Material> predicate) {
        return checkBlocks(this.getBlocks(world), predicate);
    }
    
    public static boolean checkBlocks(final Collection<Block> collection, final Predicate<Material> predicate) {
        final Iterator<Block> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next().getType())) {
                return false;
            }
        }
        return true;
    }
    
    public double cY() {
        return (this.y1 + this.y2) * 0.5;
    }
    
    public List<Block> getBlocks(final World world) {
        final int n = (int)Math.floor(this.x1);
        final int n2 = (int)Math.ceil(this.x2);
        final int n3 = (int)Math.floor(this.y1);
        final int n4 = (int)Math.ceil(this.y2);
        final int n5 = (int)Math.floor(this.z1);
        final int n6 = (int)Math.ceil(this.z2);
        final ArrayList<Block> list = new ArrayList<Block>();
        list.add(world.getBlockAt(n, n3, n5));
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    list.add(world.getBlockAt(i, j, k));
                }
            }
        }
        return list;
    }
    
    public Cuboid(final PlayerLocation playerLocation) {
        this(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
    }
    
    public Cuboid(final double x1, final double x2, final double y1, final double y2, final double z1, final double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }
    
    public boolean contains(final PlayerLocation playerLocation) {
        return this.x1 <= playerLocation.getX() && this.x2 >= playerLocation.getX() && this.y1 <= playerLocation.getY() && this.y2 >= playerLocation.getY() && this.z1 <= playerLocation.getZ() && this.z2 >= playerLocation.getZ();
    }
    
    public double cZ() {
        return (this.z1 + this.z2) * 0.5;
    }
    
    public Cuboid move(final double n, final double n2, final double n3) {
        this.x1 += n;
        this.x2 += n;
        this.y1 += n2;
        this.y2 += n2;
        this.z1 += n3;
        this.z2 += n3;
        return this;
    }
    
    public double distanceXZ(final double n, final double n2) {
        return MathHelper.sqrt_double(Math.min(Math.pow(n - this.x1, 2.0), Math.pow(n - this.x2, 2.0)) + Math.min(Math.pow(n2 - this.z1, 2.0), Math.pow(n2 - this.z2, 2.0)));
    }
}
