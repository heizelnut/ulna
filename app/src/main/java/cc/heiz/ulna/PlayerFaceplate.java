package cc.heiz.ulna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayerFaceplate extends AppCompatActivity {
    ImageView cover_art;
    ImageButton play_pause;
    TextView title;
    SeekBar volume;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_player_faceplate);
        Intent intent = getIntent();

        WebRadio to_play = (WebRadio) intent.getSerializableExtra("radio");
        CreateNotification.createNotification(PlayerFaceplate.this, to_play, true);

        cover_art = findViewById(R.id.faceplate_image);
        play_pause = findViewById(R.id.faceplate_play_pause);
        title = findViewById(R.id.faceplate_title);

        cover_art.setImageResource(to_play.image);
        play_pause.setImageResource(R.drawable.pause);
        title.setText(to_play.title);

        volume = findViewById(R.id.faceplate_volume);

        play_pause.setOnClickListener(view -> {

        });
    }
}