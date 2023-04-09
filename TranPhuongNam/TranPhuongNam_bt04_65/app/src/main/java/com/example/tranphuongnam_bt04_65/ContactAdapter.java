package com.example.tranphuongnam_bt04_65;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> implements Filterable {
    ArrayList<Contact> contact_sold;
    ArrayList<Contact> contactsFilter;
    Listener listener;

    public ContactAdapter(Listener listener, ArrayList<Contact> contacts) {
        this.listener= listener;
        this.contactsFilter = contacts;
        this.contact_sold = contacts;
    }
    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent,false);
        return new ContactVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = contactsFilter.get(position);
        if(contact == null)
        {
            return;
        }
        holder.txName.setText(contact.getFirstname().concat(" ").concat(contact.getLastname()));
        holder.txMail.setText("".concat(contact.getGmail()));
        holder.txPhoneNumber.setText(contact.getPhonenumber().concat(""));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnItemClickListener(contact);
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnEditListener(position, contact);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnDeleteListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactsFilter.size();
    }
    public  Filter getFilter()
    {
      return new Filter() {
          @Override
          protected FilterResults performFiltering(CharSequence charSequence) {
              String search = charSequence.toString();
              if(search.isEmpty())
              {
                  contactsFilter=  contact_sold;
              }
              else
              {
                 ArrayList<Contact> contactsSearch = new ArrayList<>();
                  for (Contact cont : contact_sold)
                  {
                      if(cont.getFirstname().toLowerCase().contains(search.toLowerCase()) || cont.getPhonenumber().contains(search)||cont.getLastname().toLowerCase().contains(search.toLowerCase())|| cont.getFirstname().toLowerCase().contains(search.toLowerCase()))
                      {
                          contactsSearch.add(cont);
                      }
                  }
                  contactsFilter = contactsSearch;
              }
              FilterResults filterResults = new FilterResults();
              filterResults.values = contactsFilter;
              return filterResults;
          }

          @Override
          protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactsFilter = (ArrayList<Contact>) filterResults.values;
                notifyDataSetChanged();
          }
      };
    }


    static class ContactVH extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView txName, txPhoneNumber, txMail;
        ImageView imgUnknow, ivEdit, ivDelete;


        public ContactVH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgunknow);
            txName = itemView.findViewById(R.id.txName);
            txPhoneNumber = itemView.findViewById(R.id.txPhoneNumber);
            txMail = itemView.findViewById(R.id.txMail);
            ivDelete = itemView.findViewById(R.id.imgDel);
            ivEdit = itemView.findViewById(R.id.imgEdit);
            imgUnknow = itemView.findViewById(R.id.imgunknow);
        }
    }
    public void addContact(Contact contact){
        contactsFilter.add(contact);
        notifyDataSetChanged();
    }
    public void editContact(Contact contact,int pos){
        contactsFilter.set(pos,contact);
        notifyDataSetChanged();
    }
    public void delContact(int pos){
        contactsFilter.remove(pos);
        notifyDataSetChanged();
    }
    public void delConcact(Contact contact){
        contactsFilter.remove(contact);
        notifyDataSetChanged();
    }


    interface  Listener{
        void OnItemListener(int pos, Contact contact);
        void OnEditListener(int pos, Contact contact);

        void OnDeleteListener(int pos);
        void setOnItemClickListener(Contact contact);
    }
}
