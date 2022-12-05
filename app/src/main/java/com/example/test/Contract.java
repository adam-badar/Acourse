package com.example.test;

public interface Contract {

    public interface Model {
        public boolean isFound(String username);

    }

    public interface View {
        public void sendUserToNextStudentPage();
        public void sendUsertoNextAdminPage();
    }

    public interface Presenter {
        public void checkUsername();
    }

}
