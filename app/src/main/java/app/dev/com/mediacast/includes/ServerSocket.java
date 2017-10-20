package app.dev.com.mediacast.includes;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import app.dev.com.mediacast.MainActivity;

/**
 * Created by Dev on 02-10-17.
 */

public class ServerSocket {

    java.net.ServerSocket serverSocket;
    String message = "";
    static final int socketServerPORT = 8000;
    MainActivity activity;

    public ServerSocket (MainActivity activity) {
        this.activity = activity;
        try {
            serverSocket = null;
            Thread socketServerThread = new Thread(new SocketServerThread());
            socketServerThread.start();
            Log.d("Socket","Conn OK");
        }catch (Exception ex){
            Log.e("Socket", "Conn NOK: " + ex.toString());
        }
    }


    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                Log.e("null" ,"!=");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread {

        int count = 0;

        @Override
        public void run() {
            Log.e("Socket", "Run ");
            Socket soc = null;
            try {
                // create ServerSocket using specified port
                try {
                    serverSocket = new java.net.ServerSocket(socketServerPORT);
                    Log.e("Socket", "serverSocket OK ");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    Log.e("Socket", "serverSocket NOK ");
                }

                while(true) {

                    soc = serverSocket.accept();
                    assert soc != null;
                    BufferedInputStream buffer = new BufferedInputStream(soc.getInputStream());
                    final DataOutputStream out = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));

                    /*Recepcion de Mensaje*/
                    /*Lectura por BufferedReader
                     */
                    Log.e("Socket ", "-------BufferedReader--------");
                    try {
                        BufferedReader r = new BufferedReader(
                                new InputStreamReader(buffer));
                        message = "";
                        int value = 0;
                        while((value = r.read()) != -1) {
                            // converts int to character
                            char c = (char)value;
                            // prints character
                            message += c;
                        }

                        Log.e("Socket", " Mensaje recibido" + message);

                        /*Respuesta al Cliente*/
                        try {
                            String response = "OK";
                            out.write(response.getBytes());
                            out.flush();
                            out.close();
                            Log.e("Socket", " Send Reply" + response);
                        }catch(Exception ex){
                            Log.e("Socket", " Send Reply Error:" + ex);
                        }

                    }catch(Exception ex){
                        Log.e("Socket", "ErrorBufferedReader: " + ex.toString() + " => " + message.toString());
                    }

                    /* ENVIA MENSAJE A LA ACTIVITY*/
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Log.e("Socket", "setContent ");
                            activity.setContent(message);
                            /*Respuesta al Cliente*/
                        }
                    });


                }

            } catch (IOException e1) {
                e1.printStackTrace();
                Log.e("Socket", "serverSocket NOK ");
            }
        }
    }

}
