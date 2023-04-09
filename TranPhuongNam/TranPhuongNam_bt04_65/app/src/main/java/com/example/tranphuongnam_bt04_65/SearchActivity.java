package com.example.tranphuongnam_bt04_65;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity implements ContactAdapter.Listener {
    ArrayList<Contact> contacts;
    ContactAdapter contactAdapter;
    RecyclerView rvContact;
    SearchView searchView;
    int postion;
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
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("");
        App app = new App();

        rvContact = findViewById(R.id.ryContact);
        contacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(SearchActivity.this,contacts);
        rvContact.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
        rvContact.setAdapter(contactAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.mnsearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void OnItemListener(int pos, Contact contact){
        postion = pos;
        Intent intent = new Intent(SearchActivity.this, InfoActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    @Override
    public void OnEditListener(int pos, Contact contact) {
        postion = pos;
        Intent intent = new Intent(SearchActivity.this, AddEditActivity.class);
        intent.putExtra("flag", 2);
        intent.putExtra("contact", contact);
        mLauncher.launch(intent);
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.menu.menu_search){
            Collections.sort(contacts);
            contactAdapter.notifyDataSetChanged();
        }
        if(item.getItemId() == R.id.mnsearch){
            Intent intent = new Intent(SearchActivity.this,SearchActivity.class);
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
        Intent intent = new Intent(SearchActivity.this, InfoActivity.class);
        intent.putExtra("contact",contact);
        startActivity(intent);
    }
}
