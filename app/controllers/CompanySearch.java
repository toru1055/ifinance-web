package controllers;

import play.*;
import play.mvc.*;
import play.twirl.api.Html;
import play.data.Form;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

import jp.thotta.ifinance.model.Database;
import jp.thotta.ifinance.model.CompanyProfile;

import views.html.*;

public class CompanySearch extends Controller {
  public Result search() {
    String stockId = Form.form().bindFromRequest().get("id");
    String query = Form.form().bindFromRequest().get("q");
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
      return notFound(
          "<h1>Page not found</h1>").as("text/html");
    }
  }
}
