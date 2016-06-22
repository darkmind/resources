package com.example.asm.resources;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class full_char_list extends Activity {
    String[] class_names = { "Воин", "Маг", "Жрец" };
    String[] aligments = { "LG", "NG", "CG", "LN", "NN", "CN", "LE", "NE", "CE"  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_char_list);

        ListView lv_aligments = (ListView) findViewById(R.id.charlist_alignment);
        ArrayAdapter<String> adapter_al = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, aligments);
        lv_aligments.setAdapter(adapter_al);

        ListView lv_classes = (ListView) findViewById(R.id.charlist_alignment);
        ArrayAdapter<String> adapter_cl = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, class_names);
        lv_classes.setAdapter(adapter_cl);
    }
}
