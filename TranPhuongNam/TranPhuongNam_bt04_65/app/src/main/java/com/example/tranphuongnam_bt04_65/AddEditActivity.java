package com.example.tranphuongnam_bt04_65;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Random;

public class AddEditActivity extends AppCompatActivity {
    TextInputEditText edfName, edlName, edEmail, edPhone, edBirthday, edDes;
    TextInputLayout tifName, tilName, tiEmail, tiPhone, tiBirthday;
    int mYear, mMonth, mDay;
    int flag;
    Contact contactEdit;
    ImageView imgAvatar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle saveUnstanceState) {
        super.onCreate(saveUnstanceState);
        setContentView(R.layout.activity_add_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tifName = findViewById(R.id.tiFname);
        tilName = findViewById(R.id.tiLname);
        tiEmail = findViewById(R.id.tiEmail);
        tiPhone = findViewById(R.id.tiPhone);
        tiBirthday = findViewById(R.id.tiBd);

        imgAvatar = findViewById(R.id.imgunknow);

        edfName = findViewById(R.id.etFirstname);
        edlName = findViewById(R.id.etLastname);
        edEmail = findViewById(R.id.etEmail);
        edPhone = findViewById(R.id.etPhonenum);
        edBirthday = findViewById(R.id.etBd);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        if (flag == 1) {
            getSupportActionBar().setTitle(R.string.add);
        }
        else {
            getSupportActionBar().setTitle(R.string.edit);
            contactEdit = (Contact) intent.getSerializableExtra("contact");
            edfName.setText(contactEdit.getFirstname());
            edlName.setText(contactEdit.getLastname());
            edEmail.setText(contactEdit.getGmail());
            edPhone.setText(contactEdit.getPhonenumber());
            edBirthday.setText(contactEdit.getBirthday());
//            imgAvatar.setImageResource(contactEdit.getImage());
       }

            edBirthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar calendar = Calendar.getInstance();
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get(Calendar.MONTH);
                    mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            edBirthday.setText ( dayOfMonth + "/" + String.format("%02d",month+1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnSave){
            if(edfName.getText().toString().isEmpty()
                    ||edlName.getText().toString().isEmpty()
                    ||edEmail.getText().toString().isEmpty()
                    ||edPhone.getText().toString().isEmpty()
                    ||edBirthday.getText().toString().isEmpty()){
                tifName.setError("Not null");
                tilName.setError("Not null");
                tiEmail.setError("Not null");
                tiPhone.setError("Not null");
                tiBirthday.setError("Not null");
                return false;
            }else {
                if(flag == 1){

                    Contact contact = new Contact(new Random().nextInt(9999),
                            edfName.getText().toString(),
                            edlName.getText().toString(),
                            "",
                            edPhone.getText().toString(),
                            "",
                            edBirthday.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("contact", contact);
                    intent.putExtra("flag", 1);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    String Name = edfName.getText().toString() + " " +edlName.getText().toString();
                    Contact contact = new Contact(contactEdit.getId(),
                            edfName.getText().toString(),
                            edlName.getText().toString(),
                            contactEdit.getImage(),
                            edEmail.getText().toString(),
                            edPhone.getText().toString(),
                            edBirthday.getText().toString()
                            );

                    Intent intent = new Intent();
                    intent.putExtra("contact", contact);
                    intent.putExtra("flag", 2);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
