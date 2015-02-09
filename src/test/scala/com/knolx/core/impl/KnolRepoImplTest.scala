/*
 * class: KnolRepoImplTest extends Funsuite to create Unit test on 
 * all CURD operations defined in the trait packaged in  com.knol.core and mixes the train BeforeAnd After. 
 */

package com.knolx.core.impl

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import com.knol.core.Knolder
import com.mysql.jdbc.Connection
import org.slf4j.LoggerFactory
import com.knol.core.Knolder

class KnolRepoImplTest extends FunSuite with BeforeAndAfter {
  val logger = LoggerFactory.getLogger(this.getClass)
  val KnolRepoImplObj = new KnolRepoDbImpl
  /**
   * Block: before contains the code to create a testing table
   * named knolder in test db
   */
  before{
    try {
      val myConnection = new ConnectionImpl()
      val connection = myConnection.getConnection() match {
        case Some(connection) =>
          val createStmt = connection.createStatement()
          val createSql = "create table knolder(id INT PRIMARY KEY AUTO_INCREMENT, name varchar(20), email varchar(30), mobile varchar(13));"
          createStmt.executeUpdate(createSql)
          val insertSql = "insert into knolder (name,email,mobile) values('Manish','reseamanish@gmail.com','9999999999');"
          createStmt.executeUpdate(insertSql)
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
          val deleteStmt = connection.createStatement()
          val deleteSql1 = "drop table knolder;"
          deleteStmt.executeUpdate(deleteSql1)
          val deleteSql2 = "drop table knolsession;"
          deleteStmt.executeUpdate(deleteSql2)
          connection.close()
        case None => None
      }
    } catch {
      case ex: Exception =>
        //logger.error("Error in before" + ex.printStackTrace())
        None
    }

  }
  
/* Negatively testing the method createKnolder 
 * 
 */
  test("Create a Knol Negative") {
    val KnolderObject = Knolder(Some(1), "Manish", "manish@knoldus.com", "9958317862")
    assert(KnolRepoImplObj.createKnolder(KnolderObject) === Some(2))

  }
  /*
   *Tests the method createKnolder() by passing on Knolder object and comparing it with expected 
   * resulting object values of record Knolder
   */
  test("Create a Knol") {
    val KnolderObject = Knolder(Some(1), "Manish", "manish@knoldus.com", "9958317862")
    assert(KnolRepoImplObj.createKnolder(KnolderObject) === Some(2))

  }

  /*
   *Tests the method updateKnolder() by passing on Knolder object and comparing 
   * the value row affected as 1  
   */
  test("Updating a knol") {
    val KnolderObject = Knolder(Some(1), "Girish", "manish@knoldus.com", "9958317862")
    assert(KnolRepoImplObj.updateKnolder(KnolderObject) === Some(1))
  }
  /*
   *Tests the method updateKnolder() Negatively by passing non-existing Id
   */
  test("Updating a non existing knol") {
    val KnolderObject = Knolder(Some(17), "Girish", "manish@knoldus.com", "9958317862")
    assert(KnolRepoImplObj.updateKnolder(KnolderObject) === None)
  }

  /*
   *Tests the method deleteKnolder() by passing on Id of the knolder Object and comparing it with 
   * expected resulting rows affected as 1
   */

  test("Deleting a knol") {
    assert(KnolRepoImplObj.deleteKnolder(1) === Some(1))
  }
  /*
   *Tests the method deleteKnolder() Negatively by passing non-existing Id
   */
  test("deleting a non existing knol") {
    assert(KnolRepoImplObj.deleteKnolder(167) === None)
  }

  /* 
   * Tests the method getKnolderList() by Fetching List of Knolder records.
   * It must return the value in MutableList as result
   */

  test("Fetching the list of Records") {
    val knolObject1 = Knolder(Some(1), "Manish", "reseamanish@gmail.com", "9999999999")
    val knolObjectList = scala.collection.mutable.MutableList(knolObject1)
    assert(KnolRepoImplObj.getKnolderList() === Some(knolObjectList))

  }
}
