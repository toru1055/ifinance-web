package controllers;

import play.*;
import play.mvc.*;
import play.twirl.api.Html;
import play.data.*;
import static play.data.Form.*;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

import jp.thotta.ifinance.model.Database;
import jp.thotta.ifinance.model.CompanyNews;
import jp.thotta.ifinance.model.CompanyProfile;

import models.*;
import views.html.*;
import utils.InputHandler;

public class RemindNews extends Controller {

  @Security.Authenticated(Secured.class)
    public Result show(Long newsId) {
      CompanyNews cn = null;
      CompanyProfile cp = null;
      try {
        Connection c = Database.getConnection();
        cn = CompanyNews.findById(c, newsId);
        cp = CompanyProfile.selectByStockId(cn.stockId, c);
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        try {
          Database.closeConnection();
        } catch(SQLException e) {
          e.printStackTrace();
        }   
      }
      if(cn != null && cp != null) {
        User user = User.findByEmail(request().username());
        NewsReminder newsReminder =
          NewsReminder.findByUserNews(user.id, cn.id);
        return ok(
            RemindShow.render(
              "ニュースリマインド", cn, cp,
              form(Reminder.class), newsReminder));
      } else {
        return notFound("<h1>Page not found</h1>")
          .as("text/html");
      }
    }

  @Security.Authenticated(Secured.class)
    public Result edit(Long newsId) {
      Form<Reminder> remindForm =
        form(Reminder.class).bindFromRequest();
      CompanyNews cn = null;
      try {
        Connection c = Database.getConnection();
        cn = CompanyNews.findById(c, newsId);
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        try {
          Database.closeConnection();
        } catch(SQLException e) {
          e.printStackTrace();
        }   
      }
      if(cn != null) {
        User user = User.findByEmail(request().username());
        NewsReminder newsReminder =
          NewsReminder.findByUserNews(user.id, cn.id);
        if(newsReminder != null) {
          newsReminder.stockId = cn.stockId;
          newsReminder.message = remindForm.get().message;
          newsReminder.setRemindDate(remindForm.get().remindDate);
          newsReminder.save();
        } else {
          newsReminder =
            NewsReminder.create(cn.id,
                                user.id,
                                cn.stockId, 
                                remindForm.get().remindDate,
                                remindForm.get().message);
        }
        return redirect(
            controllers.routes.RemindNews.show(cn.id));
      }
      return badRequest(
          "<h1>Bad Request</h1>").as("text/html");
    }

  public static class Reminder {
    public String remindDate;
    public String message;
  }
}
