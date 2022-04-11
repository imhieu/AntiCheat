package com.lighter.data;

public class ReachData
{
    private final DistanceData distanceData;
    private final PlayerData playerData;
    private final double extra;
    private final PlayerLocation location;
    private final double reach;
    
    public double getExtra() {
        return this.extra;
    }
    
    public ReachData(final PlayerData playerData, final PlayerLocation location, final DistanceData distanceData, final double extra, final double reach) {
        this.playerData = playerData;
        this.location = location;
        this.distanceData = distanceData;
        this.extra = extra;
        this.reach = reach;
    }
    
    public DistanceData getDistanceData() {
        return this.distanceData;
    }
    
    public double getReach() {
        return this.reach;
    }
    
    public PlayerLocation getLocation() {
        return this.location;
    }
    
    public PlayerData getPlayerData() {
        return this.playerData;
    }
}
