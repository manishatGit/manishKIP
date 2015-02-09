package com.knolx.core.impl

import org.scalatest.BeforeAndAfter
import org.slf4j.LoggerFactory
import com.knol.core.Knolder
import org.scalatest.FunSuite
class GetKnolderListTest extends FunSuite with BeforeAndAfter {
  val logger = LoggerFactory.getLogger(this.getClass)
  val KnolRepoImplObj = new KnolRepoDbImpl
  /**
   * Block: before contains the code to create a testing table
   * named knolder in test db
   */
  before {
    try {
      val myConnection = new ConnectionImpl()
      val connection = myConnection.getConnection() match {
        case Some(connection) =>
          val createStmt = connection.createStatement()
          //val createSql = "create table knolder(id INT PRIMARY KEY AUTO_INCREMENT, name varchar(20), email varchar(30), mobile varchar(13));"
          // createStmt.executeUpdate(createSql)
          //val insertSql = "insert into knolder (name,email,mobile) values('Manish','reseamanish@gmail.com','9999999999');"
          //createStmt.executeUpdate(insertSql)
          connection.close()
        case None => None
      }
    } catch {
      case ex: Exception =>
        //  logger.error("Error in before" + ex.printStackTrace())
        None
    }
  }
  /**
   * Block: Deletes the table knolder just after executing the tests
   */
  after {
    try {
      val myConnection = new ConnectionImpl()
      val connection = myConnection.getConnection() match {
        case Some(connection) =>
          //      val deleteStmt = connection.createStatement()
          //    val deleteSql1 = "drop table knolder;"
          //  deleteStmt.executeUpdate(deleteSql1)
          // val deleteSql2 = "drop table knolsession;"
          // deleteStmt.executeUpdate(deleteSql2)
          connection.close()
        case None => None
      }
    } catch {
      case ex: Exception =>
        //logger.error("Error in before" + ex.printStackTrace())
        None
    }

  }
  /* Negatively testing the method createKnolder without creating the table 
 * 
 */
  test("Getting the list of empty records ") {
    KnolRepoImplObj.deleteKnolder(1) //Deletes the record to empty the table
    assert(KnolRepoImplObj.getKnolderList() === None)
  }
}