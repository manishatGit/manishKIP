/*
 * Trait: Connection provides basic functionality of creating a database
 * connection. Initializes values of database url, user, passwords configured in
 * configuration file application.conf
 */
package com.knol.db.connection

import com.typesafe.config.ConfigFactory
import java.sql._
import org.slf4j.LoggerFactory
trait Connection {
  val logger = LoggerFactory.getLogger(this.getClass)
  val config = ConfigFactory.load()
  val url = config.getString("db.url")
  val usr = config.getString("db.user")
  val password = config.getString("db.pass")
    def getConnection(): Option[java.sql.Connection]
}
