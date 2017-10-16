package app.dev.com.mediacast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.dev.com.mediacast.includes.ConexionTCP;
import app.dev.com.mediacast.includes.ServerSocket;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientSocket extends AppCompatActivity {


    int port = 8080;

    @BindView(R.id.txt_ip2)
    TextView txtIp;

    @BindView(R.id.cajaIp)
    EditText cajaIp;
    @BindView(R.id.cajaMsg)
    EditText cajaMsg;

    @BindView(R.id.btnClient)
    TextView btnClient;
    @BindView(R.id.txt2)
    TextView txt;
    @BindView(R.id.v1)
    Button v1;
    @BindView(R.id.i1)
    Button i1;
    @BindView(R.id.g1)
    Button g1;
    @BindView(R.id.v2)
    Button v2;
    @BindView(R.id.i2)
    Button i2;
    @BindView(R.id.g2)
    Button g2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_socket);
        ButterKnife.bind(this);
        txtIp.setText(ServerSocket.getIpAddress());
        String json = "{\n" +
                "  \"type\": \"image\",\n" +
                "  \"value\": \"http://url.com\"\n" +
                "}";
        cajaMsg.setText(json);
    }

    /**
     * Eventos OnClicks
     *
     * @param view
     */

    @OnClick({R.id.v1, R.id.i1, R.id.g1, R.id.v2, R.id.i2, R.id.g2,R.id.btnClient})
    public void onViewClicked(View view) {

        String json = "";
        switch (view.getId()) {
            case R.id.btnClient:
                ConexionTCP cc = new ConexionTCP(cajaIp.getText().toString(), port, cajaMsg.getText().toString());
                cc.execute();
                //finish();
                break;
            case R.id.v1:
                 json = "{\n" +
                        "  \"type\": \"video\",\n" +
                        "  \"value\": \"1.mp4\"\n" +
                        "}";
                ConexionTCP cc1 = new ConexionTCP(cajaIp.getText().toString(), port, json);
                cc1.execute();
                break;
            case R.id.i1:
                json = "{\n" +
                        "  \"type\": \"image\",\n" +
                        "  \"value\": \"http://isabelgueren.com/uploads/imgkiUJkxtcgi_ortada.jpg\"\n" +
                        "}";
                ConexionTCP cc2 = new ConexionTCP(cajaIp.getText().toString(), port, json);
                cc2.execute();
                break;
            case R.id.g1:
                json = "{\n" +
                        "  \"type\": \"map\",\n" +
                        "  \"value\": \"-33.400601, -70.651336\"\n" +
                        "}";
                ConexionTCP cc3 = new ConexionTCP(cajaIp.getText().toString(), port, json);
                cc3.execute();
                break;
            case R.id.v2:
                json = "{\n" +
                        "  \"type\": \"video\",\n" +
                        "  \"value\": \"2.mp4\"\n" +
                        "}";
                ConexionTCP cc4 = new ConexionTCP(cajaIp.getText().toString(), port, json);
                cc4.execute();
                break;
            case R.id.i2:
                json = "{\n" +
                        "  \"type\": \"image\",\n" +
                        "  \"value\": \"https://www.portalinmobiliario.com/proyimgs/5438_3_pe.jpg\"\n" +
                        "}";
                ConexionTCP cc5 = new ConexionTCP(cajaIp.getText().toString(), port, json);
                cc5.execute();
                break;
            case R.id.g2:
                json = "{\n" +
                        "  \"type\": \"map\",\n" +
                        "  \"value\": \"-33.454611, -70.692005\"\n" +
                        "}";
                ConexionTCP cc6 = new ConexionTCP(cajaIp.getText().toString(), port, json);
                cc6.execute();
                break;
        }
    }
}
