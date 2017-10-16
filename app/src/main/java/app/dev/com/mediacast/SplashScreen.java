package app.dev.com.mediacast;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import app.dev.com.mediacast.includes.AppVars;
import app.dev.com.mediacast.includes.Functions;
import app.dev.com.mediacast.includes.Permissions;

/**
 * Created by Dev21 on 02-10-17.
 */

public class SplashScreen extends AppCompatActivity {
    SplashScreen act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        Fresco.initialize(this);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Toast.makeText(SplashScreen.this, "onPreExecute ", Toast.LENGTH_SHORT).show();

                Permissions permissions = new Permissions(act);
                if(permissions.isStoragePermission() && permissions.isStoragePermissionRead()) {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(SplashScreen.this, "No dio permisos ", Toast.LENGTH_SHORT).show();
                }
                try{
                    Thread. sleep(3000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                //Crear archivo base
                Functions.createFileAndDir(AppVars.pathHome, AppVars.fileDefault);
                //Crear directorios y subdirectorios necesarios
                if(!Functions.createDirs(AppVars.mediaDirs)){
                    Log.e("Error","Al crear directorios base");
                    Toast.makeText(SplashScreen.this, "Warning: No se pudo crear directorios bases para la aplicación. ", Toast.LENGTH_SHORT).show();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(SplashScreen.this, "Run ", Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int i=0; i < grantResults.length;i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SplashScreen.this, "Permiso " + permissions[i] + " was " + grantResults[0], Toast.LENGTH_SHORT).show();

                Intent ip = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(ip);
                finish();
            }
        }
    }
}


