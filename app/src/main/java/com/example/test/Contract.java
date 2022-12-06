package com.example.test;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

public interface Contract {

    public interface Model {
        public void isFound(String username);
        public int getX();

        //reference.addValueEventListener(new ValueEventListener() {
        void onDataChange(@NonNull DataSnapshot dataSnapshot);
    }

    public interface View {
        public void sendUserToNextStudentPage();
        public void sendUsertoNextAdminPage();
        public String getUsername();
        public String getPassword();
        public void createToast(Contract.View context, String message);
        //public void handleClick

    }

    public interface Presenter {
        public void checkUsername();
    }

}
