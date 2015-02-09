package com.knolx.core.impl
/*
 * Class KnolSessionRepoImpl an Implementation of class KnolSession
 * to provide all CURD operations declared in the trait KnolSessionRepo
 */
import com.knol.core.KnolSesssionRepo
import com.knol.core.KnolSession

class KnolSessionRepoImpl extends ConnectionImpl with KnolSesssionRepo {
  val resultSetHolder: scala.collection.mutable.MutableList[KnolSession] = scala.collection.mutable.MutableList();

  /*
   *  Inserts a knolSession in the database table KnolSession
   *  and returns the Id of newly created record.
   */
  def createKnolSession(knolSession: KnolSession): Option[Int] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement();
          val sql = "insert into knolsession (topic,session_date,knolid) values(?,?,?);";
          val prepStmt = conn.prepareStatement(sql)
          prepStmt.setString(1, knolSession.topic)
          prepStmt.setString(2, knolSession.date)
          prepStmt.setInt(3, knolSession.KnolId)
          prepStmt.executeUpdate();
          val NewSql = "select LAST_INSERT_ID();"
          val lastInsertIdSet = prepStmt.executeQuery(NewSql);
          lastInsertIdSet.next()
          val rows = lastInsertIdSet.getInt(1);
          stmt.close();
          conn.close();
          logger.debug("Record created, Id Returned: " + rows)
          Some(rows);
        }
        case None =>
          throw new Exception()
      }
    } catch {
      case ex: Exception =>
        logger.error("Error while createing record" + ex.printStackTrace())
        None
    }
  }
  /*Method: updateKnolSession updates a KnolSession record in the table.
   * Accepts KnolSession object and update all the contents by its knolId.
   */
  def updateKnolSession(knolSession: KnolSession): Option[Int] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement()
          val sql = "update knolsession set topic=?,session_date=? where knolid=?;"
          val prepStmt = conn.prepareStatement(sql)
          val KNOL_ID = 3
          prepStmt.setInt(KNOL_ID, knolSession.KnolId)
          prepStmt.setString(1, knolSession.topic)
          prepStmt.setString(2, knolSession.date)
          val rows = prepStmt.executeUpdate();
          stmt.close();
          conn.close();
          logger.debug("Record Updated: " + rows)
          if (!(rows < 1)) {
            Some(rows)
          } else {
            throw new Exception()
          }
        }
        case None => throw new Exception
      }
    } catch {
      case ex: Exception =>
        logger.error("Error while updating record" + ex.printStackTrace())
        None
    }
  }
  /*Method: getKnolSessionList fetches the records of KnolSession
   * from the table KnolSession. Returns None if no data is available
   *
   */
  def getKnolSessionList(): Option[scala.collection.mutable.MutableList[KnolSession]] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement()
          val sql = "select * from knolsession;"
          val knolResultSet = stmt.executeQuery(sql)
          val COL_INDEX_KNOLX_ID = 1
          val COL_INDEX_TOPIC = 2
          val COL_INDEX_SESSION_DATE = 3
          val COL_INDEX_KNOLDER_ID = 4
          while (knolResultSet.next()) {
            val knolxID = knolResultSet.getInt(COL_INDEX_KNOLX_ID)
            val knolxTopic = knolResultSet.getString(COL_INDEX_TOPIC)
            val knolxSessionDate = knolResultSet.getString(COL_INDEX_SESSION_DATE)
            val knolderID = knolResultSet.getInt(COL_INDEX_KNOLDER_ID)
            resultSetHolder += (KnolSession(Some(knolxID), knolxTopic, knolxSessionDate, knolderID))
          }
          if (resultSetHolder.size < 1) { throw new Exception() }
          Some(resultSetHolder)
        }
        case None => None
      }
    } catch {
      case ex: Exception =>
        logger.error("Error while deletingng record" + ex.printStackTrace())
        None
    }
  }
  /*Method: deletteKnolSession deletes the record Knolsession from the database
   * table knolsession and returns the number of records deleted
   */
  def deleteKnolSession(knolSessionId: Int): Option[Int] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement()
          val sql = "delete from knolsession where knolid=" + knolSessionId
          val rows = stmt.executeUpdate(sql);
          stmt.close();
          conn.close();
          logger.debug("Record Deleted: " + rows)
          Some(rows);
        }
        case None => None
      }
    } catch {
      case ex: Exception =>
        logger.error("Error while deleting record" + ex.printStackTrace())
        None
    }
  }
}
