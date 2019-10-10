package com.mstr.yyj

import java.util.concurrent.TimeUnit

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.functions._

/**
  * Hello world!
  *
  */
object App {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local[4]")
      .getOrCreate()

    //    val conf = new SparkConf()
    //      .setAppName("testSpark")
    //      .setMaster("local[4]")
    //    val sc = new SparkContext(conf)

    val data = Seq(Row("12/1/2001 12:00:11", "2001/11/01 12:00:11", "12/11/2001", "22-10-01"))
    val schema = new StructType(Array(
      new StructField("datetime1", DataTypes.StringType),
      new StructField("datetime2", DataTypes.StringType),
      new StructField("datetime3", DataTypes.StringType),
      new StructField("datetime4", DataTypes.StringType)
    ))

    val dataRdd = spark.sparkContext.parallelize(data)
    val df = spark.createDataFrame(dataRdd, schema)

    var resultDf = df.withColumn("datetime1", to_timestamp(col("datetime1"), "MM/d/yyyy HH:mm:ss"))
    resultDf = resultDf.withColumn("datetime2", to_timestamp(col("datetime2"), "yyyy/dd/MM HH:mm:ss"))
    resultDf = resultDf.withColumn("datetime3", to_date(to_timestamp(col("datetime3"), "dd/MM/yyyy")))
    resultDf = resultDf.withColumn("datetime4", to_timestamp(col("datetime4"), "HH-mm-ss"))

    resultDf.show()
    resultDf.printSchema()

    TimeUnit.DAYS.sleep(1)

  }
}
