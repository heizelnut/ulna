package cc.heiz.ulna;

import static android.util.Log.ASSERT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_play;
    ListView webradioList;
    NotificationManager notificationManager;
    List<WebRadio> radios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String personal_code = sharedPref.getString("personal_code", "");
        if (personal_code.equals("")) {
            String uuid = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("personal_code", uuid);
            editor.apply();
        }
        Log.println(Log.INFO, "Personal code", personal_code);

        setRadios();
        webradioList = findViewById(R.id.webradio_list);
        webradioList.setAdapter(new WebRadioItemAdapter(this, radios));
        webradioList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), PlayerFaceplate.class);
            intent.putExtra("radio", radios.get(position));
            startActivity(intent);
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID, "Ulna", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

    }
    private void setRadios() {
        radios = new ArrayList<>();
        radios.add(new WebRadio("RTL 102.5", "https://streamingv2.shoutcast.com/rtl-1025", R.drawable.rtl_1025));
        radios.add(new WebRadio("Kiss Kiss", "https://kisskiss.fluidstream.eu/KissKiss.mp3", R.drawable.kisskiss));
    }
}