package com.mstr.yyj

import org.apache.spark.sql.types.{StructField, StructType, StringType, DoubleType}
import org.apache.spark.sql.{Row, SparkSession, types}

object SqlTest {
  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder()
      .appName("Spark basic example")
      .master("local[4]")
      .getOrCreate()

    val sqlContext = session.sqlContext

    val data = Seq(Row("bj", Row(1.0,2.0)), Row("bj", Row(1.1,2.1)))
    val rddRes =session.sparkContext.parallelize(data, 3)

    val schemaRes = types.StructType(
      StructField("key", StringType, false) ::
        StructField("location", StructType(
          StructField("lat", DoubleType, false) ::
            StructField("long", DoubleType, false) :: Nil
        ), true) :: Nil
    )

    val df = sqlContext.createDataFrame(rddRes, schemaRes)
    df.show()
    df.createOrReplaceTempView("testT")

    df.printSchema()
    val r1 = sqlContext.sql("select key, location.* from testT")
    r1.show()

  }
}
