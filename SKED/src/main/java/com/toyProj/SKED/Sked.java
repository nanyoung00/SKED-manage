package com.toyProj.SKED;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


public class Sked {
    public static int makeMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            // 2월
            if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }



    public static void calender(HashMap<String, ArrayList<String>> listMap, int year, int month) {
        System.out.println(" " + year + "년 " + month + "월의 달력");

        // 1583년부터 year까지 더한 변수
        int sum = 0;
        for(int i = 1583; i<year; i++) {
            if((i%4==0 && i%100 != 0) || i%400 == 0) {
                sum += 2;
            } else {
                sum += 1;
            }
        }
        // year년의 1월 1일 요일
        int yearFirstDay = (sum + 6) % 7;
        for(int j=1; j<month; j++) {
            yearFirstDay += makeMonth(year, j) % 7;
        }

        int monthFirstDay = yearFirstDay % 7; // 해당 month의 1일의 요일
        int monthDay = 1; // month의 일 표시
        int maxMonthDay = makeMonth(year, month); // 해당 month가 가지는 최대 일 수
        // 여기 뭔가 추가 될 수도 있음(밑에서 첫 행에 빈칸이 존재할 경우에서 쓰임)
        boolean start = false;

        System.out.println(" Sun Mon Tue Wed Thu Fri Sat ");

        loop: for(int row = 0; row <= 5; row++ ) {
            for(int col = 0; col <= 6; col++) {
                if(row == 0 && !start) {
                    if(col == monthFirstDay) {
                        start = true;
                    } else {
                        System.out.print("    ");
                        continue;
                    }
                }

                System.out.printf("  %02d", monthDay);
                monthDay++;

                if(monthDay > maxMonthDay) {
                    break loop;
                }
            }
            System.out.println("");
        }
    }





    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> listMap = new HashMap<String, ArrayList<String>>();

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] toDate = today.split("-");
        calender(listMap, Integer.parseInt(toDate[0]), Integer.parseInt(toDate[1])); // 오늘 날짜가 포함 된 월의 달력 자동 출력



    }


}
