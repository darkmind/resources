package com.example.asm.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class load_char extends ListActivity {

    final private List<String> fileList = new ArrayList<>();

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
        String file_name;
        for (File file : files){
            file_name = file.getName();
            if( file_name.equals("instant-run") ){
                continue;
            }
            fileList.add(file_name);
        }

        ArrayAdapter<String> directoryList
                = new ArrayAdapter<>(this, R.layout.activity_load_char, fileList);
        setListAdapter(directoryList);
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filename = (String) adapterView.getItemAtPosition(i);
                load_char_file(filename);
            }
        });
    }

    private void load_char_file ( String filename ){
        Context context = this;

        FileInputStream fis;
        try {
            fis = context.openFileInput(filename);
        }
        catch ( FileNotFoundException e) {
            throw new RuntimeException( "Can't open file <" + filename + ">" );
        }
        ObjectInputStream is;
        character char_o;
        try {
            is = new ObjectInputStream(fis);
            char_o = (character) is.readObject();
            is.close();
            fis.close();
        }
        catch ( IOException e ) {
            throw new RuntimeException( "IO error <" + filename + ">" );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( "Class wasn't found <" + filename + ">" );
        }

        Intent intent = new Intent(this, full_char_list.class);
        intent.putExtra( "CHAR", char_o );
        startActivity(intent);

        finish();
    }
}
