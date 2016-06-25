package com.example.asm.resources;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class enter_name extends Activity {
    String char_name;
    String chronic_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        final EditText enter_char_name = (EditText) findViewById(R.id.char_name);
        enter_char_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    char_name = enter_char_name.getText().toString();
                }
                return false;
            }
        });

        final EditText enter_chronic_name = (EditText) findViewById(R.id.chronic_name);
        enter_chronic_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    chronic_name = enter_chronic_name.getText().toString();
                }
                return false;
            }
        });
    }

    private void go_main_screen() {
        Intent intent = new Intent(this, full_char_list.class);
        character char_o = new character( char_name, chronic_name );
        intent.putExtra( "CHAR", char_o );
        startActivity(intent);
        finish();
    }

    public void create_character(View view) {
        go_main_screen();
    }
}
