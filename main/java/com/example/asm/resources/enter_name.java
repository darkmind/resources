package com.example.asm.resources;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class enter_name extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        final character char_o = character.getInstance();

        final EditText enter_char_name = (EditText) findViewById(R.id.char_name);

        enter_char_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    char_o.char_name = enter_char_name.getText().toString();
                    go_main_screen();
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void go_main_screen() {
        Intent intent = new Intent(this, main_screen.class);
        startActivity(intent);
    }
}
