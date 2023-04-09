package com.example.tranphuongnam_bt04_65;

import java.io.Serializable;

public class Contact implements Serializable, Comparable<Contact> {
    int id;
    String Firstname;
    String Lastname;
//    int image;
    String image;
    String gmail;
    String phonenumber;
    String birthday;


    public Contact(int id, String firstname, String lastname, String image, String phonenumber, String gmail, String birthday) {
        this.id = id;
        Firstname = firstname;
        Lastname = lastname;
        this.image = image;
        this.gmail = gmail;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }



    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public int compareTo(Contact contact)
    {
        if (Firstname.compareToIgnoreCase(contact.Firstname) ==0){
            return Lastname.compareToIgnoreCase(contact.Lastname);
        }
        return Firstname.compareToIgnoreCase(contact.Firstname);
    }
    @Override
    public String toString(){
        return "Contact{" +"id=" +id+", Firstname='" + Firstname +'\''+",lastName='" +Lastname + '\'' + ", image" +image +",phonenumber='" +phonenumber +'\'' +",gmail='" +gmail +'\'' + ",birthday='" +birthday +'\'' +'}';
    }
}
