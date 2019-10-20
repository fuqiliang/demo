package com.mstr.samples

import java.sql.Timestamp

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, LongType, TimestampType}

import scala.collection.mutable.ListBuffer

case class Record(user:String, location:String, startTime:Timestamp, duration:Long)

object UserLogAnalysis {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[3]").setAppName("UserLog")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.sqlContext.implicits._
    val originDF = spark.read
      .option("header", false)
      .option("inferSchema", true)
      .format("csv")
      .load("/Users/yoga/Documents/workspace/bigdatademo/src/main/resources/userTrace.log")
      .withColumnRenamed("_c0","user")
      .withColumnRenamed("_c1", "location")
      .withColumnRenamed("_c2","startTime")
      .withColumnRenamed("_c3", "duration")
      .withColumn("startTime", col("startTime").cast(TimestampType))
      .withColumn("duration", col("duration").cast(LongType))
      .as[Record]

    val dfByUserAndLocation = originDF.repartition(col("user"), col("location"))

    val countedDF = dfByUserAndLocation.mapPartitions(it => {
      val newRows: ListBuffer[Record] = ListBuffer()
      var currentRow:Record = null
      var duration:Long = 0

      while (it.hasNext) {
        val tmpRow = it.next()
        if (currentRow == null) {
          currentRow = tmpRow
          duration = tmpRow.duration
        } else if (currentRow.startTime.getTime + duration * 60000 == tmpRow.startTime.getTime) {
          duration += tmpRow.duration
        } else {
          //new row generated
          newRows.append(Record(currentRow.user, currentRow.location, currentRow.startTime, duration))
          currentRow = tmpRow
        }
      }

      if (currentRow != null)
        newRows.append(Record(currentRow.user, currentRow.location, currentRow.startTime, duration))

      newRows.toIterator
    })

    countedDF.orderBy($"user",$"startTime").show()

  }

}
