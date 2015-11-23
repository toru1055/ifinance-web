package models;

import java.util.List;
import java.util.ArrayList;
import jp.thotta.ifinance.model.CompanyNews;
import jp.thotta.ifinance.utilizer.JoinedStockInfo;

public class CompanyInformation {
  JoinedStockInfo joinedStockInfo;

  public CompanyInformation(JoinedStockInfo jsi) {
    this.joinedStockInfo = jsi;
  }

  public List<CompanyNews> companyNewsList() {
    if(joinedStockInfo != null) {
      if(joinedStockInfo.companyNewsList != null) {
        return joinedStockInfo.companyNewsList;
      } else {
        return new ArrayList<CompanyNews>();
      }
    } else {
      return null;
    }
  }

  public Integer stockId() {
    if(joinedStockInfo != null &&
        joinedStockInfo.dailyStockPrice != null) {
      return joinedStockInfo.dailyStockPrice.stockId;
    } else {
      return null;
    }
  }

  public String companyName() {
    if(joinedStockInfo != null &&
        joinedStockInfo.companyProfile != null) {
      return joinedStockInfo.companyProfile.companyName;
    } else {
      return null;
    }
  }

  public String businessCategory() {
    if(joinedStockInfo != null &&
        joinedStockInfo.companyProfile != null) {
      return joinedStockInfo.companyProfile.businessCategory;
    } else {
      return null;
    }
  }

  public String smallBusinessCategory() {
    if(joinedStockInfo != null &&
        joinedStockInfo.companyProfile != null &&
        joinedStockInfo.companyProfile.smallBusinessCategory != null) {
      return joinedStockInfo.companyProfile.smallBusinessCategory;
    } else {
      return "---";
    }
  }

  public String companyFeature() {
    if(joinedStockInfo != null &&
        joinedStockInfo.companyProfile != null) {
      return joinedStockInfo.companyProfile.companyFeature;
    } else {
      return null;
    }
  }

  public String announcementDate() {
    if(joinedStockInfo != null &&
        joinedStockInfo.corporatePerformance != null &&
        joinedStockInfo.corporatePerformance.announcementDate != null) {
      return joinedStockInfo.corporatePerformance.announcementDate.toString();
    } else {
      return null;
    }
  }

  public String actualStockPrice() {
    if(joinedStockInfo != null &&
        joinedStockInfo.dailyStockPrice != null) {
      return String.format("%.1f円",
          joinedStockInfo.dailyStockPrice.actualStockPrice());
    } else {
      return null;
    }
  }

  public String marketCap() {
    if(joinedStockInfo != null &&
        joinedStockInfo.dailyStockPrice != null) {
      return String.format("%,3d百万円",
          joinedStockInfo.dailyStockPrice.marketCap);
    } else {
      return null;
    }
  }

  public String stockNumber() {
    if(joinedStockInfo != null &&
        joinedStockInfo.dailyStockPrice != null) {
      return String.format("%,3d万株",
          joinedStockInfo.dailyStockPrice.stockNumber / 10000);
    } else {
      return null;
    }
  }

  public String tradingVolume() {
    if(joinedStockInfo != null &&
        joinedStockInfo.dailyStockPrice != null) {
      return String.format("%,3d株",
          joinedStockInfo.dailyStockPrice.tradingVolume);
    } else {
      return null;
    }
  }

  public String tradingVolumeGrowthRatio() {
    if(joinedStockInfo != null &&
        joinedStockInfo.dailyStockPrice != null) {
      return String.format("%.2f倍",
          joinedStockInfo.dailyStockPrice.tradingVolumeGrowthRatio());
    } else {
      return null;
    }
  }

  public String pbr() {
    if(joinedStockInfo != null &&
        joinedStockInfo.pbrInverse > 0) {
      return String.format("%.2f倍",
          1.0 / joinedStockInfo.pbrInverse);
      } else {
        return null;
      }
  }

  public String netPer() {
    if(joinedStockInfo != null) {
      return String.format("%.2f倍",
          joinedStockInfo.per());
    } else {
      return null;
    }
  }

  public String totalAssets() {
    if(joinedStockInfo != null &&
        joinedStockInfo.corporatePerformance != null) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance.totalAssets);
    } else {
      return null;
    }
  }

  public String ownedCapitalRatio() {
    if(joinedStockInfo != null &&
        joinedStockInfo.corporatePerformance != null) {
      return String.format("%.2f％",
          joinedStockInfo.corporatePerformance.ownedCapitalRatio() * 100);
    } else {
      return null;
    }
  }

  public String dividend() {
    if(joinedStockInfo != null &&
        joinedStockInfo.corporatePerformance != null) {
      return String.format("%.2f円",
          joinedStockInfo.corporatePerformance.dividend);
    } else {
      return null;
    }
  }

  public String dividendYield() {
    if(joinedStockInfo != null) {
      return String.format("%.2f％",
          joinedStockInfo.dividendYieldPercent());
    } else {
      return null;
    }
  }

  boolean isCorporatePerformance() {
    return joinedStockInfo != null &&
      joinedStockInfo.corporatePerformance != null;
  }

  boolean isCorporatePerformance1() {
    return joinedStockInfo != null &&
      joinedStockInfo.corporatePerformance1 != null;
  }

  boolean isCorporatePerformance2() {
    return joinedStockInfo != null &&
      joinedStockInfo.corporatePerformance2 != null;
  }

  public String salesAmount() {
    if(isCorporatePerformance()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance.salesAmount);
    } else {
      return null;
    }
  }

  public String salesAmount1() {
    if(isCorporatePerformance1()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance1.salesAmount);
    } else {
      return null;
    }
  }

  public String salesAmount2() {
    if(isCorporatePerformance2()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance2.salesAmount);
    } else {
      return null;
    }
  }

  public String operatingProfit() {
    if(isCorporatePerformance()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance.operatingProfit);
    } else {
      return null;
    }
  }

  public String operatingProfit1() {
    if(isCorporatePerformance1()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance1.operatingProfit);
    } else {
      return null;
    }
  }

  public String operatingProfit2() {
    if(isCorporatePerformance2()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance2.operatingProfit);
    } else {
      return null;
    }
  }

  public String operatingProfitRate() {
    if(isCorporatePerformance()) {
      return String.format("%.2f％",
          joinedStockInfo.corporatePerformance.operatingProfitRate() * 100);
    } else {
      return null;
    }
  }

  public String operatingProfitRate1() {
    if(isCorporatePerformance1()) {
      return String.format("%.2f％",
          joinedStockInfo.corporatePerformance1.operatingProfitRate() * 100);
    } else {
      return null;
    }
  }

  public String operatingProfitRate2() {
    if(isCorporatePerformance2()) {
      return String.format("%.2f％",
          joinedStockInfo.corporatePerformance2.operatingProfitRate() * 100);
    } else {
      return null;
    }
  }

  public String ordinaryProfit() {
    if(isCorporatePerformance()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance.ordinaryProfit);
    } else {
      return null;
    }
  }

  public String ordinaryProfit1() {
    if(isCorporatePerformance1()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance1.ordinaryProfit);
    } else {
      return null;
    }
  }

  public String ordinaryProfit2() {
    if(isCorporatePerformance2()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance2.ordinaryProfit);
    } else {
      return null;
    }
  }

  public String netProfit() {
    if(isCorporatePerformance()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance.netProfit);
    } else {
      return null;
    }
  }

  public String netProfit1() {
    if(isCorporatePerformance1()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance1.netProfit);
    } else {
      return null;
    }
  }

  public String netProfit2() {
    if(isCorporatePerformance2()) {
      return String.format("%,3d百万円",
          joinedStockInfo.corporatePerformance2.netProfit);
    } else {
      return null;
    }
  }

  public String estimateNetProfit() {
    if(joinedStockInfo != null) {
      return String.format("%,3d百万円",
          joinedStockInfo.estimateNetProfit());
    } else {
      return "---";
    }
  }

  public String estimateNetPer() {
    if(joinedStockInfo != null &&
        joinedStockInfo.estimateNetProfit() != null &&
        joinedStockInfo.estimateNetProfit() > 0.0 &&
        joinedStockInfo.dailyStockPrice != null) {
      return String.format("%.2f倍",
          (double)joinedStockInfo.dailyStockPrice.marketCap /
          joinedStockInfo.estimateNetProfit());
    } else {
      return "---";
    }
  }

  public String predictedStockPrice() {
    if(joinedStockInfo != null &&
        joinedStockInfo.predictedStockPrice() != null) {
      return String.format("%.1f円",
          joinedStockInfo.predictedStockPrice());
    } else {
      return "---";
    }
  }

  public String undervaluedRate() {
    if(joinedStockInfo != null &&
        joinedStockInfo.undervaluedRate() != null) {
      return String.format("%.2f％",
          joinedStockInfo.undervaluedRate() * 100);
    } else {
      return "---";
    }
  }

}
