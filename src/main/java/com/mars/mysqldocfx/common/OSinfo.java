package com.mars.mysqldocfx.common;

import lombok.Data;

/**
 * @Program: MysqlDocFx
 * @ClassName:OSinfo
 * @Description:
 * @Author: shenchengjian
 * @Create: 2020-07-03 19:42
 **/
@Data
/**
 * 操作系统类： 
 * 获取System.getProperty("os.name")对应的操作系统 
 */
public class OSinfo {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    private static OSinfo _instance = new OSinfo();

    private EPlatformEnum platform;

    private OSinfo(){}

    public static boolean isLinux(){
        return OS.indexOf("linux")>=0;
    }

    public static boolean isMacOS(){
        return OS.indexOf("mac")>=0&&OS.indexOf("os")>0&&OS.indexOf("x")<0;
    }

    public static boolean isMacOSX(){
        return OS.indexOf("mac")>=0&&OS.indexOf("os")>0&&OS.indexOf("x")>0;
    }

    public static boolean isWindows(){
        return OS.indexOf("windows")>=0;
    }

    public static boolean isOS2(){
        return OS.indexOf("os/2")>=0;
    }

    public static boolean isSolaris(){
        return OS.indexOf("solaris")>=0;
    }

    public static boolean isSunOS(){
        return OS.indexOf("sunos")>=0;
    }

    public static boolean isMPEiX(){
        return OS.indexOf("mpe/ix")>=0;
    }

    public static boolean isHPUX(){
        return OS.indexOf("hp-ux")>=0;
    }

    public static boolean isAix(){
        return OS.indexOf("aix")>=0;
    }

    public static boolean isOS390(){
        return OS.indexOf("os/390")>=0;
    }

    public static boolean isFreeBSD(){
        return OS.indexOf("freebsd")>=0;
    }

    public static boolean isIrix(){
        return OS.indexOf("irix")>=0;
    }

    public static boolean isDigitalUnix(){
        return OS.indexOf("digital")>=0&&OS.indexOf("unix")>0;
    }

    public static boolean isNetWare(){
        return OS.indexOf("netware")>=0;
    }

    public static boolean isOSF1(){
        return OS.indexOf("osf1")>=0;
    }

    public static boolean isOpenVMS(){
        return OS.indexOf("openvms")>=0;
    }

    /**
     * 获取操作系统名字 
     * @return 操作系统名
     */
    public static EPlatformEnum getOSname(){
        if(isAix()){
            _instance.platform = EPlatformEnum.AIX;
        }else if (isDigitalUnix()) {
            _instance.platform = EPlatformEnum.Digital_Unix;
        }else if (isFreeBSD()) {
            _instance.platform = EPlatformEnum.FreeBSD;
        }else if (isHPUX()) {
            _instance.platform = EPlatformEnum.HP_UX;
        }else if (isIrix()) {
            _instance.platform = EPlatformEnum.Irix;
        }else if (isLinux()) {
            _instance.platform = EPlatformEnum.Linux;
        }else if (isMacOS()) {
            _instance.platform = EPlatformEnum.Mac_OS;
        }else if (isMacOSX()) {
            _instance.platform = EPlatformEnum.Mac_OS_X;
        }else if (isMPEiX()) {
            _instance.platform = EPlatformEnum.MPEiX;
        }else if (isNetWare()) {
            _instance.platform = EPlatformEnum.NetWare_411;
        }else if (isOpenVMS()) {
            _instance.platform = EPlatformEnum.OpenVMS;
        }else if (isOS2()) {
            _instance.platform = EPlatformEnum.OS2;
        }else if (isOS390()) {
            _instance.platform = EPlatformEnum.OS390;
        }else if (isOSF1()) {
            _instance.platform = EPlatformEnum.OSF1;
        }else if (isSolaris()) {
            _instance.platform = EPlatformEnum.Solaris;
        }else if (isSunOS()) {
            _instance.platform = EPlatformEnum.SunOS;
        }else if (isWindows()) {
            _instance.platform = EPlatformEnum.Windows;
        }else{
            _instance.platform = EPlatformEnum.Others;
        }
        return _instance.platform;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(OSinfo.getOSname());
    }

}  
