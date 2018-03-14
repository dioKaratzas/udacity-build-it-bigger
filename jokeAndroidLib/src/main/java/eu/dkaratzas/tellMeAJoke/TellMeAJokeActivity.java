package eu.dkaratzas.tellMeAJoke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TellMeAJokeActivity extends AppCompatActivity {
    public static final String EXTRA_JOKE = "EXTRA_JOKE";

    private TextView tvJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_a_joke);
        tvJoke = findViewById(R.id.jokeValue);

        displayJoke();
    }

    private void displayJoke() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_JOKE)) {
            String joke = intent.getStringExtra(EXTRA_JOKE);
            tvJoke.setText(joke);
        }
    }
}
