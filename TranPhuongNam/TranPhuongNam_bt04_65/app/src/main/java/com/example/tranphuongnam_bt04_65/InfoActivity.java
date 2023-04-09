package com.example.tranphuongnam_bt04_65;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    TextView etName, etPhonenum, etEmail, etBd;
    ImageView imImage;
    Button btnEdit;
    Contact contact;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
            etName = findViewById(R.id.tvName);
            etPhonenum = findViewById(R.id.tvNumber);
            etEmail = findViewById(R.id.tvGmail);
            imImage = findViewById(R.id.ivName);
            etBd = findViewById(R.id.etBd);
            Intent intent = getIntent();
            contact = (Contact) intent.getSerializableExtra("contact");
//            etName.setText(contact.getName());
            etPhonenum.setText(contact.getPhonenumber());
            etEmail.setText(contact.getGmail());
            etBd.setText(contact.getBirthday());
//            imImage.setImageResource(contact.getImage());

            getSupportActionBar().setTitle(R.string.info);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}