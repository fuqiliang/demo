package com.mstr.samples.partitoner

import java.util.concurrent.TimeUnit

import org.apache.spark.Partitioner
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StructField, StructType}
import org.apache.spark.sql.functions._

class OddEvenPartitioner extends Partitioner{
  override def numPartitions: Int = 2

  override def getPartition(key: Any): Int = {
     key.toString.toInt % 2
  }
}

object OddEvenPartitioner {
  def main(args: Array[String]): Unit = {
    val testData = Seq(Row(1),Row(2),Row(3),Row(4),Row(5),Row(6),Row(7))
    val sparkSession = SparkSession.builder().appName("OddEvenPartitioner")
      .master("local[2]").getOrCreate()

    val testRdd = sparkSession.sparkContext.parallelize(testData, 1)
    testRdd.partitioner
    val testSchema = StructType(List(
        StructField("num", IntegerType, false))
    )
    val testDF = sparkSession.createDataFrame(testRdd, testSchema)


    val newDF = testDF.rdd
    newDF.collect()

    val rdd2 = newDF.map(r => (r.getInt(0), r.getInt(0)))
      .groupByKey(4)
//        .groupByKey(new Partitioner {
//          override def numPartitions: Int = 2
//
//          override def getPartition(key: Any): Int = key.toString.toInt % 2
//        })
//    newDF.rdd.partitioner
    println(rdd2.partitions.length)
    rdd2.collect()
    TimeUnit.DAYS.sleep(1)

  }
}
