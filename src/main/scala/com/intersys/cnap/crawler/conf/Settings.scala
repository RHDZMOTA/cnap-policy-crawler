package com.intersys.cnap.crawler.conf

import akka.stream.OverflowStrategy
import com.typesafe.config.{Config, ConfigFactory}
import scala.concurrent.duration._

object Settings {
  private val app: Config = ConfigFactory.load().getConfig("application")

  object Cassandra {
    private val cassandra: Config = app.getConfig("cassandra")
    val address: String = cassandra.getString("address")
    val port: Int       = cassandra.getInt("port")
    val keyspaceName: String  = cassandra.getString("keyspaceName")
    object Url {
      val url: Config = cassandra.getConfig("url")
      val table: String = url.getString("table")
      val parallelism: Int = url.getInt("parallelism")
    }
    object Answer {
      val answer: Config = cassandra.getConfig("answer")
      val table: String = answer.getString("table")
      val parallelism: Int = answer.getInt("parallelism")
    }
  }

  object Postgres {
    private val postgres: Config = app.getConfig("postgres")
    val driver: String = "org.postgresql.Driver"
    val address: String   = postgres.getString("address")
    val port: String      = postgres.getString("port")
    val database: String  = postgres.getString("database")
    val username: String  = postgres.getString("username")
    val pwd: String        = postgres.getString("pwd")
    object Url {
      val url: Config       = postgres.getConfig("url")
      val table: String     = url.getString("table")
      val parallelism: Int  = url.getInt("parallelism")
    }
    object Answer {
      val answer: Config    = postgres.getConfig("answer")
      val table: String     = answer.getString("table")
      val parallelism: Int  = answer.getInt("parallelism")
    }
  }

  object Crawler {
    private val crawler: Config = app.getConfig("crawler")
    val parallelism: Int        = crawler.getInt("parallelism")
    val depth: Int              = crawler.getInt("depth")
    val seedUrl: String         = crawler.getString("seedUrl")
    val urlLifeSpan: FiniteDuration = crawler.getInt("urlLifeSpan").second
  }

  object Source {
    private val source: Config  = app.getConfig("source")
    val bufferSize: Int         = source.getInt("bufferSize")
    val overflowStrategy: OverflowStrategy = source.getString("overflowStrategy") match {
      case "dropBuffer" => OverflowStrategy.dropBuffer
      case "dropHead"   => OverflowStrategy.dropHead
      case "dropTail"   => OverflowStrategy.dropTail
      case _ => OverflowStrategy.fail
    }
  }

  object Solr {
    private val solr: Config = app.getConfig("solr")
    val parallelism: Int   = solr.getInt("parallelism")
    val collection: String = solr.getString("collection")
    val address: String = solr.getString("address")
    val port: String    = solr.getString("port")
  }
}
