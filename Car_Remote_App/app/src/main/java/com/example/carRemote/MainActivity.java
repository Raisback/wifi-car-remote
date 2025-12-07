package com.example.carRemote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.you_tube_wifi_app.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ImageButton fwbt, bwbt, lftbt, rgtbt;
    TextView txtRES;

    private OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        NetworkRequest request = builder.build();
        connManager.requestNetwork(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                connManager.bindProcessToNetwork(network);
            }
        });


        fwbt = findViewById(R.id.fw);
        bwbt = findViewById(R.id.bw);
        lftbt = findViewById(R.id.lft);
        rgtbt = findViewById(R.id.rgt);
        txtRES = findViewById(R.id.txtRES);


        fwbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCommand("red");
            }
        });

        fwbt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendCommand("fw");
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    sendCommand("stp");
                }
                return false;
            }
        });

        bwbt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendCommand("bw");
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    sendCommand("stp");
                }
                return false;
            }
        });


        lftbt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendCommand("lt");
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    sendCommand("stp");
                }
                return false;
            }
        });

        rgtbt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendCommand("rt");
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    sendCommand("stp");
                }
                return false;
            }
        });



    }

    public void sendCommand(String cmd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String command = "http://192.168.4.1/" + cmd;
                Log.d("Command------------------------------------------", command);
                Request request = new Request.Builder().url(command).build();

                try {
                    Response response = client.newCall(request).execute();
                    String myResponse = response.body().string();
                    final String cleanResponse = myResponse.replaceAll("\\<.*?\\>", ""); // remove HTML tags
                    cleanResponse.replace("\n", ""); // remove all new line characters
                    cleanResponse.replace("\r", ""); // remove all carriage characters
                    cleanResponse.replace(" ", ""); // removes all space characters
                    cleanResponse.replace("\t", ""); // removes all tab characters
                    cleanResponse.trim();
                    Log.d("Response  = ", cleanResponse);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtRES.setText(cleanResponse);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
