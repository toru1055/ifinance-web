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
import jp.thotta.ifinance.model.CompanyProfile;

import views.html.*;
import utils.InputHandler;

public class CompanySearch extends Controller {
  public Result search() {
    String stockId = InputHandler.sanitize(
        Form.form().bindFromRequest().get("id"));
    String query = InputHandler.sanitize(
        Form.form().bindFromRequest().get("q"));
    if(stockId != null) {
      return redirect("/stock/" + stockId);
    } else if(query != null) {
      List<CompanyProfile> profileList = null;
      try {
        Connection c = Database.getConnection();
        profileList = CompanyProfile.selectByQuery(query, c);
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
          CompanySearchResult.render(
            "検索結果", query, profileList));
    } else {
      return ok(
          CompanySearchResult.render(
            "検索結果", query, new ArrayList<CompanyProfile>()));
      //return notFound(
      //    "<h1>Page not found</h1>").as("text/html");
    }
  }
}
