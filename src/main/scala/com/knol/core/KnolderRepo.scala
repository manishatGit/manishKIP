/**
 * Trait: KnolderRepo declares all CRUD methods to be performed on class Knolder
 *
 */
package com.knol.core

trait KnolderRepo {
  // creates a knolder record in the table
  def createKnolder(knolder: Knolder): Option[Int]
  // updates a knolder record in the table
  def updateKnolder(knolder: Knolder): Option[Int]
  // deletes a knolder record in the table
  def deleteKnolder(knolderId: Int): Option[Int]
  // selects list of knolder records in the table
  def getKnolderList(): Option[scala.collection.mutable.MutableList[Knolder]]
}

//Case class: KnolSession represents the structure of record KnolSession
case class Knolder(val id: Option[Int], val name: String, val email: String, val mobile: String)

