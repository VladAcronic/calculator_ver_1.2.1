import java.util.*;

public class Converter {
    /*
    класс для конвертированияЖ
    -введенных римских чисел в арабские;
    -арабских чисел в римские для вывода результата в соответствующей системе исчисления
     */

    public static Integer Roman_to_int(String token) {
        /*
        конвертирует римские числа в диапазоне от 1 до 10 (I - X) на основании данных введенных в структуру TreeMap
        при вводе пользователем числа содержащие разрешенные к использованию римские символы, и не отсутствующем
        в структуре возвращается null и вызывается NullPointerException
         */

        TreeMap<String, Integer> roman_data = new TreeMap<>();
        roman_data.put("I", 1);
        roman_data.put("II", 2);
        roman_data.put("III", 3);
        roman_data.put("IV", 4);
        roman_data.put("V", 5);
        roman_data.put("VI", 6);
        roman_data.put("VII", 7);
        roman_data.put("VIII", 8);
        roman_data.put("IX", 9);
        roman_data.put("X", 10);

        Integer number_temp = roman_data.get(token);
        if (number_temp == null) {
            String ex_message = "ERROR MESSAGE: can't found input number \"" + token +
               "\" in permitted romans number set";
            throw new NullPointerException(ex_message);
        }
        return number_temp;
    }

    public static String Int_to_roman(int number) {
        /*
        конвертирует целые числа в римские
         */
        if (number > 100 || number <= 0)
            return null;
        StringBuilder result = new StringBuilder();
        for(Integer key : units.descendingKeySet()) {
            while (number >= key) {
                number -= key;
                result.append(units.get(key));
            }
        }
        return result.toString();
    }

    private static final NavigableMap<Integer, String> units;
    static {
        /*
        хранилище базовых арабских чисел для конвертера
         */
        NavigableMap<Integer, String> initMap = new TreeMap<>();
        initMap.put(100, "C");
        initMap.put(90, "XC");
        initMap.put(50, "L");
        initMap.put(40, "XL");
        initMap.put(10, "X");
        initMap.put(9, "IX");
        initMap.put(5, "V");
        initMap.put(4, "IV");
        initMap.put(1, "I");
        units = Collections.unmodifiableNavigableMap(initMap);
    }
}

