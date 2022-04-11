package com.lighter.data.manager;

import java.util.*;
import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.google.common.collect.*;

public class CheckManager
{
    private static CheckManager instance;
    private Map<Check.CheckType, Boolean> checks;
    
    public static CheckManager getInstance() {
        return (CheckManager.instance == null) ? (CheckManager.instance = new CheckManager()) : CheckManager.instance;
    }
    
    public void enableType(final Check.CheckType checkType, final Player player) {
        if (this.validate(checkType)) {
            this.checks.put(checkType, true);
            if (player != null) {
                this.saveChecks();
            }
        }
    }
    
    public boolean enabled(final Check.CheckType checkType) {
        return this.checks.get(checkType);
    }
    
    private boolean validate(final Check.CheckType checkType) {
        return this.checks.containsKey(checkType);
    }
    
    public void disableType(final Check.CheckType checkType, final Player player) {
        if (this.validate(checkType)) {
            this.checks.put(checkType, false);
            if (player != null) {
                this.saveChecks();
            }
        }
    }
    
    private void saveChecks() {
        for (final Check.CheckType checkType : Check.CheckType.values()) {
            OptionsManager.getInstance().getConfigurationCheck().set(String.valueOf(new StringBuilder().append("checktypes.").append(checkType.getName()).append(".enabled")), (Object)this.enabled(checkType));
        }
        OptionsManager.saveConfigCheck();
    }
    
    private void loadChecks() {
        for (final Check.CheckType checkType : Check.CheckType.values()) {
            this.checks.put(checkType, OptionsManager.getInstance().getConfigurationCheck().getBoolean(String.valueOf(new StringBuilder().append("checktypes.").append(checkType.getName()).append(".enabled"))));
        }
    }
    
    public void enable() {
        this.checks = (Map<Check.CheckType, Boolean>)Maps.newConcurrentMap();
        this.loadChecks();
    }
    
    public void disable() {
        this.saveChecks();
        this.checks = null;
        CheckManager.instance = null;
    }
    
    public void reloadCheck(final Check.CheckType checkType) {
        if (this.enabled(checkType)) {
            this.checks.put(checkType, false);
            this.checks.put(checkType, true);
            this.saveChecks();
        }
        else {
            this.checks.put(checkType, false);
        }
    }
}
