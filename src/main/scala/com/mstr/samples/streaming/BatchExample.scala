package com.mstr.samples.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructType, TimestampType}
import org.apache.spark.sql.functions._

/**
  * https://docs.databricks.com/_static/notebooks/structured-streaming-scala.html
  */
object BatchExample {
  def main(args: Array[String]): Unit = {
    val inputPath = "/Users/yoga/Documents/workspace/bigdatademo/src/main/resources/events/"
    val jsonSchema = new StructType().add("time", TimestampType).add("action", StringType)
    val sparkSession = SparkSession.builder().appName("BatchExample")
      .master("local[2]").getOrCreate()

    // batch mode
    val staticInputDF = sparkSession.read.schema(jsonSchema).json(inputPath)
    val staticCountsDF = staticInputDF
      .groupBy(col("action"), window(col("time"), "1 hour"))
      .count().orderBy("action")

    staticCountsDF.printSchema()
    staticCountsDF.show(false)

    // streaming mode
    val streamingDF = sparkSession.readStream.schema(jsonSchema)
      .option("maxFilesPerTrigger", 1).json(inputPath)
    streamingDF.isStreaming
    val streamingCountsDF =
      streamingDF
        .groupBy(col("action"), window(col("time"), "1 hour"))
        .count()
    sparkSession.conf.set("spark.sql.shuffle.partitions", "1")
    val query =
      streamingCountsDF
        .writeStream
        .format("memory")        // memory = store in-memory table (for testing only in Spark 2.0)
        .queryName("counts")     // counts = name of the in-memory table
        .outputMode("complete")  // complete = all the counts should be in the table
        .start()


  }
}
