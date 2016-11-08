package com.example.owner.doodle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public DoodleView doodleView;
    SeekBar seekBar_Red;
    TextView textView_Red;
    SeekBar seekBar_Blue;
    TextView textView_Blue;
    SeekBar seekBar_Green;
    TextView textView_Green;
    SeekBar seekBar_Brush;
    TextView textView_Brush;
    SeekBar seekBar_Opacity;
    TextView textView_Opacity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doodleView = (DoodleView)findViewById(R.id.draw);
        seekbarr();

        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.clearCanvas();
            }
        });

        Switch rainbowSwitch = (Switch) findViewById(R.id.rainbowSwitch);
        rainbowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                doodleView.setRainbow(isChecked);
            }
        });

    }

    public void seekbarr() {
        seekBar_Red = (SeekBar)findViewById(R.id.seekBarRed);
        textView_Red = (TextView)findViewById(R.id.textViewRed);
        textView_Red.setText(String.valueOf(seekBar_Red.getProgress()));

        seekBar_Blue = (SeekBar)findViewById(R.id.seekBarBlue);
        textView_Blue = (TextView)findViewById(R.id.textViewBlue);
        textView_Blue.setText(String.valueOf(seekBar_Blue.getProgress()));

        seekBar_Green = (SeekBar)findViewById(R.id.seekBarGreen);
        textView_Green = (TextView)findViewById(R.id.textViewGreen);
        textView_Green.setText(String.valueOf(seekBar_Green.getProgress()));

        seekBar_Brush = (SeekBar) findViewById(R.id.seekBarBrush);
        textView_Brush = (TextView)findViewById(R.id.textViewBrush);
        textView_Brush.setText(String.valueOf(seekBar_Brush.getProgress()));

        seekBar_Opacity = (SeekBar) findViewById(R.id.seekBarOpacity);
        textView_Opacity = (TextView)findViewById(R.id.textViewOpacity);
        textView_Opacity.setText(String.valueOf(seekBar_Opacity.getProgress()));

        textView_Red.setText("Red: 0");
        textView_Blue.setText("Blue: 0");
        textView_Green.setText("Green: 0");
        textView_Brush.setText("Brush Size: 10");
        textView_Opacity.setText("Opacity: 255");

        textView_Red.setTextColor(Color.RED);
        textView_Blue.setTextColor(Color.BLUE);
        textView_Green.setTextColor(Color.GREEN);
        textView_Brush.setTextColor(Color.BLACK);
        textView_Opacity.setTextColor(Color.BLACK);

        seekBar_Red.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_val = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_val = progress;
                        textView_Red.setText("Red: " + progress_val);
                        //Toast.makeText(MainActivity.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView_Red.setText("Red: " + progress_val);
                        doodleView.setPaintRed(progress_val);
                        //Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );

        seekBar_Blue.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_val = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_val = progress;
                        textView_Blue.setText("Blue: " + progress_val);
                        //Toast.makeText(MainActivity.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView_Blue.setText("Blue: " + progress_val);
                        doodleView.setPaintBlue(progress_val);
                        //Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );

        seekBar_Green.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_val = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_val = progress;
                        textView_Green.setText("Green: " + progress_val);
                        //Toast.makeText(MainActivity.this, "SeekBar in Progress", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView_Green.setText("Green: " + progress_val);
                        doodleView.setPaintGreen(progress_val);
                        //Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );


        seekBar_Brush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_val = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_val = progress;
                textView_Brush.setText("Brush Size: " + progress_val);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                textView_Brush.setText("Brush Size: " + progress_val);
                doodleView.setSize(progress_val);
            }
        });

        seekBar_Opacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_val = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_val = progress;
                textView_Opacity.setText("Opacity: " + progress_val);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                textView_Opacity.setText("Opacity: " + progress_val);
                doodleView.setAlpha(progress_val);
            }
        });
    }



}
