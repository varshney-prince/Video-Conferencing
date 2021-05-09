package com.prince.vpmeets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Dashboard extends AppCompatActivity {
    EditText code;
    Button join,share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        code=findViewById(R.id.codeBox);
        join = findViewById(R.id.joinBtn);
        share = findViewById(R.id.shareBtn);
        URL server;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  my=new Intent(Intent.ACTION_SEND);
                my.setType("text/plain");
                String c=share.getText().toString();
                my.putExtra(Intent.EXTRA_TEXT,c);
                startActivity(Intent.createChooser(my,"Share Using .."));
            }
        });
        try {
            server = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions vd=new JitsiMeetConferenceOptions.Builder().setServerURL(server).setWelcomePageEnabled(false).build();
            JitsiMeet.setDefaultConferenceOptions(vd);
        }
        catch (MalformedURLException e){
            e.printStackTrace();

        }

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions call=new JitsiMeetConferenceOptions.Builder().setRoom(code.getText().toString())
                        .setWelcomePageEnabled(false).build();
                JitsiMeetActivity.launch(Dashboard.this,call);

            }
        });
    }
}