public class Calculator {
/*
класс для математического вычисления результата
 */
    int result;

    public void get_result (Tokens tokens_usr){
        /*
        вычисляет результат на основании переданных проверенных полей класса Tokens
         */

        try {
            this.result = tokens_usr.operand_token.operation(tokens_usr.left_number, tokens_usr.right_number);
        }
        catch (ArithmeticException ex){
            /*
            перехватывает ошибки в тч деление на нуль при условии изменения разрешенных лимитов
            на текущей версии ноль в токене будет перехвачен при проверке в методе analyze_tokens класса Parser
             */
            ex.getMessage();
            System.exit(0);
        }
    }
}
