package cc.heiz.ulna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class PlayerFaceplate extends AppCompatActivity {
    ImageView cover_art;
    ImageButton play_pause;
    TextView title;
    SeekBar volume;
    MediaPlayer mediaPlayer;
    private final int MAX_VOLUME = 100;
    ListeningSession session;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_faceplate);
        Intent intent = getIntent();
        WebRadio to_play = (WebRadio) intent.getSerializableExtra("radio");
        String uuid = intent.getStringExtra("user");
        session = new ListeningSession(to_play, uuid);

        CreateNotification.createNotification(PlayerFaceplate.this, to_play, true);

        cover_art = findViewById(R.id.faceplate_image);
        play_pause = findViewById(R.id.faceplate_play_pause);
        title = findViewById(R.id.faceplate_title);

        cover_art.setImageResource(to_play.image);
        play_pause.setImageResource(R.drawable.pause);
        title.setText(to_play.title);

        volume = findViewById(R.id.faceplate_volume);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(to_play.url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "IO Exception", Toast.LENGTH_LONG).show();
        }
        session.startSession();
        mediaPlayer.start();
        play_pause.setImageResource(R.drawable.pause);
        play_pause.setOnClickListener(view -> {
            if (!mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 1) {
                mediaPlayer.start();
                play_pause.setImageResource(R.drawable.pause);
                CreateNotification.createNotification(PlayerFaceplate.this, to_play, true);
            } else {
                mediaPlayer.pause();
                play_pause.setImageResource(R.drawable.play_arrow);
                CreateNotification.createNotification(PlayerFaceplate.this, to_play, false);
            }
        });
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final float volume = (float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                mediaPlayer.setVolume(volume, volume);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        session.stopSession();
        session.send(PlayerFaceplate.this);
        mediaPlayer.release();
        CreateNotification.clear(PlayerFaceplate.this);
    }
}