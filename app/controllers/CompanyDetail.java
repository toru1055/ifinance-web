package controllers;

import play.*;
import play.mvc.*;
import play.twirl.api.Html;

import jp.thotta.ifinance.batch.Initializer;
import jp.thotta.ifinance.model.Database;
import jp.thotta.ifinance.model.CompanyNews;
import jp.thotta.ifinance.utilizer.JoinedStockInfo;

import java.util.List;

import java.sql.Connection;
import java.sql.SQLException;

import views.html.*;
import models.CompanyInformation;

public class CompanyDetail extends Controller {
  public Result show(int stockId) {
    JoinedStockInfo jsi = null;
    List<CompanyNews> newsList = null;
    try {
      Connection c = Database.getConnection();
      jsi = JoinedStockInfo.selectByStockId(stockId, c);
      newsList = CompanyNews.selectRecentsByStockId(stockId, 10, c);
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      try {
        Database.closeConnection();
      } catch(SQLException e) {
        e.printStackTrace();
      }   
    }
    CompanyInformation ci = new CompanyInformation(jsi);
    return ok(
        CompanyDetailShow.render(
          ci.companyName() + "の銘柄詳細",
          ci));
  }
}
