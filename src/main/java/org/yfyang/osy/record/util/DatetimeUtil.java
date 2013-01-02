/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:DatetimeUtil.java  11-6-8 上午1:42 poplar.mumu ]
 */
package org.yfyang.osy.record.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:42
 * @since JDK 1.0
 */
public class DatetimeUtil {
    /**
     * 获取当前日期
     *
     * @return 当前年月日
     */
    public static String dateTime() {
        Calendar calendar = getCalendar(new Date());
        return new StringBuilder().append(calendar.get(Calendar.YEAR)).append("-")
                .append(joinTime(calendar.get(Calendar.MONTH) + 1)).append("-")
                .append(joinTime(calendar.get(Calendar.DAY_OF_MONTH))).append(" ")
                .append(joinTime(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(joinTime(calendar.get(Calendar.MINUTE))).append(":")
                .append(joinTime(calendar.get(Calendar.SECOND)))
                .toString();
    }

    /**
     * 对月份和日期判断长度是否为2，如果不是，+0返回。
     *
     * @param mday 月份或者日期
     * @return 长度高于2 返回本身，小于2，+0返回
     */
    private static String joinTime(int mday) {
        return String.format("%02d", mday);
    }

    /**
     * 将给定时间转换为日历类
     *
     * @param date 给定时间
     * @return 默认日历
     */
    private static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(LOCALE);
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar;
    }

    /**
     * 本地区域
     */
    private static final Locale LOCALE = Locale.CHINESE;
}
