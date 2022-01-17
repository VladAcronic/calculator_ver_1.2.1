import java.util.Scanner;

public class User_input_data {
    /*
    Класс отвечает за прием данных от пользователя и выдачу их в виде строки классу Parser
     */

    public static String Get_user_data () {
        /*
        Ф принимает данные в консоли в строковом виде, обрезает краевые пробелы и возвращает строку
         */
        System.out.print("Input: ");
        Scanner input_data = new Scanner(System.in);
        String input_user_data_string = input_data.nextLine().trim();
        input_data.close();
        return input_user_data_string;
    }

    public static String TEST_Get_user_data() {
        /*
        Ф для тестирования симуляцией ввода данных пользователем
         */
        //ПЕРЕМЕННАЯ ДЛЯ ВВОДА ТЕСТОВОЙ СТРОКИ
        final String TEST_USER_DATA_STR = " II + X ";

        String test_user_data_string_01 = TEST_USER_DATA_STR.trim();
        System.out.println("tested calculated string:");
        System.out.println(test_user_data_string_01);
        return test_user_data_string_01;
    }
}
