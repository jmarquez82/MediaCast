package app.dev.com.mediacast.includes;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import app.dev.com.mediacast.MainActivity;

/**
 * Created by Dev on 02-10-17.
 */

public class ServerSocket {

    java.net.ServerSocket serverSocket;
    String message = "";
    static final int socketServerPORT = 8080;
    MainActivity activity;

    public ServerSocket (MainActivity activity) {
        this.activity = activity;
        try {
            serverSocket = null;
            Thread socketServerThread = new Thread(new SocketServerThread());
            socketServerThread.start();
            Log.d("Conexión","OK");
        }catch (Exception ex){
            Log.e("Conexión", "Falló: " + ex.toString());
        }

    }

    public int getPort() {
        return socketServerPORT;
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
            Log.e("Conexión status", "Run ");
            Socket soc = null;
            try {
                // create ServerSocket using specified port
                try {
                    serverSocket = new java.net.ServerSocket(socketServerPORT);
                    Log.e("Conexión status", "serverSocket OK ");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    Log.e("Conexión status", "serverSocket NOK ");
                }

                while(true) {

                    soc = serverSocket.accept();
                    assert soc != null;
                    DataInputStream in = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
                    final DataOutputStream out = new DataOutputStream(new BufferedOutputStream(soc.getOutputStream()));
                    /*Recepcion de Mensaje*/
                    message = in.readUTF();
                    Log.e("Conexión status", "message OK");
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Log.e("Conexión status", "setContent ");
                            activity.setContent(message);
                        }
                    });
                    Log.d("URL:", message);

                    /*Respuesta al Cliente*/
                    String response = "OKI";
                    out.write(response.getBytes());
                    out.flush();
                    out.close();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
                Log.e("Conexión status", "serverSocket2 OK ");
            }
        }
    }


    public static String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }



}
