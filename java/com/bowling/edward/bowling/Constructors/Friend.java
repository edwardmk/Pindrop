package com.bowling.edward.bowling.Constructors;

public class Friend {
        private String email;
        private String uid;
        private String username;

        public Friend(){}

        public Friend(String email, String username, String uid){
            this.email = email;
            this.username = username;
            this.uid = uid;
        }

        public String getUid(){
            return uid;
        }
        public void setUid(String uid){
            this.uid = uid;
        }

        public String getEmail(){
            return email;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
}