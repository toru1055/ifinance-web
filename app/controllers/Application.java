package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;
import models.*;

public class Application extends Controller {
  public Result login() {
    return ok(login.render("login", form(Login.class)));
  }

  public Result logout() {
    session().clear();
    flash("success", "You've been logged out");
    return redirect(
        controllers.routes.Application.login()
        );
  }

  public Result authenticate() {
    Form<Login> loginForm = form(Login.class).bindFromRequest();
    if(loginForm.hasErrors()) {
      return badRequest(
          login.render("login(error)", loginForm));
    } else {
      session().clear();
      session("email", loginForm.get().email);
      return redirect(controllers.routes.CompanySearch.search());
    }
  }

  public static class Login {
    public String email;
    public String password;
    public String validate() {
      if(User.authenticate(email, password) == null) {
        return "Invalid user or password";
      }
      return null;
    }
  }
}
