import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Sked {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
                Scanner sc = new Scanner(System.in);
        HashMap<String, ArrayList<String>> listMap = new HashMap<String, ArraryList<String>>();

        // 오늘 날짜
        String todayInput = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 오늘 날짜를 년,월,일로 -를 사용해 나누기
        String[] today = todayInput.split("-");
        calendar(listMap, Integer.parseInt(today[0])), parseInt(today[1]));




    }
}
