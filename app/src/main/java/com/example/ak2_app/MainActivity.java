package com.example.ak2_app;

import android.app.ActivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    long total_time;
    int sizeOfTab = 20000;
    double availableMegs;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView apes = findViewById(R.id.imageViewapes);// zdjecie
        apes.setImageResource(R.drawable.apes);

        TextView wynik = findViewById(R.id.wynik);
        TextView ram = findViewById(R.id.ram);
        TextView timeScore = findViewById(R.id.score);

        wynik.setVisibility(View.INVISIBLE);
        ram.setVisibility(View.INVISIBLE);

        Button btn = findViewById(R.id.btnStartTest); //inicjalizacja guzika

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        availableMegs = mi.availMem / 0x100000L;
        ram.setText(String.valueOf(availableMegs) + " MB Wolnego RAMU ");
        ram.setVisibility(View.VISIBLE);

        TextView modelName = findViewById(R.id.modelName);
        modelName.setText(getPhoneModel());

        Thread threadBenchmarkSort = new Thread(() -> {
            float score = 0;
            int helper;
            Random random = new Random();
            int[] IntArray = new int[sizeOfTab];
            int random_num;
            for (int i = 0; i < sizeOfTab; i++) {
                random_num = random.nextInt(20000);
                IntArray[i] = random_num;
            }
            long startTime = System.nanoTime(); //poczatek pomiaru czasu
            for (int i = 0; i < sizeOfTab - 1; i++) {
                for (int j = 0; j < sizeOfTab - 1; j++) {
                    if (IntArray[j] > IntArray[j + 1]) {
                        helper = IntArray[j];
                        IntArray[j] = IntArray[j + 1];
                        IntArray[j + 1] = helper;
                    }
                }
            }
            addingInt();
            empty_loop();
            subtraction();
            multi();
            long endTime = System.nanoTime();//koniec pomairu czasu
            float duration = (endTime - startTime) / 100000;
            score = ((duration / 5870) - 1) * (-100);
            wynik.setText(String.valueOf(df.format(score)) + "% Szybsze od urządzenia testowego");
            timeScore.setText("Score: " + String.valueOf(duration));

        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadBenchmarkSort.run();
                wynik.setVisibility(View.VISIBLE);
                availableMegs = mi.availMem / 0x100000L;
                ram.setText(String.valueOf(availableMegs) + " MB Wolnego RAMU");
                setMemForLaw();
            }
        });
    }

    public void empty_loop() {
        for (int i = 0; i < sizeOfTab; i++) {
        }
    }

    public void addingInt() {
        int help = 0;
        for (int i = 0; i < sizeOfTab; i++) {
            help = help + 5;
        }
    }

    public void subtraction() {
        int help = 0;
        for (int i = 0; i < sizeOfTab; i++) {
            help = help - 5;
        }
    }

    public void multi() {
        int help = 0;
        for (int i = 0; i < sizeOfTab; i++) {
            help = sizeOfTab * sizeOfTab;
        }
    }

    private String getPhoneModel() {
        return Build.MODEL +" Android " + Build.VERSION.RELEASE;
    }

    private void setMemForLaw(){
        Random random = new Random();
        int losowa = 0;
        losowa = random.nextInt(6);
        ImageView apes = findViewById(R.id.imageViewapes);// zdjecie
        apes.setImageResource(R.drawable.apes);
        switch(losowa){
            case 0:
                apes.setImageResource(R.drawable.mem1);
                break;
            case 1:
                apes.setImageResource(R.drawable.mem2);
                break;
            case 2:
                apes.setImageResource(R.drawable.mem3);
                break;
            default:
                apes.setImageResource(R.drawable.mem1);
                break;
        }
    }

}



