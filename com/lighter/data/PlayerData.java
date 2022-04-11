package com.lighter.data;

import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;
import com.lighter.check.impl.*;
import java.util.stream.*;
import org.bukkit.block.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import com.lighter.main.*;
import io.netty.channel.*;
import com.google.common.collect.*;
import com.lighter.data.manager.*;
import org.bukkit.scheduler.*;
import org.bukkit.plugin.*;
import java.util.function.*;
import org.bukkit.*;
import com.lighter.util.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.util.*;

public class PlayerData
{
    private final Map<Integer, Deque<PlayerLocation>> recentMoveMap;
    private final Queue<Double> recentConnectionFrequencies;
    private int teleportTicks;
    private double lastVelY2;
    private PlayerLocation lastLastLocation;
    private PlayerLocation lastLocation;
    private int packets;
    private long lastDelayed;
    public int[] cps1;
    private final Queue<PlayerLocation> recentMoveList;
    private boolean receivedKeepAlive;
    private final PlayerLocation location;
    private int lastAttackedId;
    private int velocityTicks;
    private boolean spawnedIn;
    private CustomLocation lastMovePacket;
    private final List<ReachCheck> reachChecks;
    private int horizontalSpeedTicks;
    private long lastFlying;
    private double lastVelY;
    private long lastFast;
    private boolean banned;
    static final boolean $assertionsDisabled;
    private boolean debug;
    private Boolean sneaking;
    private PlayerData lastAttacked;
    private int horizontalVelocityTicks;
    private int steerTicks;
    private final List<EventCheck> eventChecks;
    private int totalTicks;
    private int ping;
    private final Map<Integer, Long> keepAliveMap;
    private double velZ;
    private int verticalVelocityTicks;
    private boolean abortedDigging;
    private long lastPosition;
    private int flyingTicks;
    private final Queue<Integer> connectionFrequency;
    private double velX;
    private int groundTicks;
    private final double[] reach1;
    private final List<PacketCheck> packetChecks;
    private double lastVelY3;
    private int nonMoveTicks;
    private boolean stoppedDigging;
    private double deltaY;
    private double velY;
    private boolean onGround;
    private boolean inventoryOpen;
    private final List<AimCheck> aimChecks;
    private Boolean sprinting;
    private final Queue<PlayerLocation> teleportList;
    private final List<MovementCheck> movementChecks;
    private boolean digging;
    private final Queue<BiConsumer<Integer, Double>> pingQueue;
    private final Player player;
    private int averagePing;
    private double lastDeltaY;
    private boolean swingDigging;
    private boolean alerts;
    private boolean enabled;
    private int lastAttackTicks;
    private final EntityPlayer entityPlayer;
    
    public boolean isAlerts() {
        return this.alerts;
    }
    
    @EventHandler
    public void handle(final Event event) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (event instanceof PlayerRespawnEvent) {
            this.inventoryOpen = false;
        }
        final Iterator<EventCheck> iterator = this.eventChecks.iterator();
        while (iterator.hasNext()) {
            iterator.next().handle(this.player, this, event, currentTimeMillis);
        }
    }
    
    public double getVelX() {
        return this.velX;
    }
    
    public double getLastVelY() {
        return this.lastVelY;
    }
    
    public int getPackets() {
        return this.packets;
    }
    
    private void lambda$handle$11(final long n, final AimCheck aimCheck) {
        aimCheck.handle(this.player, this, this.lastLocation, this.location, n);
    }
    
    public void setLastMovePacket(final CustomLocation lastMovePacket) {
        this.lastMovePacket = lastMovePacket;
    }
    
    @Deprecated
    public Queue<PlayerLocation> getRecentMoveList() {
        return this.recentMoveList;
    }
    
    public boolean isSpawnedIn() {
        return this.spawnedIn;
    }
    
    public double getDeltaY() {
        return this.deltaY;
    }
    
    public boolean isInventoryOpen() {
        return this.inventoryOpen;
    }
    
    public void setVelX(final double velX) {
        this.velX = velX;
    }
    
    public double getLastVelY2() {
        return this.lastVelY2;
    }
    
    public void setBanned(final boolean banned) {
        this.banned = banned;
    }
    
    private void lambda$handle$16(final Packet packet, final long n, final PacketCheck packetCheck) {
        packetCheck.handle(this.player, this, packet, n);
    }
    
    public int getVerticalVelocityTicks() {
        return this.verticalVelocityTicks;
    }
    
    static {
        $assertionsDisabled = !PlayerData.class.desiredAssertionStatus();
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public int getSteerTicks() {
        return this.steerTicks;
    }
    
    public boolean hasFast() {
        return this.hasFast(this.lastFlying);
    }
    
    public int getLastAttackTicks() {
        return this.lastAttackTicks;
    }
    
    public void setInventoryOpen(final boolean inventoryOpen) {
        this.inventoryOpen = inventoryOpen;
    }
    
    public PlayerData getLastAttacked() {
        return this.lastAttacked;
    }
    
    public int getPingTicks() {
        return (this.receivedKeepAlive ? ((int)Math.ceil(this.getPing() / 50.0)) : 20) + 1;
    }
    
    public int[] getCps() {
        return this.cps1;
    }
    
    public void setLastVelY3(final double lastVelY3) {
        this.lastVelY3 = lastVelY3;
    }
    
    static void access$100(final PlayerData playerData, final PlayerLocation playerLocation, final boolean b) {
        playerData.processPosition(playerLocation, b);
    }
    
    private static boolean lambda$new$6(final Check check) {
        return check instanceof EventCheck;
    }
    
    public double getLastDeltaY() {
        return this.lastDeltaY;
    }
    
    public Boolean getSneaking() {
        return this.sneaking;
    }
    
    public PlayerLocation getLastLastLocation() {
        return this.lastLastLocation;
    }
    
    private void lambda$handle$15(final List list, final long n, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final Integer n2, final Double n3) {
        final List<Object> list2 = list.stream().filter(PlayerData::lambda$null$12).collect((Collector<? super Object, ?, List<Object>>)Collectors.toList());
        if (!list2.isEmpty()) {
            final int index = list.indexOf(list2.stream().min(Comparator.comparingLong(PlayerLocation::getTimestamp)).get());
            if (index > 0) {
                list2.add(list.get(index - 1));
            }
            final List<? super Object> list3 = list2.stream().map((Function<? super PlayerLocation, ?>)PlayerLocation::hitbox).map((Function<? super Object, ?>)PlayerData::lambda$null$13).map((Function<? super Object, ?>)PlayerData::lambda$null$14).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
            final PlayerLocation[] array = list2.toArray(new PlayerLocation[0]);
            final DistanceData distanceData = list3.stream().min(Comparator.comparingDouble(DistanceData::getDist)).get();
            final double dist = distanceData.getDist();
            final double n4 = Math.abs(Math.sin(Math.toRadians(Math.min(MathUtil.getMinimumAngle(playerLocation, array), MathUtil.getMinimumAngle(playerLocation2, array))))) * dist;
            final double n5 = MathUtil.hypot(dist, Math.abs(Math.sin(Math.toRadians((playerLocation.getPitch() > 0.0f != playerLocation2.getPitch() > 0.0f) ? 0.0 : MathUtil.lowestAbs(playerLocation.getPitch(), playerLocation2.getPitch()))) * dist), n4) - 0.01;
            final ReachData reachData = new ReachData(this, playerLocation, distanceData, n4, n5);
            final Iterator<ReachCheck> iterator = this.reachChecks.iterator();
            this.reach1[4] = this.reach1[3];
            this.reach1[3] = this.reach1[2];
            this.reach1[2] = this.reach1[1];
            this.reach1[1] = this.reach1[0];
            this.reach1[0] = n5;
            if (iterator.hasNext()) {
                do {
                    iterator.next().handle(this.player, this, reachData, System.currentTimeMillis());
                } while (iterator.hasNext());
            }
        }
    }
    
    public void cancelMove(final Player player, final PlayerLocation playerLocation, final World world) {
        final int locToBlock = Location.locToBlock(playerLocation.getX() - 0.3);
        final int locToBlock2 = Location.locToBlock(playerLocation.getZ() - 0.3);
        int locToBlock3 = Location.locToBlock(playerLocation.getY());
        final int locToBlock4 = Location.locToBlock(playerLocation.getX() + 0.3);
        final int locToBlock5 = Location.locToBlock(playerLocation.getZ() + 0.3);
        for (int i = 0; i < 1; ++i) {
            if (Materials.checkFlag(player.getWorld().getBlockAt(locToBlock, locToBlock3 - 1, locToBlock2).getType(), 1)) {
                break;
            }
            if (Materials.checkFlag(player.getWorld().getBlockAt(locToBlock4, locToBlock3 - 1, locToBlock5).getType(), 1)) {
                break;
            }
            --locToBlock3;
        }
        player.teleport(new Location(world, playerLocation.getX(), (double)locToBlock3, playerLocation.getZ(), playerLocation.getYaw(), playerLocation.getPitch()));
    }
    
    public CustomLocation getLastMovePacket() {
        return this.lastMovePacket;
    }
    
    private static AimCheck lambda$new$5(final Check check) {
        return (AimCheck)check;
    }
    
    public long getLastFlying() {
        return this.lastFlying;
    }
    
    public int getMaxPingTicks() {
        return (this.receivedKeepAlive ? ((int)Math.ceil(Math.max(this.ping, this.averagePing) / 50.0)) : 20) + 1;
    }
    
    public int getHorizontalSpeedTicks() {
        return this.horizontalSpeedTicks;
    }
    
    public double[] getReach() {
        return this.reach1;
    }
    
    public double getLastVelY3() {
        return this.lastVelY3;
    }
    
    public void setDeltaY(final double deltaY) {
        this.deltaY = deltaY;
    }
    
    public int getPing() {
        return this.ping;
    }
    
    private void lambda$handle$17(final int n, final Cuboid cuboid, final World world) {
        final int verticalVelocityTicks = n - this.totalTicks;
        boolean b = false;
        if (this.velY > 0.0) {
            final Iterator<Block> iterator = cuboid.getBlocks(world).iterator();
            while (iterator.hasNext()) {
                if (iterator.next().isEmpty()) {
                    continue;
                }
                b = true;
                break;
            }
        }
        if (!b && this.lastVelY == 0.0 && this.ping > 0) {
            this.lastVelY = this.velY;
            this.lastVelY2 = this.velY;
            this.lastVelY3 = this.velY;
            this.verticalVelocityTicks = verticalVelocityTicks;
        }
        else {
            this.lastVelY = 0.0;
            this.lastVelY2 = 0.0;
            this.lastVelY3 = 0.0;
        }
    }
    
    public void setAlerts(final boolean alerts) {
        this.alerts = alerts;
    }
    
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }
    
    public void fuckOff(final String s) {
        final long currentTimeMillis = System.currentTimeMillis();
        final PlayerConnection playerConnection = ((CraftPlayer)this.getPlayer()).getHandle().playerConnection;
        if (playerConnection == null) {
            return;
        }
        final NetworkManager networkManager = playerConnection.networkManager;
        if (networkManager == null) {
            return;
        }
        final Channel channel = networkManager.channel;
        if (channel == null) {
            return;
        }
        channel.close();
        Main.getPlugin().getLogger().info("------------------");
        Main.getPlugin().getLogger().info("Lighter Anti-Crash");
        Main.getPlugin().getLogger().info(String.valueOf(new StringBuilder().append("-> ").append(this.getPlayer().getName())));
        Main.getPlugin().getLogger().info(String.valueOf(new StringBuilder().append("Force kicked in ").append(System.currentTimeMillis() - currentTimeMillis).append("ms.")));
        Main.getPlugin().getLogger().info(String.valueOf(new StringBuilder().append("-> ").append(s)));
        Main.getPlugin().getLogger().info("Lighter Anti-Crash");
        Main.getPlugin().getLogger().info("------------------");
    }
    
    static int access$000(final PlayerData playerData) {
        return playerData.packets;
    }
    
    public void setLastVelY2(final double lastVelY2) {
        this.lastVelY2 = lastVelY2;
    }
    
    public boolean hasFast(final long n) {
        return this.lastFlying != 0L && this.lastFast != 0L && n - this.lastFast < 110L;
    }
    
    public PlayerData(final Player player, final List<Check> list) {
        this.recentMoveList = new ConcurrentLinkedQueue<PlayerLocation>();
        this.recentMoveMap = new ConcurrentHashMap<Integer, Deque<PlayerLocation>>();
        this.teleportList = new ConcurrentLinkedQueue<PlayerLocation>();
        this.receivedKeepAlive = false;
        this.lastPosition = 0L;
        this.digging = false;
        this.swingDigging = false;
        this.abortedDigging = false;
        this.stoppedDigging = false;
        this.banned = false;
        this.enabled = true;
        this.keepAliveMap = new ConcurrentHashMap<Integer, Long>();
        this.pingQueue = new ConcurrentLinkedQueue<BiConsumer<Integer, Double>>();
        this.connectionFrequency = new ConcurrentLinkedQueue<Integer>();
        this.recentConnectionFrequencies = new ConcurrentLinkedQueue<Double>();
        this.packets = 0;
        this.lastAttackTicks = 600;
        this.nonMoveTicks = 0;
        this.teleportTicks = 0;
        this.flyingTicks = 0;
        this.groundTicks = 0;
        this.velocityTicks = 0;
        this.verticalVelocityTicks = -20;
        this.horizontalVelocityTicks = 0;
        this.totalTicks = 0;
        this.steerTicks = 0;
        this.spawnedIn = false;
        this.sprinting = null;
        this.sneaking = null;
        this.player = player;
        this.entityPlayer = ((CraftPlayer)player).getHandle();
        this.packetChecks = (List<PacketCheck>)ImmutableList.copyOf((Collection)list.stream().filter((Predicate<? super Object>)PlayerData::lambda$new$0).map((Function<? super Object, ?>)PlayerData::lambda$new$1).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        this.movementChecks = (List<MovementCheck>)ImmutableList.copyOf((Collection)list.stream().filter((Predicate<? super Object>)PlayerData::lambda$new$2).map((Function<? super Object, ?>)PlayerData::lambda$new$3).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        this.aimChecks = (List<AimCheck>)ImmutableList.copyOf((Collection)list.stream().filter((Predicate<? super Object>)PlayerData::lambda$new$4).map((Function<? super Object, ?>)PlayerData::lambda$new$5).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        this.eventChecks = (List<EventCheck>)ImmutableList.copyOf((Collection)list.stream().filter((Predicate<? super Object>)PlayerData::lambda$new$6).map((Function<? super Object, ?>)PlayerData::lambda$new$7).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        this.reachChecks = (List<ReachCheck>)ImmutableList.copyOf((Collection)list.stream().filter((Predicate<? super Object>)PlayerData::lambda$new$8).map((Function<? super Object, ?>)PlayerData::lambda$new$9).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        this.location = new PlayerLocation(System.currentTimeMillis(), this.totalTicks, this.entityPlayer.locX, this.entityPlayer.locY, this.entityPlayer.locZ, this.entityPlayer.yaw, this.entityPlayer.pitch, this.entityPlayer.onGround);
        this.lastLocation = this.location.clone();
        this.lastLastLocation = this.location.clone();
        this.alerts = (player.hasMetadata("UCHEAT_ALERTS") && player.hasPermission(OptionsManager.getInstance().getModPermission()));
        this.debug = (player.hasMetadata("UCHEAT_DEBUG") && player.hasPermission("admin.use"));
        this.cps1 = new int[5];
        this.reach1 = new double[5];
    }
    
    private static MovementCheck lambda$new$3(final Check check) {
        return (MovementCheck)check;
    }
    
    private static PacketCheck lambda$new$1(final Check check) {
        return (PacketCheck)check;
    }
    
    public boolean hasLag() {
        return this.hasLag(this.lastFlying);
    }
    
    public PlayerLocation getLocation(final int n) {
        final int n2 = this.recentMoveList.size() - 1;
        return (n2 > n) ? ((PlayerLocation)Iterables.get((Iterable)this.recentMoveList, n2 - n)) : null;
    }
    
    private static boolean lambda$new$8(final Check check) {
        return check instanceof ReachCheck;
    }
    
    public int getHorizontalVelocityTicks() {
        return this.horizontalVelocityTicks;
    }
    
    public EntityPlayer getEntityPlayer() {
        return this.entityPlayer;
    }
    
    public int getTeleportTicks() {
        return this.teleportTicks;
    }
    
    public boolean isTeleporting() {
        return !this.teleportList.isEmpty();
    }
    
    private static Cuboid lambda$null$13(final Cuboid cuboid) {
        return cuboid.expand(0.0325, 0.0, 0.0325);
    }
    
    public double getVelZ() {
        return this.velZ;
    }
    
    public int getAveragePing() {
        return this.averagePing;
    }
    
    private static boolean lambda$new$4(final Check check) {
        return check instanceof AimCheck;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    private void lambda$handle$10(final long n, final MovementCheck movementCheck) {
        movementCheck.handle(this.player, this, this.lastLocation, this.location, n);
    }
    
    public Map<Integer, Deque<PlayerLocation>> getRecentMoveMap() {
        return this.recentMoveMap;
    }
    
    private static boolean lambda$new$2(final Check check) {
        return check instanceof MovementCheck;
    }
    
    public boolean isDebug() {
        return this.debug;
    }
    
    private static boolean lambda$null$12(final long n, final Integer n2, final Double n3, final PlayerLocation playerLocation) {
        return n - playerLocation.getTimestamp() - n2 - 75L <= 25.0 + n3;
    }
    
    public boolean isBanned() {
        return this.banned;
    }
    
    private void processPosition(PlayerLocation clone, final boolean b) {
        if (this.enabled) {
            final long currentTimeMillis = System.currentTimeMillis();
            if (this.recentMoveList.size() > 20) {
                this.recentMoveList.poll();
            }
            clone.setTimestamp(currentTimeMillis);
            this.recentMoveList.add(clone);
            if (!b) {
                for (final PlayerData playerData : PlayerManager.getInstance().getPlayers().values()) {
                    final Deque<PlayerLocation> deque = playerData.getRecentMoveMap().get(this.entityPlayer.getId());
                    if (deque != null && !deque.isEmpty()) {
                        if ((clone = deque.peekLast()) == null) {
                            continue;
                        }
                        clone = clone.clone();
                        clone.setTickTime(playerData.getTotalTicks());
                        clone.setTimestamp(currentTimeMillis);
                        deque.add(clone);
                        if (deque.size() <= 20) {
                            continue;
                        }
                        deque.poll();
                    }
                }
            }
        }
    }
    
    public int getTotalTicks() {
        return this.totalTicks;
    }
    
    public void setLastVelY(final double lastVelY) {
        this.lastVelY = lastVelY;
    }
    
    private static ReachCheck lambda$new$9(final Check check) {
        return (ReachCheck)check;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    private static DistanceData lambda$null$14(final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final Cuboid cuboid) {
        final double distanceXZ = cuboid.distanceXZ(playerLocation.getX(), playerLocation.getZ());
        final double distanceXZ2 = cuboid.distanceXZ(playerLocation2.getX(), playerLocation2.getZ());
        final double n = playerLocation.getX() - cuboid.cX();
        final double n2 = playerLocation2.getX() - cuboid.cX();
        final double n3 = playerLocation.getZ() - cuboid.cZ();
        final double n4 = playerLocation2.getZ() - cuboid.cZ();
        final double n5 = playerLocation.getY() - cuboid.cY();
        final double n6 = playerLocation2.getY() - cuboid.cY();
        return new DistanceData((Math.abs(n) < Math.abs(n2)) ? n : n2, (Math.abs(n3) < Math.abs(n4)) ? n3 : n4, (Math.abs(n5) < Math.abs(n6)) ? n5 : n6, Math.min(distanceXZ2, distanceXZ));
    }
    
    public void handle(final Packet packet, final boolean b) {
        if (this.enabled) {
            if (b) {
                final long currentTimeMillis = System.currentTimeMillis();
                if (packet instanceof PacketPlayInFlying) {
                    final PacketPlayInFlying packetPlayInFlying = (PacketPlayInFlying)packet;
                    final CustomLocation lastMovePacket = new CustomLocation(packetPlayInFlying.a(), packetPlayInFlying.b(), packetPlayInFlying.c(), packetPlayInFlying.d(), packetPlayInFlying.e());
                    final CustomLocation lastMovePacket2 = this.getLastMovePacket();
                    if (lastMovePacket2 != null) {
                        if (!packetPlayInFlying.g()) {
                            lastMovePacket.setX(lastMovePacket2.getX());
                            lastMovePacket.setY(lastMovePacket2.getY());
                            lastMovePacket.setZ(lastMovePacket2.getZ());
                        }
                        if (!packetPlayInFlying.h()) {
                            lastMovePacket.setYaw(lastMovePacket2.getYaw());
                            lastMovePacket.setPitch(lastMovePacket2.getPitch());
                        }
                    }
                    this.setLastMovePacket(lastMovePacket);
                    this.onGround = packetPlayInFlying.g();
                    this.location.setTimestamp(currentTimeMillis);
                    if (packetPlayInFlying.g()) {
                        this.location.setX(packetPlayInFlying.a());
                        this.location.setY(packetPlayInFlying.b());
                        this.location.setZ(packetPlayInFlying.c());
                    }
                    if (packetPlayInFlying.h()) {
                        this.location.setYaw(packetPlayInFlying.d());
                        this.location.setPitch(packetPlayInFlying.e());
                    }
                    this.location.setOnGround(packetPlayInFlying.f());
                    new BukkitRunnable(this, this.location.clone(), packetPlayInFlying) {
                        final PlayerData this$0;
                        final PlayerLocation val$clonedLocation;
                        final PacketPlayInFlying val$packetPlayInFlying;
                        
                        public void run() {
                            PlayerData.access$100(this.this$0, this.val$clonedLocation, this.val$packetPlayInFlying.g() && PlayerData.access$000(this.this$0) < 20);
                        }
                    }.runTask((Plugin)Main.getPlugin());
                    this.packets = ((this.player.getVehicle() == null && this.steerTicks > this.getPingTicks()) ? (packetPlayInFlying.g() ? 0 : (++this.packets)) : 0);
                    this.nonMoveTicks = (packetPlayInFlying.g() ? 0 : (++this.nonMoveTicks));
                    final long n = currentTimeMillis - this.lastFlying;
                    if (n > 80L) {
                        this.lastDelayed = currentTimeMillis;
                    }
                    if (n < 25L) {
                        this.lastFast = currentTimeMillis;
                    }
                    this.lastFlying = currentTimeMillis;
                    this.teleportTicks = (this.isTeleporting() ? 0 : (++this.teleportTicks));
                    this.connectionFrequency.add(50 - (int)n);
                    if (this.abortedDigging) {
                        this.abortedDigging = false;
                        this.swingDigging = false;
                        this.digging = false;
                    }
                    if (this.stoppedDigging) {
                        this.stoppedDigging = false;
                        this.digging = false;
                    }
                    final PlayerLocation playerLocation;
                    if ((playerLocation = this.teleportList.peek()) != null) {
                        final int n2 = this.totalTicks - playerLocation.getTickTime();
                        if (packetPlayInFlying.g() && n2 >= this.getMoveTicks() && playerLocation.sameLocation(this.location)) {
                            this.teleportList.poll();
                            this.lastVelY = 0.0;
                            this.lastVelY2 = 0.0;
                            this.lastVelY3 = 0.0;
                            this.velY = 0.0;
                            this.velX = 0.0;
                            this.velZ = 0.0;
                            this.lastLocation = this.location.clone();
                            this.lastLastLocation = this.location.clone();
                            this.spawnedIn = false;
                            return;
                        }
                        if (n2 > (packetPlayInFlying.g() ? (this.getPingTicks() * 2) : (this.getPingTicks() * 4))) {
                            this.teleportList.poll();
                        }
                    }
                    if (this.lastVelY == 0.0 && this.velY != 0.0) {
                        if (packetPlayInFlying.f()) {
                            this.velY = 0.0;
                        }
                        else {
                            this.velY -= 0.08;
                            this.velY *= 0.98;
                        }
                    }
                    if (packetPlayInFlying.f() && this.teleportTicks > this.getPingTicks()) {
                        this.spawnedIn = true;
                    }
                    ++this.totalTicks;
                    ++this.lastAttackTicks;
                    this.flyingTicks = (this.player.isFlying() ? 0 : (++this.flyingTicks));
                    this.groundTicks = (packetPlayInFlying.f() ? 0 : (++this.groundTicks));
                    ++this.steerTicks;
                    ++this.velocityTicks;
                    ++this.horizontalSpeedTicks;
                    ++this.verticalVelocityTicks;
                    ++this.horizontalVelocityTicks;
                    if (!this.lastLocation.sameLocation(this.location)) {
                        this.movementChecks.forEach((Consumer<? super Object>)this::lambda$handle$10);
                    }
                    if (!this.lastLocation.sameDirection(this.location)) {
                        this.aimChecks.forEach((Consumer<? super Object>)this::lambda$handle$11);
                    }
                    this.lastLastLocation = this.lastLocation.clone();
                    this.lastLocation = this.location.clone();
                    final double deltaY = this.getDeltaY();
                    final double deltaY2 = this.location.getY() - this.lastLocation.getY();
                    this.setLastDeltaY(deltaY);
                    this.setDeltaY(deltaY2);
                    if (this.getLastAttackTicks() <= 1 && this.lastAttacked != null && !this.hasLag() && !this.hasFast() && this.teleportTicks > this.getPingTicks() + 2 && this.lastAttacked.getTeleportTicks() > this.lastAttacked.getPingTicks() + this.getPingTicks() + 2 && !this.lastAttacked.hasLag(currentTimeMillis - this.getPing()) && !this.lastAttacked.hasFast(currentTimeMillis - this.getPing()) && this.player.getGameMode() != GameMode.CREATIVE) {
                        final PlayerLocation clone = this.location.clone();
                        final PlayerLocation clone2 = this.lastLastLocation.clone();
                        final Deque<PlayerLocation> deque = this.recentMoveMap.get(this.lastAttackedId);
                        if (deque != null && !deque.isEmpty() && deque.size() > 10) {
                            this.pingQueue.add((BiConsumer<Integer, Double>)this::lambda$handle$15);
                        }
                    }
                }
                else if (packet instanceof PacketPlayInUseEntity) {
                    final PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity)packet;
                    if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                        if (this.horizontalVelocityTicks > this.getPingTicks() / 2 - 2) {
                            this.velX *= 0.6;
                            this.velZ *= 0.6;
                        }
                        this.lastAttackedId = SafeReflection.getAttackedEntity(packetPlayInUseEntity);
                        final Entity a = packetPlayInUseEntity.a(this.entityPlayer.getWorld());
                        if (a != null) {
                            if (a instanceof EntityPlayer) {
                                final EntityPlayer entityPlayer = (EntityPlayer)a;
                                this.lastAttacked = ((entityPlayer.playerConnection != null) ? PlayerManager.getInstance().getPlayers().get(entityPlayer.getBukkitEntity().getPlayer().getUniqueId()) : null);
                            }
                            else {
                                this.lastAttacked = null;
                            }
                        }
                        else {
                            this.lastAttacked = null;
                        }
                        this.lastAttackTicks = 0;
                    }
                }
                else if (!(packet instanceof PacketPlayInKeepAlive)) {
                    if (packet instanceof PacketPlayInEntityAction) {
                        final PacketPlayInEntityAction packetPlayInEntityAction = (PacketPlayInEntityAction)packet;
                        if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING) {
                            this.sprinting = true;
                        }
                        else if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING) {
                            this.sprinting = false;
                        }
                        else if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING) {
                            this.sneaking = true;
                        }
                        else if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING) {
                            this.sneaking = false;
                        }
                    }
                    else if (packet instanceof PacketPlayInClientCommand) {
                        if (((PacketPlayInClientCommand)packet).a() == PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
                            this.inventoryOpen = true;
                        }
                    }
                    else if (packet instanceof PacketPlayInCloseWindow) {
                        this.inventoryOpen = false;
                    }
                    else if (packet instanceof PacketPlayInBlockDig) {
                        final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
                        if (this.player.getGameMode() == GameMode.CREATIVE) {
                            this.digging = false;
                            this.swingDigging = false;
                        }
                        else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                            this.digging = true;
                            this.swingDigging = true;
                            this.abortedDigging = false;
                            this.stoppedDigging = false;
                        }
                        else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                            this.abortedDigging = true;
                        }
                        else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
                            this.stoppedDigging = true;
                        }
                    }
                    else if (packet instanceof PacketPlayInSteerVehicle) {
                        this.steerTicks = 0;
                    }
                }
                else {
                    final int a2 = ((PacketPlayInKeepAlive)packet).a();
                    this.receivedKeepAlive = true;
                    final Long n3 = this.keepAliveMap.remove(a2);
                    if (n3 != null) {
                        this.ping = (int)(currentTimeMillis - n3);
                        this.averagePing = (this.averagePing * 3 + this.ping) / 4;
                        final double variance = MathUtil.variance(0, this.connectionFrequency);
                        this.recentConnectionFrequencies.add(variance);
                        if (this.recentConnectionFrequencies.size() > 4) {
                            this.recentConnectionFrequencies.poll();
                        }
                        BiConsumer<Integer, Double> biConsumer;
                        while ((biConsumer = this.pingQueue.poll()) != null) {
                            biConsumer.accept(this.ping, variance);
                        }
                        this.connectionFrequency.clear();
                    }
                }
                this.packetChecks.forEach((Consumer<? super Object>)this::lambda$handle$16);
            }
            else {
                final long currentTimeMillis2 = System.currentTimeMillis();
                if (packet instanceof PacketPlayOutPosition) {
                    final long currentTimeMillis3 = System.currentTimeMillis();
                    this.teleportList.add(SafeReflection.getLocation(currentTimeMillis3, this.totalTicks, (PacketPlayOutPosition)packet));
                    this.teleportTicks = 0;
                    this.lastPosition = currentTimeMillis3;
                }
                else if (packet instanceof PacketPlayOutKeepAlive) {
                    final PlayerConnection playerConnection = this.entityPlayer.playerConnection;
                    SafeReflection.setNextKeepAliveTime(playerConnection, SafeReflection.getNextKeepAliveTime(playerConnection) + 36);
                    this.keepAliveMap.put(SafeReflection.getKeepAliveId((PacketPlayOutKeepAlive)packet), currentTimeMillis2);
                }
                else if (packet instanceof PacketPlayOutEntityVelocity) {
                    final VelocityPacket velocity = SafeReflection.getVelocity((PacketPlayOutEntityVelocity)packet);
                    if (velocity.getEntityId() == this.player.getEntityId()) {
                        this.velY = velocity.getY() / 8000.0;
                        this.velX = velocity.getX() / 8000.0;
                        this.velZ = velocity.getZ() / 8000.0;
                        this.horizontalVelocityTicks = 0;
                        new BukkitRunnable(this, this::lambda$handle$17) {
                            final Runnable val$runnable;
                            final PlayerData this$0;
                            
                            public void run() {
                                this.val$runnable.run();
                            }
                        }.runTask((Plugin)Main.getPlugin());
                        this.velocityTicks = Math.min(this.velocityTicks, 0) - (int)Math.ceil(Math.pow(MathUtil.hypotSquared(this.velX, this.velY, this.velZ) * 2.0, 1.75) * 4.0);
                        this.horizontalSpeedTicks = Math.min(this.horizontalSpeedTicks, 0) - (int)Math.ceil(Math.pow(MathUtil.hypotSquared(this.velX, this.velZ) * 2.0, 2.0) * 8.0);
                    }
                }
                else if (packet instanceof PacketPlayOutExplosion) {
                    this.velocityTicks = 0;
                    this.horizontalSpeedTicks = 0;
                }
                else if (packet instanceof PacketPlayOutOpenWindow) {
                    this.digging = false;
                }
                else if (packet instanceof PacketPlayOutEntityTeleport) {
                    final PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = (PacketPlayOutEntityTeleport)packet;
                    final int entityId = SafeReflection.getEntityId(packetPlayOutEntityTeleport);
                    Deque<PlayerLocation> deque2 = this.recentMoveMap.get(entityId);
                    if (deque2 == null) {
                        deque2 = new ConcurrentLinkedDeque<PlayerLocation>();
                        this.recentMoveMap.put(entityId, deque2);
                    }
                    deque2.add(SafeReflection.getLocation(System.currentTimeMillis(), this.totalTicks, packetPlayOutEntityTeleport));
                    if (deque2.size() > 20) {
                        deque2.poll();
                    }
                }
                else if (!(packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMove) && !(packet instanceof PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook)) {
                    if (packet instanceof PacketPlayOutEntityDestroy) {
                        this.recentMoveMap.keySet().removeAll(Arrays.stream(SafeReflection.getEntities((PacketPlayOutEntityDestroy)packet)).boxed().collect((Collector<? super Integer, ?, List<? super Integer>>)Collectors.toList()));
                    }
                }
                else {
                    final PacketPlayOutEntity packetPlayOutEntity = (PacketPlayOutEntity)packet;
                    final Deque<PlayerLocation> deque3 = this.recentMoveMap.get(SafeReflection.getEntityId(packetPlayOutEntity));
                    if (deque3 != null && !deque3.isEmpty()) {
                        final Vector movement = SafeReflection.getMovement(packetPlayOutEntity);
                        assert deque3.peekLast() != null;
                        final PlayerLocation add = deque3.peekLast().clone().add(movement.getX(), movement.getY(), movement.getZ());
                        add.setTickTime(this.totalTicks);
                        add.setTimestamp(System.currentTimeMillis());
                        deque3.add(add);
                        if (deque3.size() > 20) {
                            deque3.poll();
                        }
                    }
                }
            }
        }
    }
    
    public long getLastPosition() {
        return this.lastPosition;
    }
    
    public boolean isSwingDigging() {
        return !this.swingDigging;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void setVelZ(final double velZ) {
        this.velZ = velZ;
    }
    
    public boolean isDigging() {
        return this.digging;
    }
    
    public PlayerLocation getLocation() {
        return this.location;
    }
    
    public Boolean getSprinting() {
        return this.sprinting;
    }
    
    public boolean hasLag(final long n) {
        return (this.lastFlying != 0L && this.lastDelayed != 0L && n - this.lastDelayed < 150L) || System.currentTimeMillis() - this.lastFlying > 90L;
    }
    
    public void setLastDeltaY(final double lastDeltaY) {
        this.lastDeltaY = lastDeltaY;
    }
    
    private static boolean lambda$new$0(final Check check) {
        return check instanceof PacketCheck;
    }
    
    public int getVelocityTicks() {
        return this.velocityTicks;
    }
    
    public int getMoveTicks() {
        return (int)Math.floor(Math.min(this.ping, this.averagePing) / 125.0);
    }
    
    private static EventCheck lambda$new$7(final Check check) {
        return (EventCheck)check;
    }
}
