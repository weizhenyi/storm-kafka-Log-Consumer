package net.kafka.consumer.adclick;

import java.util.ArrayList;
import java.util.List;

import net.kafka.consumer.util.ConfigFactory;
import net.kafka.consumer.util.ConfigProperties;
import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.KafkaConfig.StaticHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;

public class AdclickMain {

	public static void main(String[] args) throws Exception {

		ConfigProperties cp = ConfigFactory.getInstance().getConfigProperties(
				ConfigFactory.APP_CONFIG_PATH);
		System.out.println(cp.getProperty(ConfigProperties.CONFIG_NAME_HBASE_ZOOKEEPER_QUORUM));
		
		List<String> hosts = new ArrayList<String>();
		hosts.add("192.168.1.2");
		

		SpoutConfig kafkaConf = new SpoutConfig(StaticHosts.fromHostString(
				hosts, 1), "advtest5", "/kafkastorm", "adclick");
		kafkaConf.scheme = new StringScheme();
		kafkaConf.zkPort = 2181;
		kafkaConf.forceStartOffsetTime(-2); // To start from beginning of topic
		KafkaSpout kafkaSpout = new KafkaSpout(kafkaConf);

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", kafkaSpout, 4);
		builder.setBolt("combined-rk", new AdClickCombinedRowkey())
				.shuffleGrouping("spout");
		builder.setBolt("cnt", new AdclickUpdate()).shuffleGrouping(
				"combined-rk");

		Config config = new Config();
		// config.setDebug(true);
		config.setNumWorkers(4);
		config.setMaxSpoutPending(1000);
		StormSubmitter.submitTopology("adclick-topology", config,
				builder.createTopology());

	}
}
