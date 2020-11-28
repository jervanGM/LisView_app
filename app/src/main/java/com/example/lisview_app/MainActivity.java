package com.example.lisview_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.example.lisview_app.ui.info.Info_activity;
import com.example.lisview_app.ui.shop.Names;
import com.example.lisview_app.ui.shop.ShopFragment;
import com.example.lisview_app.ui.shop.ViewPageAdapter;
import com.example.lisview_app.ui.task.AddPage_Activity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private static final String TAG="MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    ViewPageAdapter viewPage;
    ViewPager views;
    TabLayout tabs ;
    ArrayList<String> dato=new ArrayList<String>();
    ImageButton deletePage;
    ArrayList<String> nombre_pagina=new ArrayList<>();
    ArrayList<String> background=new ArrayList<>();
    int i=0;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    int stato=0;
    SharePref sharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharePref= new SharePref(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer =(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toogle);
        toogle.syncState();
        //A partir de aqui se pueden añadir cosas

        views = findViewById(R.id.Page);
        tabs= findViewById(R.id.tabs);
        tabs.setupWithViewPager(views);


        viewPage = new ViewPageAdapter( getSupportFragmentManager(),dato);
        views.setAdapter(viewPage);
        Cargar();
        viewPage.notifyDataSetChanged();
        for (int j = 0; j < nombre_pagina.size(); j++)
            tabs.getTabAt(j).setText(nombre_pagina.get(j));
        if(sharePref.loadNightModeState()==true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        navigationView = findViewById(R.id.nav_view);
        deletePage=(ImageButton)findViewById(R.id.deleteButton);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                FragmentManager fragmentManager;
                ShopFragment fragment =(ShopFragment) viewPage.getRegisteredFragment(getState());
                ShopFragment shopFragment=(ShopFragment) fragment;
                if(id==R.id.nav_gallery){
                    shopFragment.Save(Integer.toString(getState())+".txt");
                    Intent activity2 = new Intent(MainActivity.this, AddPage_Activity.class);
                    startActivityForResult(activity2,1);
                }
                else if(id==R.id.nav_tools){
                    shopFragment.Save(Integer.toString(getState())+".txt");
                    Intent activityNight = new Intent(MainActivity.this, DayNightActivity.class);
                    startActivity(activityNight);
                }
                else if(id==R.id.nav_info){
                    shopFragment.Save(Integer.toString(getState())+".txt");
                    Intent activity_info = new Intent(MainActivity.this, Info_activity.class);
                    startActivity(activity_info);
                }
                else if(id==R.id.nav_save){
                    List<Fragment> fragments=getSupportFragmentManager().getFragments();
                    for(int g=0;g<fragments.size();g++) shopFragment.Save(Integer.toString(g)+".txt");
                    Toast.makeText(MainActivity.this,"Guardando datos",Toast.LENGTH_SHORT).show();
                }

                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        views.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(background.get(position).equals("red")){
                    if(sharePref.loadNightModeState()==false)views.setBackgroundColor(Color.RED);
                    else views.setBackgroundColor(Color.parseColor("#990000"));
                }
                else if(background.get(position).equals("blue")) {
                    if(sharePref.loadNightModeState()==false)views.setBackgroundColor(Color.parseColor("#0080ff"));
                    else views.setBackgroundColor(Color.BLUE);
                }
                else if(background.get(position).equals("green")){
                    if(sharePref.loadNightModeState()==false)views.setBackgroundColor(Color.GREEN);
                    else views.setBackgroundColor(Color.parseColor("#006600"));
                }
                else if(background.get(position).equals("yellow")) {
                    if(sharePref.loadNightModeState()==false)views.setBackgroundColor(Color.YELLOW);
                    else  views.setBackgroundColor(Color.parseColor("#999900"));
                }
                else if(background.get(position).equals("pink")) {
                    if(sharePref.loadNightModeState()==false)views.setBackgroundColor(Color.parseColor("#ff69b4"));
                    else views.setBackgroundColor(Color.parseColor("#99004c"));
                }
                else {
                    if(sharePref.loadNightModeState()==false)views.setBackgroundColor(Color.WHITE);
                    else views.setBackgroundColor(Color.BLACK);
                }
                ShopFragment fragment =(ShopFragment) viewPage.getRegisteredFragment(getState());
                ShopFragment shopFragment=(ShopFragment) fragment;
                shopFragment.Load(Integer.toString(getState())+".txt");

            }

            @Override
            public void onPageSelected(int position) {
                stato=position;
                Bundle bundle = new Bundle();
                bundle.putString("position", Integer.toString(position));
// set Fragmentclass Arguments
                ShopFragment fragobj = new ShopFragment();
                fragobj.setArguments(bundle);
                final int posicion=position;
                deletePage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(dato.size()>1) {
                            new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Eliminar Lista")
                                    .setMessage("¿Quiere eliminar esta lista?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {
                                        for(int i=posicion;i<dato.size()-1;i++) copyFileUsingStream(Integer.toString(i + 1) + ".txt", Integer.toString(i) + ".txt");
                                        deleteFile(Integer.toString(dato.size()-1) + ".txt");
                                    }catch (IOException e){

                                    }
                                    dato.remove(posicion);
                                    viewPage.notifyDataSetChanged();
                                    background.remove(posicion);
                                    nombre_pagina.remove(posicion);

                                    Guardar();
                                    for (int j = 0; j < nombre_pagina.size(); j++) tabs.getTabAt(j).setText(nombre_pagina.get(j));
                                    views.setCurrentItem(posicion-1,true);
                                }
                            }).setNegativeButton("No",null).show();


                        }

                    }
                });

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                ShopFragment fragment =(ShopFragment) viewPage.getRegisteredFragment(getState());
                ShopFragment shopFragment=(ShopFragment) fragment;
                    if(state==1) shopFragment.Save(Integer.toString(getState())+".txt");

            }
        });
    }
    public int getState() {
        return stato;
    }
    private void Cargar() {
        String archivos_main[]=this.fileList();



        if(ArchivoExiste(archivos_main,"paginas.txt")){
            try {
                InputStreamReader archivo0 = new InputStreamReader(this.openFileInput("paginas.txt"));
                BufferedReader br= new BufferedReader(archivo0);
                String linea= br.readLine();
                while(linea !=null ){

                    dato.add(linea);
                    linea=br.readLine();
                }
                br.close();
                archivo0.close();
            }catch (IOException e){
                dato.add("0");
            }
        }
        else dato.add("0");
        if(ArchivoExiste(archivos_main,"background.txt")){
            try {
                InputStreamReader archivo1 = new InputStreamReader(this.openFileInput("background.txt"));
                BufferedReader br= new BufferedReader(archivo1);
                String linea= br.readLine();
                while(linea !=null ){

                    background.add(linea);
                    linea=br.readLine();
                }
                br.close();
                archivo1.close();
            }catch (IOException e){
                background.add("white");
            }
        }
        else background.add("white");
        if(ArchivoExiste(archivos_main,"nombres.txt")){
            try {
                InputStreamReader archivo2 = new InputStreamReader(this.openFileInput("nombres.txt"));
                BufferedReader br= new BufferedReader(archivo2);
                String linea= br.readLine();
                while(linea !=null ){

                    nombre_pagina.add(linea);
                    linea=br.readLine();
                }
                br.close();
                archivo2.close();
            }catch (IOException e){
                nombre_pagina.add("Home_page");
            }
        }
        else {
            nombre_pagina.add("Home_page");
        }
    }

    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if(requestCode==1) {
                nombre_pagina.add(data.getStringExtra("Nombre"));
                background.add(data.getStringExtra("Color"));
                i++;
                dato.add(Integer.toString(i));
                viewPage.notifyDataSetChanged();
                Guardar();
                for (int j = 0; j < nombre_pagina.size(); j++)
                    tabs.getTabAt(j).setText(nombre_pagina.get(j));
            }

        }
    }
    public ArrayList<String> getMyData() {
        return dato;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private boolean ArchivoExiste(String archivos[], String NombreArchivo){
        for(int i=0;i<archivos.length;i++)
            if(NombreArchivo.equals(archivos[i])) return true;
        return false;
    }

    public void Guardar(){
        try {
            OutputStreamWriter archivo0 = new OutputStreamWriter(this.openFileOutput("paginas.txt", Activity.MODE_PRIVATE));
            OutputStreamWriter archivo1= new OutputStreamWriter(this.openFileOutput("background.txt",Activity.MODE_PRIVATE));
            OutputStreamWriter archivo2= new OutputStreamWriter(this.openFileOutput("nombres.txt",Activity.MODE_PRIVATE));
            for(int i=0;i<dato.size();i++){
                archivo0.write(dato.get(i).toString());
                archivo0.append(System.getProperty("line.separator"));
            }
            archivo0.flush();
            for(int i=0;i<background.size();i++){
                archivo1.write(background.get(i).toString());
                archivo1.append(System.getProperty("line.separator"));
            }
            archivo1.flush();
            for(int i=0;i<nombre_pagina.size();i++){
                archivo2.write(nombre_pagina.get(i).toString());
                archivo2.append(System.getProperty("line.separator"));
            }
            archivo2.flush();
            archivo0.close();
            archivo1.close();
            archivo2.close();
        }catch (IOException e){

        }
        Toast.makeText(this, "Save Mode",Toast.LENGTH_SHORT).show();

    }

    private void copyFileUsingStream(String source, String dest) throws IOException {
        InputStreamReader is = null;
        OutputStreamWriter os = null;
        try {
            is = new InputStreamReader(this.openFileInput(source));
            os= new OutputStreamWriter(this.openFileOutput(dest,Activity.MODE_PRIVATE));
            BufferedReader br= new BufferedReader(is);
            String linea= br.readLine();
            while(linea !=null ){
                os.write(linea);
                os.append(System.getProperty("line.separator"));
                linea=br.readLine();
            }

            br.close();
            is.close();
            os.close();

        }catch (IOException e){

        }
    }
}
