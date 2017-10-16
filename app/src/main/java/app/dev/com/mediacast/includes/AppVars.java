package app.dev.com.mediacast.includes;

import android.os.Environment;

import java.io.File;

/**
 * Created by Dev21 on 02-10-17.
 */

public class AppVars {
    /*Generals params*/
    public static String appBasePath = "SourceRinno/";
    public static String appName = "FundamentaApp/";
    public static String pathHome = appBasePath + appName;
    public static String pathHomeStatic = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + pathHome;

    /*Statics Media*/
    public static String[] mediaDirs = {"videos","images","others"};
    public static String fileDefault = "Object.json";


    /*Customs params App*/
    public static String videoname = "entel_video.mp4";
    public static File carpetaHome = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Source/DeviceAccesorios");
    public static File inactividad = new File(carpetaHome.getAbsolutePath()+"/inactividad/");
}
