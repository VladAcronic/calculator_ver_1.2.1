public class Result_getter {

    String result_str;//переменная для строкового представления результата
    Calculator calculation_result;//результат вычисления метода get_result класса Calculator

    Result_getter (Tokens tokens_usr, boolean print_result_usr) throws NullPointerException {
        /*
        конструктор выводит результат в зависимости от системы исчисления (по полю token_type класса Tokens)
         */

        this.calculation_result = new Calculator();
        this.calculation_result.get_result(tokens_usr);

        //выводим результат для арабских чисел
        if (tokens_usr.token_type == 'a'){
            this.result_str = String.valueOf(this.calculation_result.result);
        }

        //выводим результат для римских чисел
        else {//tokens_usr.token_type == 'r'
            try {
                this.result_str = Converter.Int_to_roman(this.calculation_result.result);
                if (this.result_str == null) {
                    String ex_msg = "ERROR MESSAGE: the result of calculation on roman numbers have to be positive";
                    throw new NullPointerException(ex_msg);
                }
            }
            catch (NullPointerException ex){
                /*
                при получении отрицательного числа в конвертере Converter.intToRoman перехватываем null
                 */
                System.out.println(ex.getMessage());
                System.exit(0);
            }
        }
        if (print_result_usr == true){
            System.out.printf("Output: "+this.result_str);
        }
    }
}
