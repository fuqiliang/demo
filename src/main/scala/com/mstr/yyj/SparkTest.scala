package com.mstr.yyj

import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

import org.apache.spark.sql.catalyst.expressions.Hour
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Column, Row, SparkSession}
import org.apache.spark.sql.functions._

object SparkTest {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark basic example")
      .master("local[4]")
      .getOrCreate()

    val context = spark.sparkContext
/*
    val data = Seq(
      Row("2013-11-11T12:12:13Z","2013-11-11T12:12:13Z"),
      Row("2013-11-11T10:11:13Z","2013-11-11 12:12:13")
    )
    val dataRdd = context.parallelize(data, 2)

    //RDD API
    val df = spark.createDataFrame(dataRdd,
      StructType(
        List(
          new StructField("c1", StringType),
          new StructField("c2", StringType)
        )
      )
    )

    df.createOrReplaceTempView("demo_table")
    val df2 = spark.sql("select c1, h from demo_table")
    //SQL API
    val res_cnt = df2.collect()
  */




    TimeUnit.DAYS.sleep(1)
  }
}
