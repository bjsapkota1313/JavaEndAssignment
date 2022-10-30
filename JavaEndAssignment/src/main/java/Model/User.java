package Model;

public class User extends Person{ // users are employees working library
   private final String userName;
   private final  String password;

   public String getUserName() {

      return userName;
   }
   public String getPassword() {

      return password;
   }
   public User( String firstName, String lastName, String password) {
      super( firstName, lastName);
      this.userName = firstName;// making username as firstName
      this.password = password;
   }
}