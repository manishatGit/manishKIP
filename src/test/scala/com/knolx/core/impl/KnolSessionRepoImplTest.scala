/**
 * class KnolSessionRepoImplTest: asserts all unit test on methods
 * defined in the class KnolSessionRepoImpl
 */

package com.knolx.core.impl

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import com.knol.core.KnolSession

class KnolSessionRepoImplTest extends FunSuite with BeforeAndAfter {
  val logger = LoggerFactory.getLogger(this.getClass)
  val knolSessionImplObj = new KnolSessionRepoImpl()
  /**
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

  /*Tests the InsertKnolSession Negatively 
   * by passing the Wrong Query and generating an Sql Exception
   * */
  test("Creating the record KnolSession By giving Wrong Date format") {

    val sessionDate = "20-0j-12" //Wrong Date
    val knolObject = KnolSession(Some(1), "Scala in Business", sessionDate, 1)
    assert(knolSessionImplObj.createKnolSession(knolObject) === None)
  }
  /*Tests the method createKnolder() by passing on KnolSession object and comparing it with expected 
     * resulting object values of record KnolSession
     */
  test("Creating the record KnolSession") {

    val sessionDate = "2015-02-12"
    val knolObject = KnolSession(Some(1), "Scala in Business", sessionDate, 1)
    assert(knolSessionImplObj.createKnolSession(knolObject) === Some(2))
  }

  /*
     *Tests the method updateKnolder() by passing on KnolSession object and comparing it with expected 
     * resulting object values of record KnolSession (changing the session_date value of the knolx)
     */

  test("Updating the record KnolSession") {

    val sessionDate = "2015-02-24"
    val knolObject = KnolSession(Some(2), "Scala in Business", sessionDate, 1)
    assert(knolSessionImplObj.updateKnolSession(knolObject) === Some(1))
  }
  test("Updating the record KnolSession By giving Wrong ID format") {
    val sessionDate = "20-0j-12" //Wrong Date
    val knolObject = KnolSession(Some(-12), "Scala in Business", sessionDate, 1)
    assert(knolSessionImplObj.updateKnolSession(knolObject) === None)
  }
  /*Tests the DeleteKnolSession Negatively 
   * by passing the Wrong Query and generating an Sql Exception
   * */
  test("Deleting the record KnolSession By giving Wrong ID format") {

    val sessionDate = "20-0j-12" //Wrong Date
    val knolObject = KnolSession(Some(-12), "Scala in Business", sessionDate, 1)
    assert(knolSessionImplObj.updateKnolSession(knolObject) === None)
  }

  /*Tests the deletion of records from the database table knolsession 
   * returns the number of records deleted, None if no row was deleted
   */
  test("Deleting the record KnolSession") {
    assert(knolSessionImplObj.deleteKnolSession(1) === Some(1))
  }

  /*Tests the method getKnolSessionList(). The scenario is to get the list of records 
   * So far in the table KnolSession store it in a Mutable list of type KnolSession
   * and compare it with the following output.
   */
  test("Fetching the list KnolSessions") {
    assert(knolSessionImplObj.getKnolSessionList() === Some(scala.collection.mutable.MutableList(KnolSession(Some(1), "Scala Collections", "2015-02-15", 1))))
  }
  /*Tests the method getKnolSessionList(). The scenario is to get the list of records 
   * So far Since we are going to delete all records from the table it should return None.
   */
}
