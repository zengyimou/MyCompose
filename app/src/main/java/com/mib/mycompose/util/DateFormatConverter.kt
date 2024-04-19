package com.mib.mycompose.util

import android.os.Build
import android.text.TextUtils
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @author lindan
 * 日期格式化工具
 */
object DateFormatConverter {
    const val DATE_FORMAT1 = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT2 = "yyyy/MM/dd HH:mm"
    const val DATE_FORMAT3 = "MM-dd-yyyy"
    const val DATE_FORMAT4 = "yyyy-MM-dd"
    const val DATE_FORMAT5 = "yyyy-MM-dd,HH:mm"
    const val DATE_FORMAT6 = "MM-dd,HH:mm"
    const val DATE_FORMAT7 = "yyyy/MM/dd HH:mm:ss"
    const val DATE_FORMAT8 = "HH:mm"
    const val DATE_FORMAT9 = "yyyy/MM/dd, HH:mm:ss"
    const val DATE_FORMAT10 = "dd-MM-yyyy"

    const val TAG = "DateFormatConverter"

    /**
     * 将yyyy-MM-ddTHH:mm:ss转换为日期类型
     */
    fun convertToDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return try {
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            null
        }

    }

    fun convertToDate(dateString: String, pattern: String): Date? {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }

    /**
     * 判断一个日期是否今天
     */
    fun isToday(date: Date): Boolean {
        val now = Date()
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val nowDay = dateFormat.format(now)
        val day = dateFormat.format(date)
        return day == nowDay
    }

    /**
     * 判断一个日期是否同年
     */
    fun isSameYear(date: Date): Boolean {
        val now = Date()
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val nowYear = dateFormat.format(now)
        val year = dateFormat.format(date)
        return year == nowYear
    }

    /**
     * 判断一个日期是否在今天之前
     */
    fun beforeToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        return date.before(today.time)
    }

    /**
     * 判断一个日期是否明天
     */
    fun isTomorrow(date: Date): Boolean {
        val tomorrow = Date(System.currentTimeMillis() + 3600000 * 24)
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val tomorrowDay = dateFormat.format(tomorrow)
        val day = dateFormat.format(date)
        return day == tomorrowDay
    }

    /**
     * 判断一个日期是否后天
     */
    fun isAfterTomorrow(date: Date): Boolean {
        val tomorrow = Date(System.currentTimeMillis() + 3600000 * 48)
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val tomorrowDay = dateFormat.format(tomorrow)
        val day = dateFormat.format(date)
        return day == tomorrowDay
    }

    /**
     * 判断两个日期是否同一天
     */
    fun isTheSameDay(date1: Date, date2: Date): Boolean {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date1String = dateFormat.format(date1)
        val date2String = dateFormat.format(date2)
        return TextUtils.equals(date1String, date2String)
    }

    /**
     * 判断一个日期是否昨天
     */
    fun isYesterday(date: Date): Boolean {
        val yesterday = Date(System.currentTimeMillis() - 3600000 * 24)
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val nowDay = dateFormat.format(yesterday)
        val day = dateFormat.format(date)
        return day == nowDay
    }

    /**
     * 判断一个日期是否在本月
     */
    fun isInThisMonth(date: Date): Boolean {
        val today = Date()
        val dateFormat = SimpleDateFormat("yyyyMM", Locale.getDefault())
        val nowDay = dateFormat.format(today)
        val day = dateFormat.format(date)
        return day == nowDay
    }

    /**
     * 判断两个日期是否在同一个月中
     */
    fun isTheSameMonth(date1: Calendar, date2: Calendar): Boolean {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
    }

    /**
     * 获取日期的周几字符串表示
     */
    fun getDayOfString(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "周日"
            Calendar.MONDAY -> "周一"
            Calendar.TUESDAY -> "周二"
            Calendar.WEDNESDAY -> "周三"
            Calendar.THURSDAY -> "周四"
            Calendar.FRIDAY -> "周五"
            Calendar.SATURDAY -> "周六"
            else -> ""
        }
    }

    /**
     * 获取自定义格式的日期字符串
     */
    fun getCustomString(date: Date, pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            dateFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取日期的yyyy年MM月显示字符串
     */
    fun getYearMonthString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy年MM月", Locale.getDefault())
        return try {
            dateFormat.format(date)
        } catch (e: Exception) {
            ""
        }

    }

    /**
     * 获取日期的周几字符串表示
     *
     * @param date
     * @return
     */
    fun getDayLabel(date: Date): String {
        if (isToday(date)) {
            return "今天"
        }
        return if (isYesterday(date)) {
            "昨天"
        } else getDayOfString(date)
    }

    fun getTimeLabel(date: Date): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT8, Locale.getDefault())
        return try {
            dateFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun transToString(time: Long, format:String = "yyyy-MM-dd-HH-mm-ss"):String {
        return SimpleDateFormat(format, Locale.getDefault()).format(time)
    }

    /**
     * 总时间转换
     */
    fun transSumTimeString(time: Long):String {
        return if(time < 60){
            //秒
            "$time s"
        }else if(time in 60..3599){
            //分
            val resultTime = BigDecimal(time/60.0).setScale(2, BigDecimal.ROUND_HALF_UP)
            "${resultTime} m"
        }else if(time >= 3600 && time < 3600 * 24){
            //时
            val resultTime = BigDecimal(time/3600.0).setScale(2, BigDecimal.ROUND_HALF_UP)
            "${resultTime} h"
        }else{
            "$time"
        }
    }

    fun transSumTime(time: Long):String {
        return if(time < 60){
            //秒
            val seconds = if(time > 9) time else "0${time}"
            "00:$seconds"
        }else if(time in 60..3599){
            //分
            val min = time/60
            val diff = time - min * 60
            val seconds = if(diff > 9) diff else "0${diff}"
            "${min}:${seconds}"
        }else{
            "$time"
        }
    }

    fun getFeedbackTimeString(date: Date): String{
        val format = when {
            isToday(date) -> {
                "HH:mm:ss"
            }
            isSameYear(date) -> {
                "dd/MM HH:mm:ss"
            }
            else -> {
                "dd/MM/yyyy HH:mm:ss"
            }
        }
        return getCustomString(date, format)
    }

    fun getWhatsappMessageTimeFormat(time: Long): String {
        val date = Date(time)
        val format = when {
            isToday(date) -> {
                DATE_FORMAT8
            }
            isSameYear(date) -> {
                "MM-dd,HH:mm"
            }
            else -> {
                "dd/MM/yyyy HH:mm"
            }
        }
        return getCustomString(date, format)
    }

    /**
     *  通过生日计算出年龄
     * @param birthday String
     * @return Int
     */
    fun getAgeFromBirthday(birthday: String): Int {
        // 计算年龄差距
        val age = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try{
                // 定义日期格式
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                // 获取当前日期
                val currentDate = LocalDate.now()
                // 将生日日期和当前日期转换为 LocalDate 对象
                val birthDate = LocalDate.parse(birthday, formatter)
                Period.between(birthDate, currentDate).years
            }catch (e: Exception) {
                return 0
            }
        } else {
            // 获取当前日期
            val currentDate = Calendar.getInstance()
            // 将生日日期转换为 Calendar 对象
            val birthDate = Calendar.getInstance()
            val dateParts = birthday.split("/")
            if(dateParts.isNotEmpty() && dateParts.size == 3){
                birthDate.set(dateParts[2].toInt(), dateParts[1].toInt() - 1, dateParts[0].toInt())
            }
            // 计算年龄差距
            var flag = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
            if (currentDate.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
                (currentDate.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) &&
                        currentDate.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))
            ) {
                flag--
            }
            flag
        }
        return age
    }

    /**
     * example :12m ago 13h ago
     * @param str String
     * @return String
     */
    fun getAgoString(str: String): String{
        val date = convertToDate(str, DATE_FORMAT1)
        if (date == null){
            return str
        }else{
            val time = (Date().time - date.time) / 1000L
            return when {
                time < 3600 -> {
                    "${time / 60L}min ago"
                }
                time < 24 * 3600 -> {
                    "${time / 3600L}h ago"
                }
                else -> {
                    "${time / 86400L}d ago"
                }
            }
        }
    }

    /**
     * 获取当前时间戳当天0点
     * @return Long
     */
    fun getTodayStartTime(timestamp: Long = 0L): Long {
        val date = if(timestamp == 0L) Date() else Date(timestamp)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        //去掉毫秒
        return calendar.time.time / 1000
    }

    /**
     * 通讯记录 生成过去时间格式
     * @param timestamp Long
     * @return String
     */
    fun getAgoString(timestamp: Long = 0L): String {
        /** 当日时间 秒级*/
        val time = timestamp / 1000
        /** 一天的时间 秒级*/
        val oneDayTimestamp = 60 * 60 * 24L
        /** 当日0点时间 秒级*/
        val todayTimestamp = getTodayStartTime(Date().time)
        /** 昨日0点时间 秒级*/
        val yesterdayTimestamp = todayTimestamp - oneDayTimestamp
        return if(time > todayTimestamp){
            //当天
            getCustomString(Date(timestamp), DATE_FORMAT5)
        }else if(time > yesterdayTimestamp){
            //昨天
            "Yesterday"
        }else if(time > (todayTimestamp - 5 * oneDayTimestamp)){
            //五天內
            getDayOfString(Date(timestamp))
        }else{
            //五天外
            getCustomString(Date(timestamp), DATE_FORMAT6)
        }
    }

    /**
     * 格式化时分秒
     * @param seconds Long
     * @return String
     */
    fun formatDuration(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    }


    /**
     * Get today zero timestamp
     *
     * @return
     */
    fun getTodayZeroTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val zeroTimestamp = calendar.timeInMillis
        println("当日零点时间戳: $zeroTimestamp")
        return zeroTimestamp
    }

}
