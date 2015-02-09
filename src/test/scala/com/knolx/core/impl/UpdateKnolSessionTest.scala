package com.knolx.core.impl

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import com.knol.core.Knolder
import com.knol.core.KnolSession

class UpdateKnolSessionTest extends FunSuite with BeforeAndAfter {
  val logger = LoggerFactory.getLogger(this.getClass)
  val knolSessionImplObj = new KnolSessionRepoImpl()    /**
   * Block: before contains the code to create a testing table
   * named knolder and a table knolsession in test db
   */
  before {
    try {
      val myConnection = new ConnectionImpl()
      val connection = myConnection.getConnection() match {
        case Some(connection) => {
          logger.debug("Got the connection")
          val createStmt = connection.createStatement()
          val createSql1 = "create table knolder(id INT PRIMARY KEY AUTO_INCREMENT, name varchar(20), email varchar(30), mobile varchar(13));"
          createStmt.executeUpdate(createSql1)
          val insertSql1 = "insert into knolder (name,email,mobile) values('Manish','reseamanish@gmail.com','9999999999');"
          createStmt.executeUpdate(insertSql1)
          val createSql2 = "create table knolsession(id INT AUTO_INCREMENT, topic varchar(30), session_date date, knolid INT, PRIMARY KEY(id), FOREIGN KEY (knolid) REFERENCES knolder (id) ON DELETE CASCADE)"
          createStmt.executeUpdate(createSql2)
          logger.debug("Statement created.. ")
          val insertSql2 = "insert into knolsession (topic,session_date,knolid) values ('Scala Collections','2015-2-15',1);"
          createStmt.executeUpdate(insertSql2)
          connection.close()
        }
        case None => None
      }
    } catch {
      case ex: Exception =>
        logger.error("Error in before" + ex.printStackTrace())
        None
    }
  }
  /**
   * Block: Deletes the table knolder and knolSession just after executing the tests
   */
  after {
    try {
      val myConnection = new ConnectionImpl()
      val connection = myConnection.getConnection() match {
        case Some(connection) => {
          logger.debug("Got the connection")
          val deleteStmt = connection.createStatement()
          val deleteSql1 = "drop table knolsession;"
          deleteStmt.executeUpdate(deleteSql1)
          val deleteSql2 = "drop table knolder;"
          deleteStmt.executeUpdate(deleteSql2)
        }
        case None => None
      }
    } catch {
      case ex: Exception =>
        logger.error("Error in before" + ex.printStackTrace())
        None
    }
  }
  /* Negatively testing the method updateKnolder without creating the table 
 * 
 */
  test("Updating a KnolSession by with wrong Id") {
    val knolSessionObject =  KnolSession(Some(5), "Scala in Business", "2012-02-02", 6)
    assert(knolSessionImplObj.updateKnolSession(knolSessionObject) === None)
  }

}