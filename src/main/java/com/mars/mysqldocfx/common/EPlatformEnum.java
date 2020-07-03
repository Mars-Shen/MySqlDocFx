package com.mars.mysqldocfx.common;

/**
 * 平台
 */
public enum EPlatformEnum {
    Any("any"),
    Linux("Linux"),
    Mac_OS("Mac OS"),
    Mac_OS_X("Mac OS X"),
    Windows("Windows"),
    OS2("OS/2"),
    Solaris("Solaris"),
    SunOS("SunOS"),
    MPEiX("MPE/iX"),
    HP_UX("HP-UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FreeBSD("FreeBSD"),
    Irix("Irix"),
    Digital_Unix("Digital Unix"),
    NetWare_411("NetWare"),
    OSF1("OSF1"),
    OpenVMS("OpenVMS"),
    Others("Others");

    EPlatformEnum(String desc){
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    private String description;

    public static EPlatformEnum getEnum(String description){
        for(EPlatformEnum ePlatformEnum: EPlatformEnum.values()){
            if(ePlatformEnum.getDescription().equals(description)){
                return ePlatformEnum;
            }
        }
        return null;
    }
}