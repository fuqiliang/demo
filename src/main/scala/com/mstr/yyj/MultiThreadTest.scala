package com.mstr.yyj

import java.util.concurrent.{Executors, ThreadFactory, TimeUnit}
import java.util.concurrent.atomic.AtomicInteger

import org.apache.spark.sql.SparkSession


object MultiThreadTest {
  def main(args: Array[String]): Unit = {
    val executors = Executors.newFixedThreadPool(3, new ThreadFactory {
      val counter = new AtomicInteger(0)
      override def newThread(runnable: Runnable): Thread = {
        new Thread(runnable, "thread-" + counter.getAndIncrement())
      }
    })
    for (i <- 0 to 3) {
      executors.submit(new Task(i))
//      executors.execute(new Task(i))
    }


    TimeUnit.DAYS.sleep(1)
    executors.shutdown()
  }


  class Task(val id: Int) extends Runnable {
    override def run(): Unit = {
      /*
      val session = SparkSession
        .builder()
        .appName(s"""Spark basic example $id""")
        .master("local[4]")
        .getOrCreate()

      println(Thread.currentThread().getName + session)
      */

      try {
        throw new OutOfMemoryError("test not caught")
      } catch {
        case e: Exception => println("thread caught exception " + e.getMessage)
      }

    }
  }
}
