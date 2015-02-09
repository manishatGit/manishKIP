/*
 * Trait: KnolSessionRepo declares all CURD methods to be applied
 * on KnolSession records
 */
package com.knol.core

trait KnolSesssionRepo {
  // Inserts a knolSession in the database table KnolSession
  def createKnolSession(knolSession: KnolSession): Option[Int]
  // Updates a knolSession into the database table KnolSession
  def updateKnolSession(knolSession: KnolSession): Option[Int]
  //Deletes a knolSession from the database table KnolSession
  def deleteKnolSession(knolSessionId: Int): Option[Int]
  // Fetches the list of knolSession records from the database table KnolSession
  def getKnolSessionList(): Option[scala.collection.mutable.MutableList[KnolSession]]
}

//Case class: KnolSession represents the structure of record KnolSession
case class KnolSession(val id: Option[Int], val topic: String, val date: String, val KnolId: Int)

