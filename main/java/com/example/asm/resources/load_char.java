package com.example.asm.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class load_char extends ListActivity {

    private List<String> fileList = new ArrayList<String>();
    Map<String, Integer> sp_res = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this;
        File root = new File(context.getFilesDir().getAbsolutePath());
        ListDir(root);

    }

    private void ListDir(File f){
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files){
            fileList.add(file.getName());
        }

        ArrayAdapter<String> directoryList
                = new ArrayAdapter<String>(this, R.layout.activity_load_char, fileList);
        setListAdapter(directoryList);
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Going read");
                String filename = (String) adapterView.getItemAtPosition(i);
                System.out.println("Going read from " + filename );
                load_char_file(filename);
            }
        });
    }

    private void load_char_file ( String filename ){
        Context context = this;
        Properties properties = new Properties();
        FileInputStream handler;
        System.out.println("Going to try");
        try {
            handler = new FileInputStream( context.getFilesDir().getAbsolutePath() + "/" +  filename);
            System.out.println("Going to open");
            properties.load(handler);
            System.out.println("Going to fill");
            for (String key : properties.stringPropertyNames()) {
                Integer val = Integer.parseInt( properties.get(key).toString() );
                sp_res.put(key, val);
            }
            character char_o = character.getInstance();
            char_o.char_name = filename;
            char_o.set_sp_res( sp_res );

            System.out.println("Going to main");
            Intent intent = new Intent(this, main_screen.class);
            startActivity(intent);
        }
        catch ( FileNotFoundException e ) {
            e.printStackTrace();
            System.exit(0);
        }
        catch ( IOException e ) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
