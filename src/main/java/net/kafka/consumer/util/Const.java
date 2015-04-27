package net.kafka.consumer.util;

public class Const {
	public static final String HBASE_TABLE_NAME = "hbase.tablename";
	public static final int MAX_INPUT_SPLIT_SIZE = 50 * 1000 * 1000;// MB
	public static final int MIN_INPUT_SPLIT_SIZE = 512 * 1000 * 1000;// MB

	public static final String INVALID_CHARACTOR_PATH = "/invalid.txt";

	public static final String HBASE_ADS_RECOM_UID = "UID_";
	public static final String HBASE_ADS_RECOM_ADID = "ADID_";

	
	public static final String HADOOP_MAP_SPECULATIVE_EXECUTION = "mapred.map.tasks.speculative.execution";
	public static final String HADOOP_REDUCE_SPECULATIVE_EXECUTION = "mapred.reduce.tasks.speculative.execution";

}
