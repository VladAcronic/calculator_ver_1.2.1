public class Main {

    public static void main(String[] args) throws Exception {

        //для ввода сроки из функции TEST_Get_user_data:
        //String input_data_string = User_input_data.TEST_Get_user_data();

        String input_data_string = User_input_data.Get_user_data ();
        Tokens user_tokens = Parser.Pars_user_string(input_data_string);
        new Result_getter(user_tokens, true);

    }
}

