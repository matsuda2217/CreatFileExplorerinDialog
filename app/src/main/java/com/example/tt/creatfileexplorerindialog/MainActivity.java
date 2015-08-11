package com.example.tt.creatfileexplorerindialog;

import android.app.Dialog;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    Button btnOpen ;
    File curFolder;
    File dir;
    TextView textFolder;
    List<String> arrayList = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpen = (Button) findViewById(R.id.btnOpenDialog) ;
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
        dir =  Environment.getExternalStorageDirectory().getAbsoluteFile();
        curFolder = dir;

    }

    @Override
    protected Dialog onCreateDialog(int id) {
      Dialog dialog = null;
        switch (id) {
            case 1:
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_activity);
                dialog.setTitle("Custom Dialog");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                Button btnUp = (Button) dialog.findViewById(R.id.btnUp);
                btnUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });
                textFolder = (TextView) dialog.findViewById(R.id.textFolder);
                listView = (ListView)dialog.findViewById(R.id.listView);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(arrayList.get(position));
                        ListDir(selected);
                    }
                });


                break;

        }

        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case 1:
                ListDir(curFolder);
                break;
        }

    }

    public void ListDir(File file) {
        curFolder = file;
        textFolder.setText(file.getPath());
        Log.d("TAG", file.getPath());
        File[] files = file.listFiles();

            arrayList.clear();
            for (File f : files) {
                arrayList.add(f.getPath());
            }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
