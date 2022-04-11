package com.lighter.data.manager;

import java.util.*;
import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.google.common.collect.*;

public class VlManager
{
    private Map<Check.CheckType, Integer> vl;
    private static VlManager instance;
    
    public static VlManager getInstance() {
        return (VlManager.instance == null) ? (VlManager.instance = new VlManager()) : VlManager.instance;
    }
    
    private void saveChecks() {
        for (final Check.CheckType checkType : Check.CheckType.values()) {
            OptionsManager.getInstance().getConfigurationCheck().set(String.valueOf(new StringBuilder().append("checktypes.").append(checkType.getName()).append(".max-vl")), (Object)this.vl_to_ban(checkType));
        }
        OptionsManager.saveConfigCheck();
    }
    
    public void removeVl(final Check.CheckType checkType, final Player player, final Integer n) {
        if (this.validate(checkType)) {
            this.vl.put(checkType, n);
            if (player != null) {
                this.saveChecks();
            }
        }
    }
    
    private boolean validate(final Check.CheckType checkType) {
        return this.vl.containsKey(checkType);
    }
    
    public void disable() {
        this.saveChecks();
        this.vl = null;
        VlManager.instance = null;
    }
    
    private void loadVl() {
        for (final Check.CheckType checkType : Check.CheckType.values()) {
            this.vl.put(checkType, OptionsManager.getInstance().getConfigurationCheck().getInt(String.valueOf(new StringBuilder().append("checktypes.").append(checkType.getName()).append(".max-vl"))));
        }
    }
    
    public void enable() {
        this.vl = (Map<Check.CheckType, Integer>)Maps.newConcurrentMap();
        this.loadVl();
    }
    
    public void addVl(final Check.CheckType checkType, final Player player, final Integer n) {
        if (this.validate(checkType)) {
            this.vl.put(checkType, n);
            if (player != null) {
                this.saveChecks();
            }
        }
    }
    
    public Integer vl_to_ban(final Check.CheckType checkType) {
        return this.vl.get(checkType);
    }
}
