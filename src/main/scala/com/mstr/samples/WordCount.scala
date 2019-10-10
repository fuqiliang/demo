package com.mstr.samples

import java.util.concurrent.TimeUnit

import org.apache.spark.sql.SparkSession

object WordCount {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("countAverage")
      .master("local[2]").getOrCreate()
    val location = "/Users/yoga/Documents/workspace/bigdatademo/src/main/resources/countword.txt"

    val originDF = sparkSession.sparkContext.textFile(location, 2);
    originDF.flatMap(s => s.split(" ")).map(s => (s, 1)).reduceByKey(_ + _)

    TimeUnit.DAYS.sleep(1);
  }

}
