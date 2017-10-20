package app.dev.com.mediacast.includes;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Dev on 02-10-17.
 * TCP Client
 */

public class ConexionTCP extends AsyncTask<Void, Void, Void>
{
    public interface OnReadListener
    {
        public void onRead(ConexionTCP client, String response);
    }

    String dstAddress;
    int dstPort;
    String response = "";
    OnReadListener listener = null;
    String message;

    public ConexionTCP(String addr, int port, String message)
    {
        this.dstAddress = addr;
        this.dstPort = port;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... arg0)
    {

        Socket socket = null;

        try
        {
            socket = new Socket(dstAddress, dstPort);

            //Send message
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            BufferedInputStream buffer = new BufferedInputStream(socket.getInputStream());
            out.write(message.getBytes());
            out.flush();
            out.close();

            //Request Message

            Log.e("Socket ", "-------BufferedReader--------");
            try {
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(buffer));
                response = "";
                int value = 0;
                response = r.readLine();
                while((value = r.read()) != -1) {
                    // converts int to character
                    char c = (char)value;
                    // prints character
                    response += c;
                }
                r.close();
                Log.e("Socket", " Mensaje recibido" + response);

            }catch(Exception ex){
                Log.e("Socket", "ErrorBufferedReader: " + ex.toString() + " => " + response.toString());
            }





        } catch (UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally
        {
            if (socket != null)
            {
                try
                {
                    socket.close();
                    Log.i("Socket","Socket close");
                } catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    Log.i("Socket","Socket not close");
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        //textResponse.setText(response);
        Log.i("Socket","onPostExecute:" + response + " r:" + result);
        super.onPostExecute(result);

    }


    public void setListener(OnReadListener listener)
    {
        this.listener = listener;
    }
}

