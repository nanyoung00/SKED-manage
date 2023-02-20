package com.toyProj.SKED;

import java.sql.SQLOutput;
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
            continue;
        }
    }





    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, ArrayList<String>> listMap = new HashMap<String, ArrayList<String>>();

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] toDate = today.split("-");
        calender(listMap, Integer.parseInt(toDate[0]), Integer.parseInt(toDate[1])); // 오늘 날짜가 포함 된 월의 달력 자동 출력

        System.out.println("");
        System.out.println("");
        System.out.println("==============================");
        System.out.println(" 1. 일정 등록 ");
        System.out.println(" 2. 일정 조회 & 수정 & 삭제 ");
        System.out.println(" 3. 달력 이동");
        System.out.println(" h. 도움말 ");
        System.out.println(" q. 종료 ");
        System.out.println("==============================");

        while(true) {
            System.out.print("선택해라 [ 1.일정 등록 | 2.일정 조회 & 수정 & 삭제 | 3. 달력 이동 | h.도움말 | q.종료 ] \n");
            char order = sc.next().charAt(0);
            sc.nextLine();

            switch(order) {
                // 일정 등록
                case '1':
                    System.out.print("일정을 등록 할 날짜를 입력하세요.(ex: 2023-02-15)\n>> ");
                    String inputDate = sc.nextLine();


                    if(!listMap.containsKey(inputDate)) {
                        ArrayList<String> newList = new ArrayList<String>();
                        listMap.put(inputDate, newList);
                    }

                    System.out.print("등록할 일정을 입력하세요.\n");
                    String inputList = sc.nextLine();

                    ArrayList<String> existList = listMap.get(inputDate);
                    // 날짜에 이미 기존 일정이 있는데 더 추가할 경우
                    existList.add(inputList);
                    listMap.put(inputDate, existList);
                    break;


                // 일정 조회 & 수정 & 삭제
                case '2':
                    System.out.print("조회할 날짜를 입력하세요.(ex: 2023-02-20)\n>> ");
                    String findDate = sc.nextLine();
                    ArrayList<String> schedule = listMap.get(findDate);

                    if(listMap.containsKey(findDate)) {
                        // 해당 날짜에 일정이 있을 경우
                        // ArrayList<String> schedule = listMap.get(findDate);

                        System.out.printf("%d개의 일정이 있습니다.\n", schedule.size());

                        for(int i=0; i<schedule.size(); i++) {
                            // 존재하는 일정 전체를 조회함
                            System.out.printf("%d.%s\n", i+1, schedule.get(i));
                        }
                    } else {
                        // 해당 날짜에 일정이 없다면
                        System.out.println("해당 날짜에 일정이 존재하지 않습니다.");
                        continue;
                    }

                    while(true) {
                        System.out.println("일정 수정 및 삭제를 하시겠습니까? ( a : 수정 / b : 삭제 / c : 취소 )");
                        System.out.print(">> ");
                        String changePick = sc.nextLine();

                        if(changePick.equalsIgnoreCase("a")) {
                            // 일정수정
                            System.out.println("현재 등록된 일정 리스트 입니다.");
                            for(int i=0; i<schedule.size(); i++) {
                                System.out.printf("%d.%s\n", i+1, schedule.get(i));
                            }
                            System.out.println("변경할 일정의 번호를 입력하세요. (숫자만 입력 가능합니다.)");
                            System.out.print(">> ");
                            int numChange = sc.nextInt();
                            sc.nextLine();

                            if(numChange-1 >= schedule.size()) {
                                // 존재하지 않는 경우
                                System.out.println("잘못 입력하셨습니다. 일정을 다시 선택해주세요.");
                                continue;
                            } else {
                                System.out.println("일정 내용을 변경해주세요.");
                                System.out.print(">> ");
                                String changeList = sc.nextLine();

                                schedule.set(numChange-1, changeList);
                                System.out.println("정상적으로 일정이 변경되었습니다.");
                                continue;
                            }
                        } else if(changePick.equalsIgnoreCase("b")) {
                            System.out.println("현재 등록된 일정 리스트 입니다.");
                            for(int i=0; i< schedule.size(); i++) {
                                System.out.printf("%d.%s\n", i+1, schedule.get(i));
                            }
                            System.out.println("삭제할 일정의 번호를 입력하세요. (숫자만 입력 가능합니다.");
                            System.out.print(">> ");
                            int numDelete = sc.nextInt();
                            sc.nextLine();

                            if(numDelete-1 >= schedule.size()) {
                                System.out.println("잘못 입력하셨습니다. 일정을 다시 선택해주세요.");
                                continue;
                            } else {
                                System.out.println("일정을 정말 삭제하시겠습니까? (y : 삭제 / n : 취소)");
                                System.out.print(">> ");
                                String deleteList = sc.nextLine();
                                if(deleteList.equalsIgnoreCase("y")) {
                                    schedule.remove(numDelete-1);
                                    continue;
                                } else {
                                    System.out.println("일정 삭제를 취소합니다.");
                                }
                            }

                        } else if(changePick.equalsIgnoreCase("c")) {
                            // 일정 변경 및 삭제를 하지 않는다.
                            break;
                        }
                    }
                    break;

                case '3':
                    System.out.println("이동할 년도를 입력하세요: ");
                    int calYear = sc.nextInt();
                    System.out.println("이동할 월을 입력하세요: ");
                    int calMonth = sc.nextInt();
                    System.out.println("");
                    calender(listMap, calYear, calMonth);
                    System.out.println("");
                    break;

                case 'h':
                    System.out.println("==============================");
                    System.out.println("      일정관리 프로그램 도움말 ");
                    System.out.println("==============================");
                    System.out.println("          1. 일정등록");
                    System.out.println(">> 일정을 등록할 날짜와 일정 내용을 입력하면 해당 날짜에 저장됩니다.");
                    System.out.println("          2. 일정 조회 & 수정 & 삭제");
                    System.out.println(">> 등록된 일정을 보여줍니다. 원하는 일정을 수정하거나 삭제할 수 있습니다.");
                    System.out.println("          3. 달력이동");
                    System.out.println(">> 원하는 년도의 달력을 확인할 수 있습니다.");
                    System.out.println("          h. 도움말");
                    System.out.println(">> 옵션별 기능에 대한 설명을 작성해두었습니다.");
                    System.out.println("          q. 프로그램 종료");
                    System.out.println(">> 프로그램을 종료합니다.");
                    System.out.println("");
                    break;

                case 'q':
                    System.out.println("프로그램을 종료합니다. 감사합니다.");
                    System.exit(0);

                default:
                    System.out.println("잘못된 명령입니다. 다시 선택해주세요.");

            }
        }

    }


}
