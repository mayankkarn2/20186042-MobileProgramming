package com.example.dictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game extends AppCompatActivity {
    TextView question;
    EditText answer;
    Button Submit;
    Button Quit;

    private static final String TAG = "Game";
    private static boolean play = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        question = (TextView) findViewById(R.id.question);
        answer = (EditText) findViewById(R.id.answer);
        Submit = (Button) findViewById(R.id.game_sub);
        Quit = (Button) findViewById(R.id.quit);
        Map<String, Object> m = null;
        try {
            m = new fetchData(Game.this).fetchdata();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

            final String[] set = generateString(m).split("#");
            if (set[0].length() >= 2) {
                question.setText(set[0]);
            }
            final Map<String, Object> finalM = m;
            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(finalM, set[1]);
                }
            });
    }

    public String generateString(Map<String, Object> map) {
        Random random = new Random();
        List<String> keys = new ArrayList<>(map.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        String value = (String) map.get(randomKey);
        Log.v(TAG, " "+randomKey);
        int max = randomKey.length()-1;
        int min = 0;
        int randoNum1 = min + random.nextInt((max - min)-1);
        int randoNum2 = min + random.nextInt((max - min)-1);
        int randoNum3 = min + random.nextInt((max - min)-1);
        StringBuilder copy = new StringBuilder(randomKey);
        copy.setCharAt(randoNum1, '_');
        copy.setCharAt(randoNum2, '_');
        copy.setCharAt(randoNum3, '_');
        return copy.toString()+"#"+randomKey+"#"+value;
    }

    public void checkAnswer(Map<String, Object> m, String answers) {
            if(answers.equals(answer.getText().toString())) {
                Toast.makeText(Game.this, "Correct Guess", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Game.this, "Incorrect Guess", Toast.LENGTH_SHORT).show();
            }
    }
}
