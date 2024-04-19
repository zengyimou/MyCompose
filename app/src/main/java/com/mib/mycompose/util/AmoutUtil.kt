package com.mib.mycompose.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


/**
 * @author yimou
 */
object AmoutUtil {


    /**
     * 金额转换成k
     * @param str String
     * @return String
     */
    fun changeTextToThousand(str: String): String {
        var strBuffer = StringBuffer()
        val b3 = BigDecimal(str)
        val b1 = BigDecimal("1000") // 千 k
        val b2 = BigDecimal("1000000") // 百万 m

        var formatStr = ""
        var unit = ""

        if(b3.compareTo(b1) == -1){
            //没超过1000
            return changeFormat(str)
        }else if((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1) || b3.compareTo(b2) == -1){
            unit = "k"
            formatStr = changeFormat(b3.divide(b1).toString())
        }else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            unit = "m"
            formatStr = changeFormat(b3.divide(b2).toString())
        }

        return strBuffer.append(formatStr).append(unit).toString()
    }

    /**
     * 金额添加逗号
     * @param str String
     * @return String
     */
    fun changeFormat(str: String): String {
        return getFormat(",###,###,###.##", BigDecimal(str))?: ""
    }

    //自定义数字格式方法
    private fun getFormat(style: String?, value: BigDecimal): String? {
        val df = DecimalFormat()
        df.applyPattern(style) // 将格式应用于格式化器
        return df.format(value)
    }

    fun formatFloatNumber(str: String): String {
        if (str.isEmpty()) {
            return ""
        }
        val df = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
        df.applyPattern(",###,###,###.##") // 将格式应用于格式化器
        return df.format(BigDecimal(str))
    }

    /**
     * 西语金钱格式
     * @param str String
     * @return String
     */
    fun formatESFloatNumber(str: String): String {
        if (str.isEmpty()) {
            return ""
        }
        val d = java.lang.Double.valueOf(str)
        val numberFormat = NumberFormat.getInstance(Locale("es"))
        return numberFormat.format(d)
    }



}