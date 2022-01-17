import java.util.TreeSet;

public class DATA_STORAGE {
    /*
    класс для хранения констант с разрешенным диапазонами используемых чисел,
    испульзуемыми математическими операциями,
    допустимыми арабскими и римскими цифрами
     */
}

class NUMBERS_PERMISSIONS {
    /*
    класс используется для для хранения разрешенных нижнего и верхнего пределов значений чисел
     */

    final int UPPER_VALUE_OF_NUMBERS = 10;//верхний предел чисел
    final int LOW_VALUE_OF_NUMBERS = 1;//нижний предел чисел
}

enum Math_operation {
    /*
    перечисление математических операторов, их строкового представления и их функциональное представление
     */

    SUM("+"){
        public int operation (int left_token_usr, int right_token_usr){
            return (left_token_usr + right_token_usr);
        }
    },
    SUBTRACT ("-"){
        public int operation (int left_token_usr, int right_token_usr){
            return (left_token_usr - right_token_usr);
        }
    },
    MULTIPLY ("*"){
        public int operation (int left_token_usr, int right_token_usr){
            return (left_token_usr * right_token_usr);
        }
    },
    DIVIDE("/"){
        public int operation (int left_token_usr, int right_token_usr){
            return (left_token_usr / right_token_usr);
        }
    };

    public abstract int operation (int left_token_usr, int right_token_usr);

    private String operation;
    Math_operation (String operation){this.operation = operation;}
    public String get_operation(){return this.operation;}

    public String get_escape_operation() {
        /*
        преобразует строковое представление Math_operation для операций сложения и умножения в "\\+" и "\\*",
        соответственно (экранирование для использование в функции Parser.string_splitter())
         */

        if ((operation == SUM.operation) | (operation == MULTIPLY.operation)) {
            return String.join("", "\\", this.operation);
        }
        else {return this.operation;}
    }
}

enum Arabic_Numbers {
    /*
    перечисление допустимых арабских цифр и их char представление
     */
    _1('1'),
    _2('2'),
    _3('3'),
    _4('4'),
    _5('5'),
    _6('6'),
    _7('7'),
    _8('8'),
    _9('9'),
    _0('0');

    //делаем конструктор
    private Character ArabicNumber_char;
    Arabic_Numbers(char Arabic_Number_char) {this.ArabicNumber_char = Arabic_Number_char;}
    public Character getArabicNumber_char(){return this.ArabicNumber_char;}

    public static TreeSet<Character> get_TreeSet_Arabic_numbers(){
        /*
        возвращает представление арабских чисел в виде сета для быстрой проверки на вхождение
         */

        TreeSet<Character> arabic_number_temp = new TreeSet<Character>();
        for (Arabic_Numbers an: Arabic_Numbers.values()){
            arabic_number_temp.add(an.getArabicNumber_char());
        }
        return arabic_number_temp;
    }
}

enum Roman_Numbers {
    /*
    перечисление допустимых рирмских символов и их char представление
     */

    _1('I'),
    _5('V'),
    _10('X');

    private Character Roman_Number_char;
    Roman_Numbers(char Roman_Number_char) {this.Roman_Number_char = Roman_Number_char;}
    public Character getRoman_Number_char(){return this.Roman_Number_char;}

    public static TreeSet <Character> get_TreeSet_roman_numbers(){
        /*
        вохвращает представление допустимых римских символов в виде сета для быстрой проверки на вхождение
         */
        TreeSet<Character> roman_number_temp = new TreeSet<Character>();
        for (Roman_Numbers rn: Roman_Numbers.values()){
            roman_number_temp.add(rn.getRoman_Number_char());
        }
        return roman_number_temp;
    }
}
