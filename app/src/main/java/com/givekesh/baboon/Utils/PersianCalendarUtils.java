/**
 * Persian Calendar see: http://code.google.com/p/persian-calendar/
 * Copyright (C) 2012  Mortezaadi@gmail.com
 * PersianCalendarUtils.java
 * <p/>
 * Persian Calendar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.givekesh.baboon.Utils;

/**
 * algorithms for converting Julian days to the Persian calendar, and vice versa
 * are adopted from <a
 * href="casema.nl/couprie/calmath/persian/index.html">couprie.nl</a> written in
 * VB. The algorithms is not exactly the same as its original. I've done some
 * minor changes in the sake of performances and corrected some bugs.
 *
 * @author Morteza contact: <a
 *         href="mailto:Mortezaadi@gmail.com">Mortezaadi@gmail.com</a>
 * @version 1.0
 */
class PersianCalendarUtils {

    private static final long PERSIAN_EPOCH = 1948321;

    /**
     * Converts a provided Persian (Shamsi) date to the Julian Day Number (i.e.
     * the number of days since January 1 in the year 4713 BC). Since the
     * Persian calendar is a highly regular calendar, converting to and from a
     * Julian Day Number is not as difficult as it looks. Basically it's a
     * mather of dividing, rounding and multiplying. This routine uses Julian
     * Day Number 1948321 as focal point, since that Julian Day Number
     * corresponds with 1 Farvardin (1) 1.
     *
     * @param year  int persian year
     * @param month int persian month
     * @return long
     */
    private static long persianToJulian(long year, int month) {
        return 365L * ((ceil(year - 474L, 2820D) + 474L) - 1L) + ((long) Math.floor((682L * (ceil(year - 474L, 2820D) + 474L) - 110L) / 2816D)) + (PERSIAN_EPOCH - 1L) + 1029983L
                * ((long) Math.floor((year - 474L) / 2820D)) + (month < 7 ? 31 * month : 30 * month + 6) + 1;
    }


    /**
     * Converts a provided Julian Day Number (i.e. the number of days since
     * January 1 in the year 4713 BC) to the Persian (Shamsi) date. Since the
     * Persian calendar is a highly regular calendar, converting to and from a
     * Julian Day Number is not as difficult as it looks. Basically it's a
     * mather of dividing, rounding and multiplying.
     *
     * @param julianDate long
     * @return long
     */
    public static long julianToPersian(long julianDate) {
        long persianEpochInJulian = julianDate - persianToJulian(475L, 0);
        long cyear = ceil(persianEpochInJulian, 1029983D);
        long ycycle = cyear != 1029982L ? ((long) Math.floor((2816D * (double) cyear + 1031337D) / 1028522D)) : 2820L;
        long year = 474L + 2820L * ((long) Math.floor(persianEpochInJulian / 1029983D)) + ycycle;
        long aux = (1L + julianDate) - persianToJulian(year, 0);
        int month = (int) (aux > 186L ? Math.ceil((double) (aux - 6L) / 30D) - 1 : Math.ceil((double) aux / 31D) - 1);
        int day = (int) (julianDate - (persianToJulian(year, month) - 1L));
        return (year << 16) | (month << 8) | day;
    }

    /**
     * Ceil function in original algorithm
     *
     * @param double1 double
     * @param double2 double
     * @return long
     */
    private static long ceil(double double1, double double2) {
        return (long) (double1 - double2 * Math.floor(double1 / double2));
    }
}
