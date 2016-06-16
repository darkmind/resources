package com.example.asm.resources;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class greeting_screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_screen);
    }

    public void run_enter_name(View view) {
        Intent intent = new Intent(this, enter_name.class);
        startActivity(intent);
    }

    public void run_load_screen(View view) {
        Intent intent = new Intent(this, load_char.class);
        startActivity(intent);
    }

    public void exit_app(View view) {
        System.exit(0);
    }
}
