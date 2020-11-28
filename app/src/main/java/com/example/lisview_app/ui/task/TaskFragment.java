package com.example.lisview_app.ui.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lisview_app.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class TaskFragment extends Fragment {
    Button send_home;
    EditText texto_home;
    ArrayList listado_home;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_task, container, false);
        Intent activity2 = new Intent(getActivity(),AddPage_Activity.class);
        startActivityForResult(activity2,0);
        return v;
    }
    private boolean ArchivoExiste(String archivos[], String NombreArchivo){
        for(int i=0;i<archivos.length;i++)
            if(NombreArchivo.equals(archivos[i])) return true;
        return false;
    }
    //Save method
    public void Guardar(View v){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(getActivity().openFileOutput("task.txt", Activity.MODE_PRIVATE));
            for(int i=0;i<listado_home.size();i++){
                archivo.write(listado_home.get(i).toString());
                archivo.append(System.getProperty("line.separator"));
            }
            archivo.flush();
            archivo.close();
        }catch (IOException e){

        }
        Toast.makeText(getContext(), "Save Mode",Toast.LENGTH_SHORT).show();

    }
}