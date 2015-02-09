/**
 * Trait: KnolQuery is designed to perform join operations on Knolder type records and KnolSession
 * type records.
 */

package com.knol.core
trait KnolQuery {
  // fetches the Knolsession held in a certain time period
  def getKnolSessionListByPersonName(personName: String): Option[scala.collection.mutable.MutableList[KnolSession]]
}
