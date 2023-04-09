package com.example.tranphuongnam_bt04_65;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tranphuongnam_bt04_65.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements ContactAdapter.Listener  {

    RecyclerView rvContacts;
    ImageView imgadd;

    ArrayList<Contact> contacts;
    ContactAdapter contactAdapter;
    int postion;
    DBHelper dbHelper;

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                 public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == RESULT_OK){
                        if (result.getData().getIntExtra("flag", 0) == 1){
                            Contact contact = (Contact) result.getData().getSerializableExtra("contact");
                            contactAdapter.addContact(contact);
                        }else {
                            Contact contact = (Contact) result.getData().getSerializableExtra("contact");
                            contactAdapter.editContact(contact,postion);
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(MainActivity.this);
        rvContacts = findViewById(R.id.rvContacts);
        //App app = new App();
        contacts = dbHelper.getContact() ;
        //Log.d("ABC", contacts.size()+"");
        contactAdapter = new ContactAdapter(MainActivity.this, contacts);

        rvContacts.setAdapter(contactAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false));
        rvContacts.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));

        imgadd = findViewById(R.id.imgadd);
        imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("flag", 1);
                mLauncher.launch(intent);
            }
        });
    }
    
    @Override
    public void OnItemListener(int pos, Contact contact){
        postion = pos;
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    @Override
    public void OnEditListener(int pos, Contact contact) {
        postion = pos;
        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
        intent.putExtra("flag", 2);
        intent.putExtra("contact", contact);
        mLauncher.launch(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
         MenuInflater menuInflater =getMenuInflater();
         menuInflater.inflate(R.menu.menu_main,menu);
         return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.menu.menu_search){
            Collections.sort(contacts);
            contactAdapter.notifyDataSetChanged();
        }
        if(item.getItemId() == R.id.mnsearch){
            Intent intent = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void OnDeleteListener(int pos) {
        contactAdapter.delContact(pos);
    }

    @Override
    public void setOnItemClickListener(Contact contact){
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        intent.putExtra("contact",contact);
        startActivity(intent);
    }
    
}