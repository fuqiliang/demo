package com.mstr.yyj

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.StdIn

object Solution {
  def main(args: Array[String]): Unit = {

    val t = StdIn.readLine.trim.toInt

    for (tItr <- 1 to t) {
      val n = StdIn.readLine.trim.toInt

      val prices = StdIn.readLine.replaceAll("\\s+$", "").split(" ").map(_.trim.toInt)
      val result = stockmax(prices)

      println(result)
    }

  }

  def stockmax(prices: Array[Int]): Long = {
    val as = prices.toList

    def loop(stock: List[Int], profit: Int, stockCount: Int, samePriceHoldNum: Int) : Int = {
      stock match {
        case 0 :: 0 :: Nil => profit
        case cur :: next :: left if next < cur => loop(next :: left, profit + cur * stockCount, 0, 0) // sale
        case cur :: next :: left if next > cur => loop(next :: left, profit - cur * (samePriceHoldNum + 1) , stockCount + 1 + samePriceHoldNum, 0)
        case cur :: next :: left if next == cur => loop(next :: left, profit, stockCount, samePriceHoldNum + 1)
      }
    }

    loop(as ::: (0 :: 0 :: Nil), 0, 0, 0)

  }

  def isValidBST(input: List[Int]): Boolean = {
    val stack = mutable.Stack[Int]()

    def checkLoop(as: List[Int], lastPop: Int, firstTime: Boolean) : (Boolean, Int) = {
      as match {
        case Nil => (true, lastPop)
        case h :: t if stack.isEmpty || stack.top >= h => {
          stack.push(h)
          checkLoop(t, lastPop, firstTime)
        }
        case _ => {
          val tmp = stack.pop()
          if (firstTime || tmp >= lastPop) checkLoop(as, tmp, false)
          else (false, -1)
        }
      }
    }

    val (flag, lastPop) = checkLoop(input, 0, true)
    if (flag && stack.nonEmpty) stack.top >= lastPop
    else false
  }

  def fibonacciModified(t1: Int, t2: Int, n: Int): BigInt = {

    def go(current: Int, end: Int, left: BigInt, right: BigInt): BigInt = {
      if (current <= end) {
        go(current + 1 , end, right, left + right * right)
      } else {
        right
      }
    }

    go(3, n, BigInt(t1), BigInt(t2))
  }

  def fib(n: Int): Int = {
    def go(n: Int) : Int = {
      n match {
        case 0 => 0
        case 1 => 1
        case x : Int => fib(x-1) + fib(x-2)
      }
    }

    go(n)

  }

  def fibTail(n: Int) : Int = {

    @annotation.tailrec
    def go(current: Int, end: Int, left: Int , right: Int) : Int = {
      if (current <= end) {
        go (current + 1, end, right, left + right)
      } else {
        right
      }
    }

    go(0, n, -1, 1)
  }

  def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean = {
    def loop(i: Int, order: Boolean) : Boolean = {
      if (i == as.length - 1) true
      else if (order != ordered(as(i + 1), as(i))) false
      else loop(i + 1, order)
    }

    val len = as.length
    if (len <= 2) true
    else loop(1, ordered(as(1), as(0)))
  }

  def partial1[A,B,C](a: A, f: (A,B) => C): B => C = (b: B) => f(a, b)
  def curry[A,B,C](f: (A, B) => C): A => (B => C) = a => (b => f(a,b))
  def uncurry[A,B,C](f: A => B => C): (A, B) => C = (a,b) => f(a)(b)
  def compose[A,B,C](f: B => C, g: A => B): A => C = a => f(g(a))


}
