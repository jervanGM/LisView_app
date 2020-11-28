package com.example.lisview_app.ui.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lisview_app.MainActivity;
import com.example.lisview_app.R;

public class AddPage_Activity extends AppCompatActivity {
    Button red,blue,green,yellow,pink,cancel,ok;
    EditText page_name;
    String color,text;
    void Setup(){
        red=(Button)findViewById(R.id.Red);
        blue=(Button)findViewById(R.id.Blue);
        green=(Button)findViewById(R.id.Green);
        yellow=(Button)findViewById(R.id.Yellow);
        pink=(Button)findViewById(R.id.Pink);
        cancel=(Button)findViewById(R.id.Cancel);
        ok=(Button)findViewById(R.id.buttonOK);
        page_name=(EditText)findViewById(R.id.TextaddPage);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page_);
        Setup();
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="red";
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="blue";
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="green";
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="yellow";
            }
        });
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="pink";
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_main = new Intent(AddPage_Activity.this, MainActivity.class);
                if(color=="") color="other";
                String mensaje="";
                if(page_name.getText().toString().equals(""))mensaje="Nueva lista";
                else mensaje=page_name.getText().toString();
                System.out.println(mensaje);
                activity_main.putExtra("Color",""+color);
                activity_main.putExtra("Nombre",""+mensaje);
                setResult(1,activity_main);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
