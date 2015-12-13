package controllers;

import play.*;
import play.mvc.*;
import play.twirl.api.Html;
import play.data.*;
import static play.data.Form.*;

import jp.thotta.ifinance.batch.Initializer;
import jp.thotta.ifinance.model.Database;
import jp.thotta.ifinance.utilizer.JoinedStockInfo;

import java.util.List;

import java.sql.Connection;
import java.sql.SQLException;

import views.html.*;
import models.*;

public class CompanyDetail extends Controller {
  @Security.Authenticated(Secured.class)
  public Result show(int stockId) {
    JoinedStockInfo jsi = null;
    try {
      Connection c = Database.getConnection();
      jsi = JoinedStockInfo.selectByStockId(stockId, c);
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      try {
        Database.closeConnection();
      } catch(SQLException e) {
        e.printStackTrace();
      }   
    }
    if(jsi != null) {
      CompanyInformation ci = new CompanyInformation(jsi);
      User user = User.findByEmail(request().username());
      List<NewsReminder> remindList =
        NewsReminder.findByStockId(stockId);
      List<NewsReminder> remindListNotBindNews =
        NewsReminder.findByStockIdNotBindNews(stockId);
      return ok(
          CompanyDetailShow.render(
            ci.companyName() + "の銘柄詳細",
            ci, remindList, user, remindListNotBindNews));
    } else {
      return notFound(
          "<h1>Page not found</h1>").as("text/html");
    }
  }

  @Security.Authenticated(Secured.class)
    public Result editReminder(Integer stockId, Long remindId) {
      NewsReminder newsReminder = null;
      Form<Reminder> remindForm =
        form(Reminder.class).bindFromRequest();
      User user = User.findByEmail(request().username());
      String message = remindForm.get().message;
      String remindDate = remindForm.get().remindDate;
      if(user != null) {
        if(remindId > 0L) {
          newsReminder = NewsReminder.find.byId(remindId);
          if(newsReminder == null ||
              newsReminder.userId != user.id) {
            return badRequest(
                "<h1>Bad Request</h1>").as("text/html");
          }
          newsReminder.stockId = stockId;
          newsReminder.message = message;
          newsReminder.setRemindDate(remindDate);
          if(newsReminder.getRemindDate() != null) {
            newsReminder.save();
          } else {
            return badRequest(
                "<h1>Bad Request</h1>").as("text/html");
          }
        } else {
          newsReminder =
            NewsReminder.create(0L,
                                user.id,
                                stockId, 
                                remindDate,
                                message);
        }
        return redirect(
            controllers.routes.CompanyDetail.show(stockId));
      }
      return badRequest(
          "<h1>Bad Request</h1>").as("text/html");
    }

  public static class Reminder {
    public String remindDate;
    public String message;
  }
}
