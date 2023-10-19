package com.example.cachedemo.command;

import jakarta.validation.constraints.Size;

public class DataCommand {
    private String dataName;
    @Size(min = 1, max = 2)
    private String ipAddress;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
