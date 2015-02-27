package slydin.speedkana;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * SpeedKana tests your kana recognition skills, be sure to sound off the characters.
 * Copyright (C) 2015  Jeric Derama
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class SpeedActivity extends ActionBarActivity {

    private static final String TAG = "speedkana-SpeedActivity";
    private CountDownTimer timer;
    private TextView speedView;
    private String[] levelArray;
    private int interval_mill;
    private int totalInterval;

    private double interval = 0.8;
    private int level = 1;
    private String fileName = "hiragana_level_1";
    private int section = 0;
    private int index = 0;
    private Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        // Get the text view
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            interval = extras.getDouble("spinnerValue");
            level = extras.getInt("levelValue");
            section = extras.getInt("section");
        }
        Log.i(TAG, "Section: " + section);
        if (section == 1)
            switch (level) {
                case 1:
                    fileName = "hiragana_level_1";
                    break;
                case 2:
                    fileName = "hiragana_level_2";
                    break;
                case 3:
                    fileName = "hiragana_level_3";
                    break;
                case 4:
                    fileName = "hiragana_level_4";
                    break;
            }
        else if (section == 2)
            switch (level) {
                case 1:
                    fileName = "katakana_level_1";
                    break;
                case 2:
                    fileName = "katakana_level_2";
                    break;
                case 3:
                    fileName = "katakana_level_3";
                    break;
                case 4:
                    fileName = "katakana_level_4";
                    break;
            }

        AssetManager am = getAssets();

        InputStream is;
        BufferedReader reader;
        String line;
        ArrayList<String> fileLevelArray = new ArrayList<String>();
        try{
            is = am.open(fileName);
            reader = new BufferedReader(new InputStreamReader(is));

            while((line = reader.readLine()) != null){
                fileLevelArray.add(line);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        String[] tempLevelArray = fileLevelArray.toArray(new String[fileLevelArray.size()]);
        //String[] tempLevelArray = getApplicationContext().getResources().getStringArray(levelResource);
        // Randomize the array
        levelArray = randomFill(tempLevelArray);
        interval_mill = (int) (interval * 1000);
        totalInterval = interval_mill * levelArray.length + interval_mill;

        // Display TextView
        speedView = (TextView) findViewById(R.id.speedView);
        timer = new CountDownTimer(totalInterval, interval_mill) {
            @Override
            public void onTick(long millisUntilFinished) {
                String character = levelArray[index];
                index++;
                speedView.setText(character);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        super.onBackPressed();
    }

    public String[] randomFill(String[] array) {
        int n = array.length;
        int[] list = new int[n];
        String[] sList = new String[n];
        int location = 1;
        int freeCount = n;
        int counter = 0;
        for (int i = 1; i < n; i++) {
            int skip = (int) Math.floor(freeCount * r.nextFloat() + 1);
            while (skip > 0) {
                counter++;
                location = (location + 1) % n;
                if (list[location] == 0)
                    skip = skip - 1;
            }
            list[location] = i;
            sList[location] = array[i];
            freeCount = freeCount - 1;
        }
        return sList;
    }
}
