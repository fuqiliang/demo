package com.mstr

object Solution {

  def drawTriangles(n: Int) {

    def clearTriangles(n: Int, end: Int, previous:List[List[Int]]): List[List[Int]] = {
      if (n > end) previous
      else {
        clearTriangles(n + 1, end, drawZeroTriangles(n, previous))
      }
    }

    def drawZeroTriangles(n: Int, input: List[List[Int]]): List[List[Int]] = {
      if (n == 0) return input

      val gap = 32 >> n
      val maxOneCount = gap * 2 - 1
      var tmp: List[List[Int]] = input
      var rowIndex = gap

      def loop(startI: Int, endI: Int, as: List[Int], oneC:Int) : List[Int] = {
        if (startI < endI - 1) {
          val e = startI + maxOneCount
          val newLine = as.zipWithIndex.map(t => if (t._2 >= (startI + oneC ) && t._2 <= e) 0 else t._1)
          loop(e + 1, endI, newLine, oneC)
        } else {
          as
        }
      }

      while (rowIndex < 32 ) {
        var oneCount = 1
        while (oneCount <= maxOneCount) {
          val currentLine = tmp(rowIndex)
          val first1 = currentLine.indexOf(1)
          val last1 = currentLine.lastIndexOf(1)

          val newLine = loop(first1, last1, currentLine, oneCount)
          tmp = tmp.updated(rowIndex, newLine)

          oneCount += 2
          rowIndex += 1
        }
        rowIndex += gap
      }
      tmp
    }

    def printTriangles(g: List[List[Int]]) = g.foreach(as => {
      as.foreach(i => if(i == 0) print('_') else print(1))
      print("\n")
    })

    val init = (0 until 32).map(r => List.fill(31 - r)(0) ::: List.fill(1 + 2 * r)(1) ::: List.fill(31 - r)(0) ).toList
    val result = clearTriangles(0, n, init)
    printTriangles(result)

  }

  def maxProfit(prices: Array[Int]): Int = {
    1
  }


  def main(args: Array[String]) : Unit ={
//    drawTriangles(2)

//   val x = Array[Int]()


//    val x = List(1,2,3,4,5)
//    val y = x.filter(x => {println("a-" + x); x % 2 == 0})
//    val z = y.filter(y => {println("b-" + y); y > 3})
//    z.foreach(println(_))

  }

}
