package com.mstr.samples

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DoubleType

object Average {

  case class Record(name: String, course: String, grade: Double)

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("countAverage")
      .master("local[2]").getOrCreate()
    val location = "/Users/yoga/Documents/workspace/bigdatademo/src/main/resources/grades.txt"

    import sparkSession.sqlContext.implicits._

    val originDF = sparkSession.read
      .option("header", false)
      .option("inferSchema", true)
      .format("csv")
      .load(location)
      .withColumnRenamed("_c0","name")
      .withColumnRenamed("_c1","course")
      .withColumnRenamed("_c2","grade")
      .withColumn("grade", col("grade").cast(DoubleType))
      .as[Record]

    val averageDF = originDF.groupBy("name").agg(avg("grade"))
    averageDF.show()

  }

}
