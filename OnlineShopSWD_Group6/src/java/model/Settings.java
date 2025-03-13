package model;

public class Settings {
    private int settingID;
    private String settingType;
    private String settingValue;
    private String status;

    public Settings() {
    }

    public Settings(int settingID, String settingType, String settingValue, String status) {
        this.settingID = settingID;
        this.settingType = settingType;
        this.settingValue = settingValue;
        this.status = status;
    }

    public int getSettingID() {
        return settingID;
    }

    public void setSettingID(int settingID) {
        this.settingID = settingID;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
