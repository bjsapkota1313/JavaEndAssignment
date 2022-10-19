package Database;

import Model.User;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    private final List<User> users;

    public UserData() {
        users = new ArrayList<User>();
        users.add(new User("John","Sapkota","Admin@123"));
        users.add(new User("Amy","Jackson","Admin@1234"));
     }
     public User loginWithCredentials(String username, String password){
        User loggingUser=null;
        for (User user : users
              ) {
             if (user.getUserName().equals(username) && user.getPassword().equals(password))
             {
                loggingUser = user;
             }

         }
        return loggingUser;
     }
}
