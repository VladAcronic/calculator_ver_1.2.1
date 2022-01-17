import java.util.TreeSet;

public class Parser {
    /*
    класс Parser парсит принятую от пользователя строку на числа и математический оператор и
    возвращает структуру данных типа Tokens для последующих математических вычислений в классе Calculator
     */

    public static Tokens Pars_user_string(String input_data_string_usr) throws Exception {
        /*
        ф парсит принятую от пользователя строку на числа и математический оператор и возвращает
        структуру данных типа Tokens
         */

        //принимаем предобработанную для парсинга строку
        //TODO после отладки заменить принимаемую строку от функции TEST_Get_user_data на строку от
        // функции Get_user_data

        //String input_data_string = User_input_data.TEST_Get_user_data();
        //String input_data_string = User_input_data.Get_user_data ();

        String input_data_string = input_data_string_usr;

        Tokens user_tokens = new Tokens();

        //делим строку по матоператору
        try {
            user_tokens.Split_string_by_math_operand (input_data_string);
        }
        catch (Exception ex){}

        //анализируем строковые токены на содержание
        try {
            user_tokens.analyze_tokens();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }

        //парсим найденные строковые токены в целые числа
        try {
            user_tokens.parse_tokens();
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return user_tokens;
    }
}

class Tokens {
    String left_expression_token;
    String right_expression_token;
    Math_operation operand_token = null;
    char token_type = 'n';
    /*
    'n' - nonrecognized token type;
    'a' - arabic token type;
    'r' - roman token type;
     */
    int left_number;
    int right_number;

    //****************************************************************************************************

    public void Split_string_by_math_operand (String data_string_usr ) throws Exception {
        /*
        принимает объект StringBuffer и ищет в нем математический оператор указанный в enum Math_operation
        класса DATA_STORAGE до его первого вхождения в стоку и записывает его в поле Math_operation operand_token.
        после нахождения матоператора выполнение функции прерывается выбросом исключения
         */

        //проверяем посимвольно совпадение в буфере строки с символом операнда
        //при нахождении матоператора, записываем его в поле this.operand_token и
        // выбросом исключения выходим из циклов

        StringBuffer data_StringBuffer_usr_temp = new StringBuffer(data_string_usr);
        String[] tokens_array;

        for (int i = 0; i < data_StringBuffer_usr_temp.length(); i++) {
            for (Math_operation operand: Math_operation.values()){
                if  (data_StringBuffer_usr_temp.charAt(i) == operand.get_operation().charAt(0)){
                    this.operand_token = operand;
                    //режем строку по найденному оператору и вытягиваем левый токен
                    tokens_array = data_string_usr.split (operand.get_escape_operation());
                    this.left_expression_token = tokens_array[0].trim();
                    this.right_expression_token = tokens_array[1].trim();

                    //проверяем левый токен на нулевую длину и найденный оператором
                    //если левый токе пустой и операнд - знак "-", возможно мы нашли отрицательное число,
                    //и, тогда, прерываем внутренний цикл продолжаем искать матоператор в строке
                    if ((this.left_expression_token.length() == 0) & (operand.get_operation() == "-")){
                        break;
                    }
                    // во всех остальных случаях найденного матоператора, выбрасываем исключение для
                    // выхода из всех циклов
                    else {
                        throw new Exception();
                    }
                }
            }
        }
    }

    /*
    public void String_splitter (String input_data_string_usr){

        TODO метод становится неиспользуемым
        расделяет исходную строку по найденному матоператору, в подстроках удаляет краевые пробелы,
        создает из них объекты StringBuffer и записывает их в соответствующие поля класса Tokens


        String[] tokens_array = input_data_string_usr.split (this.operand_token.get_escape_operation());
        this.left_expression_token = tokens_array[0].trim();
        this.right_expression_token = tokens_array[1].trim();
    }
    */

    private char String_token_analyzer(StringBuffer token_StringBuffer_usr,
                                       TreeSet <Character> arabic_numbers_TreeSet_usr,
                                       TreeSet <Character> roman_numbers_TreeSet_usr) throws Exception {
        /*
        метод принимает объект StringBuffer вычисляет тип введенных данных (арабские/римские цифры в токене),
        анализирует его на пригодность для парсинга в числовое представление
         */

        //создаем переменную типа токена
        char token_type_temp;

        //если токен пустой выбрасываем исключение
        if (token_StringBuffer_usr.length() == 0)
            throw new Exception("ERROR MESSAGE: expression before and after math operand cannot be empty");

        //проверяем по первому символу токена тип числа (арабская/римская) и допустимость символа
        char first_tokens_char_temp = token_StringBuffer_usr.charAt(0);

        if (arabic_numbers_TreeSet_usr.contains(first_tokens_char_temp)| first_tokens_char_temp == '-' ){
            token_type_temp = 'a';
        }
        else if(roman_numbers_TreeSet_usr.contains(first_tokens_char_temp)) {
            token_type_temp = 'r';
        }

        else {
            token_type_temp = 'n';
            String ex_message = "ERROR MESSAGE: can't identify type of numbers; \n" +
                    "find invalid symbol \"" + first_tokens_char_temp + "\" in expression";
            throw  new Exception (ex_message);
        }

        //выполняем проверки для арабских символов
        if (token_type_temp == 'a') {

            //проверяем не является ли первый символ нулем, если он не единственный
            if (token_StringBuffer_usr.length() > 1 && first_tokens_char_temp == '0')
                throw new Exception("ERROR MESSAGE: the number cannot start from zero");
            //проверяем наличие иных символов, если первым символом введен знак '-'
            if ((first_tokens_char_temp == '-') & (token_StringBuffer_usr.length() == 1)){
                String ex_message = "ERROR MESSAGE: entered symbols set \""+ token_StringBuffer_usr+ "\" is incorrect";
                throw new Exception(ex_message);
            }
            //проверяем символы токена на допустимые значения
            for (int i = 1; i < token_StringBuffer_usr.length(); i++) {
                if (!arabic_numbers_TreeSet_usr.contains(token_StringBuffer_usr.charAt(i))) {
                    String ex_message = "ERROR MESSAGE: use invalid arabic symbol \"" +
                            token_StringBuffer_usr.charAt(i) +"\" in expression \"" + token_StringBuffer_usr+"\"";
                    throw new Exception(ex_message);
                }
            }
        }
        //выполняем проверки для римских символов
        else if (token_type_temp == 'r') {

            //проверяем символы токена на допустимые значения
            for (int i = 1; i < token_StringBuffer_usr.length(); i++){
                if (!roman_numbers_TreeSet_usr.contains(token_StringBuffer_usr.charAt(i)))
                    throw new Exception("ERROR MESSAGE: use invalid roman symbol "+token_StringBuffer_usr.charAt(i)+
                            "in expression");
            }
        }
        return token_type_temp;
    }

    public void analyze_tokens () throws Exception {

        //создаем TreeSets арабских и римских символов
        TreeSet <Character> arabic_numbers_TreeSet_temp = Arabic_Numbers.get_TreeSet_Arabic_numbers();
        TreeSet <Character> roman_numbers_TreeSet_temp = Roman_Numbers.get_TreeSet_roman_numbers();

        //проверяем заполнились ли поля Tokens при поиске матоператора и разделении строки
        if (this.operand_token == null){
            String ex_message = "ERROR MESSAGE: math operand is not found";
            throw new Exception(ex_message);
        }
        //проверяем левый токен
        StringBuffer token_StringBuffer_temp = new StringBuffer(this.left_expression_token);
        this.token_type = String_token_analyzer(token_StringBuffer_temp, arabic_numbers_TreeSet_temp,
                roman_numbers_TreeSet_temp);
        //проверяем правый токен, включая на совпадение с типом данных левого токена

        token_StringBuffer_temp = new StringBuffer(this.right_expression_token);
        if (String_token_analyzer(token_StringBuffer_temp, arabic_numbers_TreeSet_temp,
                roman_numbers_TreeSet_temp) != this.token_type) throw new
                Exception("ERROR MESSAGE: tokens must have the same type");
    }

    private void check_number_for_permissions (int parsed_token_int_usr) throws Exception {

        NUMBERS_PERMISSIONS np = new NUMBERS_PERMISSIONS();

        if (parsed_token_int_usr < np.LOW_VALUE_OF_NUMBERS || parsed_token_int_usr > np.UPPER_VALUE_OF_NUMBERS ){
            String ex_str_temp = "ERROR MESSAGE: result cannot be calculated, \n" +
                    "the number " + parsed_token_int_usr + " is out of permitted limits";
            throw new Exception (ex_str_temp);
        }
    }

    public void parse_tokens () {
        /*
        парсит проверенные на допустимые символы строковые токены в целые числа, ,
        производя проверку полученных значений на допустимые лимиты
         */

        //для арабских чисел:
        if (this.token_type == 'a') {
            try {
                this.left_number = Integer.parseInt(this.left_expression_token);
                check_number_for_permissions(this.left_number);
                this.right_number = Integer.parseInt(this.right_expression_token);
                check_number_for_permissions(this.right_number);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.exit(0);
            }
        }

        //для римских чисел:
        else if (this.token_type == 'r') {
            try {
                this.left_number = Converter.Roman_to_int(this.left_expression_token);
                this.right_number = Converter.Roman_to_int(this.right_expression_token);
            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
                System.exit(0);
            }
        }
    }

    //****************************************************************************************************
    public void print_tokens(){
        /*
        отладочный метод; печатает найденные строковые токены
         */

        System.out.println("user_tokens:");
        System.out.printf("left_token:"+left_expression_token+"\n");
        System.out.printf("right_token:"+right_expression_token+"\n");
        System.out.printf("operand_token:"+operand_token+"\n");
        System.out.printf("left_number:" + left_number + "\n");
        System.out.printf("right_number:" + right_number + "\n");
        System.out.println("escape operand_token:"+operand_token.get_escape_operation()+"\n");
    }
}
