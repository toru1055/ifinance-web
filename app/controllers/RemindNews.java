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
      CompanyNews news = null;
      CompanyProfile profile = null;
      try {
        Connection c = Database.getConnection();
        news = CompanyNews.findById(c, newsId);
        profile = CompanyProfile.selectByStockId(news.stockId, c);
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        try {
          Database.closeConnection();
        } catch(SQLException e) {
          e.printStackTrace();
        }   
      }
      if(news != null && profile != null) {
        User user = User.findByEmail(request().username());
        List<NewsReminder> remindList =
          NewsReminder.findListByNewsId(news.id);
        return ok(
            RemindShow.render(
              "ニュースリマインド", news, profile,
              form(Reminder.class), remindList, user));
      } else {
        return notFound("<h1>Page not found</h1>")
          .as("text/html");
      }
    }

  @Security.Authenticated(Secured.class)
    public Result edit(Long remindId) {
      Form<Reminder> remindForm =
        form(Reminder.class).bindFromRequest();
      Long newsId = remindForm.get().newsId;
      CompanyNews news = null;
      NewsReminder newsReminder = null;
      try {
        Connection c = Database.getConnection();
        news = CompanyNews.findById(c, newsId);
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        try {
          Database.closeConnection();
        } catch(SQLException e) {
          e.printStackTrace();
        }   
      }
      User user = User.findByEmail(request().username());
      if(news != null && user != null) {
        if(remindId > 0L) {
          newsReminder = NewsReminder.find.byId(remindId);
          if(newsReminder == null ||
              newsReminder.userId != user.id) {
            return badRequest(
                "<h1>Bad Request</h1>").as("text/html");
          }
          newsReminder.stockId = news.stockId;
          newsReminder.message = remindForm.get().message;
          newsReminder.setRemindDate(remindForm.get().remindDate);
          if(newsReminder.getRemindDate() != null) {
            newsReminder.save();
          } else {
            return badRequest(
                "<h1>Bad Request</h1>").as("text/html");
          }
        } else {
          newsReminder =
            NewsReminder.create(news.id,
                                user.id,
                                news.stockId, 
                                remindForm.get().remindDate,
                                remindForm.get().message);
        }
        return redirect(
            controllers.routes.RemindNews.show(news.id));
      }
      return badRequest(
          "<h1>Bad Request</h1>").as("text/html");
    }

  public static class Reminder {
    public Long newsId;
    public String remindDate;
    public String message;
  }
}
