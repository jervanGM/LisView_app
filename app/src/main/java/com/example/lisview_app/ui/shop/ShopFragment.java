package com.example.lisview_app.ui.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.lisview_app.MainActivity;
import com.example.lisview_app.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ShopFragment extends Fragment {
    Button send_home;
    EditText texto_home;
    ArrayList<Names> listado_home;
    ArrayList<Boolean> checked_list;
    boolean  item_selected=false;
    int position_selected;
    boolean isLoaded=false;
    NamesListAdapter adaptador;
    SwipeMenuListView listaTareas;
    public boolean isLoaded() {
        return isLoaded;
    }
    public static ShopFragment newInstance(String s) {
        ShopFragment sh=new ShopFragment();
        Bundle args=new Bundle();
        args.putString("string",s);
        sh.setArguments(args);
        return sh;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_shop, container, false);
        send_home=(Button)v.findViewById(R.id.ButtonPrueba);
        texto_home=(EditText)v.findViewById(R.id.Texte);
        listaTareas=(SwipeMenuListView)v.findViewById(R.id.List);
        listado_home=new ArrayList<Names>();
        checked_list=new ArrayList<>();
        listaTareas.setChoiceMode(listaTareas.CHOICE_MODE_MULTIPLE );
        adaptador= new NamesListAdapter(getActivity(),R.layout.custom_layout,listado_home,checked_list);
        listaTareas.setAdapter(adaptador);
        send_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item_selected==false){
                    Names name = new Names(texto_home.getText().toString());
                    listado_home.add(name);
                    checked_list.add(false);
                }
                else{
                    TextView text=(TextView) listaTareas.getChildAt(position_selected).findViewById(R.id.listtext);
                    text.setText(texto_home.getText().toString());
                    Names name_replace = new Names(text.getText().toString());
                    listado_home.set(position_selected,name_replace);

                    item_selected=false;
                    adaptador.notifyDataSetChanged();
                }
                texto_home.setText("");
            }
        });



        listaTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(item_selected==false){
                TextView text=(TextView) listaTareas.getChildAt(position).findViewById(R.id.listtext);
                text.setPaintFlags(text.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                checked_list.set(position,!checked_list.get(position));
                System.out.println(checked_list.get(position).toString());
                adaptador.notifyDataSetChanged();
}
            }

        });
        listaTareas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                position_selected=position;
                item_selected=true;
                texto_home.setText(listado_home.get(position).getNames());
                return false;
            }
        });


        listaTareas.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            final View e=v;
            @Override
            public void onSwipeStart(int position) {
                final int posicion=position;

                new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_delete).setTitle("Eliminar Tarea")
                        .setMessage("¿Quiere eliminar esta tarea?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listado_home.remove(posicion);
                        checked_list.remove(posicion);
                        //Guardar(e);
                        adaptador.notifyDataSetChanged();
                    }
                }).setNegativeButton("No",null).show();
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        // Inflate the layout for this fragment
        return v;
    }



    private boolean ArchivoExiste(String archivos[], String NombreArchivo){
        for(int i=0;i<archivos.length;i++)
            if(NombreArchivo.equals(archivos[i])) return true;
        return false;
    }
    public void Load(String NombreArchivo){
        String archivos[]=getActivity().fileList();
        if(ArchivoExiste(archivos,NombreArchivo)){
            try {
                listado_home.clear();
                checked_list.clear();
                InputStreamReader archivo = new InputStreamReader(getActivity().openFileInput(NombreArchivo));
                BufferedReader br= new BufferedReader(archivo);
                String linea= br.readLine();
                while(linea !=null ){

                    listado_home.add(new Names(linea));
                    linea=br.readLine();
                    checked_list.add(Boolean.valueOf(linea));
                    linea=br.readLine();
                }

                br.close();
                archivo.close();

                adaptador.notifyDataSetChanged();

            }catch (IOException e){

            }
        }
    }
    //Save method
    public void Save(String file){
        try {
                OutputStreamWriter archivo = new OutputStreamWriter(getActivity().openFileOutput(file, Activity.MODE_PRIVATE));
                if (listado_home != null || listado_home.size() > 0) {
                    for (int i = 0; i < listado_home.size(); i++) {
                        archivo.write(listado_home.get(i).getNames());
                        archivo.append(System.getProperty("line.separator"));
                        archivo.write(checked_list.get(i).toString());
                        archivo.append(System.getProperty("line.separator"));
                    }
                    archivo.flush();
                    archivo.close();
                }

        }catch(IOException e){

        }


    }
    public ArrayList<Boolean> checked(){
        return checked_list;
    }
}