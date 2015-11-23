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

public class NewsSearch extends Controller {
  public Result search() {
    String query = Form.form().bindFromRequest().get("q");
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
            "ニュース検索結果", query, newsList));
    } else {
      return ok(
          NewsSearchResult.render(
            "ニュース検索", query,
            new ArrayList<CompanyNews>()));
      //return notFound(
      //    "<h1>Page not found</h1>").as("text/html");
    }
  }
}
