package com.example.canvasviewapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.stephenvinouze.materialnumberpickercore.MaterialNumberPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog numberPickerDialog;
    private FloatingActionButton PathColor, PathStroke;
    private MaterialNumberPicker numberPicker;
    private MyCanvasView myCanvasView;

    private int colorSelected = R.color.black;
    private int strokeSelected = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCanvasView = findViewById(R.id.mycanvasview);
        PathColor = findViewById(R.id.color);
        PathStroke = findViewById(R.id.stroke);
        PathColor.setOnClickListener(this);
        PathStroke.setOnClickListener(this);

        numberPicker = new MaterialNumberPicker(this);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);

        AlertDialog.Builder numberPickerDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Pick Path Stroke")
                .setView(numberPicker)
                .setNegativeButton(getString(android.R.string.cancel), null)
                .setPositiveButton(getString(android.R.string.ok), (dialogInterface, i) -> {
                    strokeSelected = numberPicker.getValue();
                    myCanvasView.setPathStrokeWidth(numberPicker.getValue());
                    numberPicker.removeAllViews();
                });
        numberPickerDialog = numberPickerDialogBuilder.create();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.color){
            showColorPickerDialog();
        } else if (id == R.id.stroke) {
            showNumberPickerDialog();
        }
    }

    private void showColorPickerDialog() {
        new ColorPickerDialog.Builder(this)
                .setTitle("Pick Path Color")
                .setColorShape(ColorShape.SQAURE)
                .setDefaultColor(colorSelected)
                .setColorListener((ColorListener) (color, colorHex) -> {
                    colorSelected = color;
                    myCanvasView.setPathColor(color);
                    PathColor.setBackgroundColor(color);
                    PathStroke.setBackgroundColor(color);
                }).show();
    }

    private void showNumberPickerDialog() {
        numberPicker.setValue(strokeSelected);
        numberPickerDialog.show();
    }
}