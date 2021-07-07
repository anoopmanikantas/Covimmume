package com.example.covimmune;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String Name;
    static EditText name;
    EditText addhouse;
    EditText addstreet;
    EditText addcs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    // OnClickListener can be used. This is an alternative method.
    // Takes the user to the next activity.

    public void btnclick(View view){

        name = findViewById(R.id.name);
        addhouse = findViewById(R.id.AddHouse);
        addstreet = findViewById(R.id.AddStreet);
        addcs = findViewById(R.id.AddCS);
        System.out.println(name.getText().toString());
        System.out.println(addhouse.getText().toString());
        System.out.println(addstreet.getText().toString());
        System.out.println(addcs.getText().toString());

        // If the fields aren't filled properly, a toast message will be shown to the user.

        if (name.getText().toString().length() == 0 ||
                addhouse.getText().toString().length() == 0 ||
                addstreet.getText().toString().length() == 0 ||
                addcs.getText().toString().length() == 0){
            String message = "Please fill in all the fields";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        // else the user will be taken to the next activity.

        else {
            Intent intent = new Intent(MainActivity.this, Second.class);
            startActivity(intent);
        }
    }

    public static String getName(){
        return String.valueOf(name);
    }
}

