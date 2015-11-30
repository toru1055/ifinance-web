package controllers;

import play.*;
import play.mvc.*;
import play.twirl.api.Html;
import play.data.Form;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

import jp.thotta.ifinance.model.Database;
import jp.thotta.ifinance.model.CompanyNews;

import views.html.*;
import utils.InputHandler;

public class NewsSearch extends Controller {
  @Security.Authenticated(Secured.class)
  public Result search() {
    String q = Form.form().bindFromRequest().get("q");
    String query = InputHandler.sanitize(q);
    if(query != null) {
      List<CompanyNews> newsList = null;
      try {
        Connection c = Database.getConnection();
        newsList = CompanyNews.selectByQuery(query, 0, 100, c);
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        try {
          Database.closeConnection();
        } catch(SQLException e) {
          e.printStackTrace();
        }   
      }
      return ok(
          NewsSearchResult.render(
            "ニュース検索結果", q, newsList));
    } else {
      return ok(
          NewsSearchResult.render(
            "ニュース検索", q, new ArrayList<CompanyNews>()));
      //return notFound(
      //    "<h1>Page not found</h1>").as("text/html");
    }
  }
}
