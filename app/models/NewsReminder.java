package models;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import jp.thotta.ifinance.common.MyDate;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
public class NewsReminder extends Model {
  @Id
    public Long id;
  @Constraints.Required
    public Long newsId;
  @Constraints.Required
    public Long userId;
  @Constraints.Required
    public Integer stockId;
  @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date createDate;
  @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date remindDate;
  @Column(columnDefinition = "TEXT")
    public String message;

  public static Finder<Long, NewsReminder> find =
    new Finder<Long, NewsReminder>(NewsReminder.class);
  public static SimpleDateFormat dateFormat =
    new SimpleDateFormat("yyyy-MM-dd");

  public NewsReminder(Long newsId, Long userId, Integer stockId) {
    this.newsId = newsId;
    this.userId = userId;
    this.stockId = stockId;
  }

  public void setRemindDate(String dateString) {
    try {
      this.remindDate = dateFormat.parse(dateString);
    } catch(ParseException e) {
      this.remindDate = null;
    }
  }

  public String getRemindDate() {
    if(this.remindDate == null) {
      return null;
    } else {
      return dateFormat.format(this.remindDate);
    }
  }

  public static NewsReminder create(Long newsId,
                                    Long userId,
                                    Integer stockId,
                                    String remindDate,
                                    String message) {
    NewsReminder reminder = new NewsReminder(newsId, userId, stockId);
    reminder.createDate = new Date();
    reminder.setRemindDate(remindDate);
    reminder.message = message;
    reminder.save();
    return reminder;
  }

  public static NewsReminder findByUserNews(Long userId,
                                            Long newsId)
  {
    return find.where().eq("news_id", newsId)
      .eq("user_id", userId).findUnique();
  }

  public static List<NewsReminder> findListByNewsId(Long newsId)
  {
    return find.where().eq("news_id", newsId)
      .ge("remind_date", MyDate.getToday().getTimeInMillis())
      .orderBy("remind_date asc")
      .findList();
  }

  public static List<NewsReminder> findByStockId(Integer stockId)
  {
    return find.where().eq("stock_id", stockId)
      .ge("remind_date", MyDate.getToday().getTimeInMillis())
      .orderBy("remind_date asc")
      .findList();
  }

  public static List<NewsReminder> findEffective()
  {
    return find.where()
      .ge("remind_date", MyDate.getToday().getTimeInMillis())
      .orderBy("remind_date asc")
      .findList();
  }
}
