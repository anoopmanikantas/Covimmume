package com.example.covimmune;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Second extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    int years = 0;
    Spinner spinner;
    String ph = "";

    // onClickListeners waits for the user to press the button, and then specified operation
    // will be performed.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_second);

        // The user is presented with a calendar after clicking the Date of Birth Button.
        findViewById(R.id.DoB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // List of Genders added to the dropdown list.
        // "Gender is a class".
        spinner = findViewById(R.id.spinner);
        List<Gender> genderlist  = new ArrayList<>();
        Gender sex = new Gender("  Male");
        genderlist.add(sex);
        Gender sex2 = new Gender("  Female");
        genderlist.add(sex2);
        Gender sex3 = new Gender("  Other");
        genderlist.add(sex3);

        // Here the user selected element will be displayed.
        // To do so Adapters are used.
        ArrayAdapter<Gender> adapter = new ArrayAdapter<Gender>(this,
                android.R.layout.simple_spinner_item, genderlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // When an item is selected or when it isn't, actions to be performed are specified.

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor(
                        "#B7B2B2"));

                Gender gender = (Gender) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showDatePickerDialog(){

        // Calendar is displayed to the user and the values are saved upon selection.

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O) // This api is required to run java libraries.
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        // Here the age is calculated based on the user's DOB and current date

        String DATE_FORMAT = "yyyy MM dd";
        SimpleDateFormat simple = new SimpleDateFormat(DATE_FORMAT);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd");
        LocalDateTime now = LocalDateTime.now();
        String to = now.format(dtf);
        String DOB = String.valueOf(year) + " " + String.valueOf(month) + " " +
                String.valueOf(dayOfMonth);
        try {
            Date date1 = simple.parse(DOB);
            Date date2 = simple.parse(to);
            long date1InMs = date1.getTime();
            long date2InMs = date2.getTime();
            long timeDiff = 0;
            if(date1InMs > date2InMs) {
                timeDiff = date1InMs - date2InMs;
            } else {
                timeDiff = date2InMs - date1InMs;
            }
            int daysDiff = (int) (timeDiff / (1000 * 60 * 60* 24));
            years = (int) (daysDiff/365.2425);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void submit(View v){
        Gender gender = (Gender) spinner.getSelectedItem();
        MainActivity.getName();
        EditText Phone = findViewById(R.id.Phone);

        // If all the fields are entered in the activity, the user can submit the form.

        if (years!=0 ||
                Phone.getText().toString().length() != 0 ||
                Phone.getText().toString().length() == 10) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(Second.this,
                    R.style.CustomAlertDialog);
            ViewGroup viewGroup = findViewById(android.R.id.content);

            // If the user is of age 45+ he/she is eligible to get vaccinated.

            if (years >= 45) {
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialogue,
                        viewGroup,
                        false);
                Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // upon clicking the OK button, the alert pop-up will be closed.

                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }

            // If the user is less than 45, he/she is not eligible to be vaccinated.

            else {
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.notdialogue,
                        viewGroup,
                        false);
                Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }

        // If the fields aren't filled properly, Toast message is shown to the user.

        else{
            String message = "Please fill in all the fields or make sure that the " +
                    "data filled is correct";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }


    // DEPRECATED: used to toast the Sex selected from dropdown list [test purposes only].
    public void displayUserData(Gender gender){
        String sex = gender.getSex();

        String userData = "Sex: " + sex;
        Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

    // Takes the user back to the first activity.
    public void backbtn(View v){
        super.onBackPressed();
    }
}
