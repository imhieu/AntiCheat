package com.lighter.check.impl;

public class Alert
{
    private final Check check;
    private final String data;
    private final int vl;
    private final long timestamp;
    
    public Check getCheck() {
        return this.check;
    }
    
    public String getData() {
        return this.data;
    }
    
    public int getVl() {
        return this.vl;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public Alert(final Check check, final String data, final int vl) {
        this.timestamp = System.currentTimeMillis();
        this.check = check;
        this.data = data;
        this.vl = vl;
    }
}
