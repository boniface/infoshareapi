package conf.connections

import java.net.InetAddress

import com.datastax.driver.core.SocketOptions
import com.outworkers.phantom.connectors.{ContactPoint, ContactPoints}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

/**
  * Created by hashcode on 2017/01/29.
  */
object Config {
  val config = ConfigFactory.load()
  val port = 9042
  val connectionTimeoutMillis = 7000 // Default is 5000
  val readTimeoutMillis = 15000 // Default is 12000
  val hosts: Seq[String] = Config.config.getStringList("cassandra.host").asScala.toList
  val inets = hosts.map(InetAddress.getByName)
  val keyspace: String = Config.config.getString("cassandra.keyspace")
//  val clusterName: String = Config.config.getString("cassandra.cluster.name")
}

object DefaultsConnector {
  val connector = ContactPoint.local.noHeartbeat().keySpace(Config.keyspace)
}

object DataConnection {

  lazy val connector = ContactPoints(Config.hosts, Config.port)
    .withClusterBuilder(
      _.withCredentials(Config.config.getString("cassandra.username"),
                        Config.config.getString("cassandra.password"))
        .withClusterName("Test Cluster")
        .withSocketOptions(new SocketOptions()
          .setReadTimeoutMillis(Config.readTimeoutMillis)
          .setConnectTimeoutMillis(Config.connectionTimeoutMillis))
      //        .withLoadBalancingPolicy(new TokenAwarePolicy(
      //          new DCAwareRoundRobinPolicy.Builder()
      //            .withUsedHostsPerRemoteDc(5)
      //            .withLocalDc("us-east").build()
      //        ))
    )
    .noHeartbeat()
    .keySpace(Config.keyspace)

  /**
    * Create an embedded connector, used for testing purposes
    */
  lazy val testConnector = ContactPoint.embedded.noHeartbeat().keySpace(Config.keyspace)
}
