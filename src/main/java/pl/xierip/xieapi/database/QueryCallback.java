package pl.xierip.xieapi.database;

import java.sql.ResultSet;

/**
 * Created by xierip on 16.08.17. Web: http://xierip.pl
 */
public interface QueryCallback {

  public void onRecive(ResultSet resultSet);
}
