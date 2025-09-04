package examples

import java.text.DecimalFormat

object SampleDecimalFormat {
  def show(): Unit = {
    println("\n=== Sample Decimal Format Demo ===")
    val number = 1234567.89

    // format to 2 decimal places
    val decimalFormat = new java.text.DecimalFormat("#,##0.00")
    println(s"Decimal format: ${decimalFormat.format(number)}")

    // format to 4 decimal places
    val decimalFormat4 = new java.text.DecimalFormat("#,##0.0000")
    println(s"Decimal format 4: ${decimalFormat4.format(number)}")

    // ¥, $ format
    val yenFormat = new java.text.DecimalFormat("¥#,##0")
    val dollarFormat = new java.text.DecimalFormat("$#,##0.00")
    println(s"Yen format: ${yenFormat.format(number)}")
    println(s"Dollar format: ${dollarFormat.format(number)}")

    // 14 digits
    val Digit14 = 0.9149999999999999
    val format = new DecimalFormat("#.##############")
    val formattedNumber = format.format(Digit14)
    println(s"Formatted 14 digits number: $formattedNumber")

    // 13 digits
    val Digit13 = 0.9149999999999
    val formattedNumber13 = format.format(Digit13)
    println(s"Formatted 13 digits number: $formattedNumber13")


    val Digit14_1 = 0.9149999999999999
    val Digit14_2 = 1.1234333333333333
    val formattedDigit14_1 = format.format(Digit14_1)
    val formattedDigit14_2 = format.format(Digit14_2)
    println(s"Formatted 14 digits number 1: $formattedDigit14_1")
    // 0.915
    println(s"Formatted 14 digits number 2: $formattedDigit14_2")
    // 1.12343333333333
  }
}
