package models;

import java.util.Date;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
public class User extends Model {
  @Id
    public Long id;
  @Constraints.Required
    public String email;
  @Constraints.Required
    public String password;

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public static Finder<String,User> find =
    new Finder<String,User>(User.class);

  public static User authenticate(String email, String password) {
    return find.where().eq("email", email)
      .findUnique();
      //.eq("password", password).findUnique();
  }

  public static User findByEmail(String email) {
    return find.where().eq("email", email).findUnique();
  }
}
