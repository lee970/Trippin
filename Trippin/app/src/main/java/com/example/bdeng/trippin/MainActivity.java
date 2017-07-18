package com.example.bdeng.trippin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLoginActivity(View view)
    {
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }
    public void goToCreateAccount(View view)
    {
        Intent intent = new Intent (this, SignupActivity.class);
        startActivity(intent);
    }
    public void goToCreateGroup(View view)
    {
        Intent intent = new Intent (this, CreateGroupActivity.class);
        startActivity(intent);
    }
}
