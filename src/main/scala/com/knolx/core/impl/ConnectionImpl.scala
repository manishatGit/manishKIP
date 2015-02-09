/*
 * Class: ConnectionImpl Implements trait Connection to create a database
 * connection
 */
package com.knolx.core.impl

import com.knol.db.connection.Connection
import java.sql._
import java.sql.DriverManager
class ConnectionImpl extends Connection {

  /*
   * Method: returns database connection, uses database url,username, password
   * from the trait: Connection
   */
  def getConnection(): Option[java.sql.Connection] = {
    try {
      Class.forName(config.getString("db.driver"));
      val conn = DriverManager.getConnection(url + "?user=" + usr + "&password=" + password)
      Some(conn)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        None
    }
  }
}
