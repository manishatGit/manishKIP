/**
 * class:  KnolRepoDbImpl extends class ConnetionImpl to get Connection from
 * database and mix KnolderRepository trait and Implements all the CRUD
 * operations declared in the trait KnolderRepo
 */
package com.knolx.core.impl
import com.knol.db.connection.Connection
import com.knol.core.Knolder
import com.knol.core.KnolderRepo
import java.sql._

class KnolRepoDbImpl extends ConnectionImpl with KnolderRepo {
  val resultSetHolder: scala.collection.mutable.MutableList[Knolder] = scala.collection.mutable.MutableList()
  /*
   * Method: createKnolder Inserts a Knolder Object as a record in database table
   * Knolder. It returns the Id of newly created Knolder record.
   */
  def createKnolder(knolder: Knolder): Option[Int] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement();
          val sql = "insert into knolder (name,email,mobile) values(?,?,?);";
          val prepStmt = conn.prepareStatement(sql);
          prepStmt.setString(1, knolder.name);
          prepStmt.setString(2, knolder.email);
          prepStmt.setString(3, knolder.mobile);
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
        logger.error("Error while createing record")
        None
    }
  }
  /*
   * Method: updateKnolder accepts a Knolder object as parameter and
   * updates that Knolder record in the table by its Id as comparison key.
   * Returns number of rows affected.
   */
  def updateKnolder(knolder: Knolder): Option[Int] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement()
          val sql = "update knolder set name=?,email=?,mobile=? where id=?;"
          val prepStmt = conn.prepareStatement(sql)
          val COLUMN_INDEX_ID = 4
          prepStmt.setString(1, knolder.name)
          prepStmt.setString(2, knolder.email)
          prepStmt.setString(3, knolder.mobile)
          prepStmt.setInt(COLUMN_INDEX_ID, knolder.id.get)
          val rows = prepStmt.executeUpdate();
          stmt.close();
          conn.close();
          logger.debug("Record Updated: " + rows)
          if (!(rows > 0)) {
            throw new Exception()
          }
          Some(rows)
        }
        case None => throw new Exception()
      }
    } catch {
      case ex: Exception =>
        logger.error("Error while updating record")
        None
    }
  }
  /*
   * Method: deleteKnolder accept Id of the Knolder record as parameters
   * and deletes that record from the database table Knolder. It returns
   * number of records deleted.
   * */
  def deleteKnolder(knolderId: Int): Option[Int] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement()
          val sql = "delete from knolder where id=" + knolderId + ";"
          val rows = stmt.executeUpdate(sql);
          stmt.close();
          conn.close();
          logger.debug("Record Deleted: " + rows)
          if (!(rows > 0)) {
            throw new Exception()
          }
          Some(rows)
        }
        case None => throw new Exception()
      }
    } catch {
      case ex: Exception =>
        logger.error("Error while deleting record" + ex.printStackTrace())
        None
    }
  }
  /*
   * Method getKnolderList() fetches the list of Knolders from database table Knolder
   * It returns a Mutable list of Knolder objects.
   */
  def getKnolderList(): Option[scala.collection.mutable.MutableList[Knolder]] = {
    try {
      val Connector = new ConnectionImpl()
      Connector.getConnection() match {
        case Some(conn) => {
          val stmt = conn.createStatement()
          val sql = "select * from knolder;"
          val knolResultSet = stmt.executeQuery(sql)
          val COL_INDEX_ID = 1
          val COL_INDEX_NAME = 2
          val COL_INDEX_EMAIL = 3
          val COL_INDEX_MOBILE = 4
          while (knolResultSet.next()) {
            val knolID = knolResultSet.getInt(COL_INDEX_ID)
            val knolName = knolResultSet.getString(COL_INDEX_NAME)
            val knolEmail = knolResultSet.getString(COL_INDEX_EMAIL)
            val knolMobile = knolResultSet.getString(COL_INDEX_MOBILE)
            resultSetHolder += (Knolder(Some(knolID), knolName, knolEmail, knolMobile))
          }
          if (resultSetHolder.size < 1) throw new Exception()
          Some(resultSetHolder)
        }
        case None => None
      }
    } catch {
      case ex: Exception =>
        logger.error("Error while fetching record" + ex.printStackTrace())
        None
    }
  }
}
