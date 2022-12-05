package com.example.test;

public interface Contract {

    public interface Model {
        public boolean isFound(String username);

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
