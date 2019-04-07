import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Currency {
    public static String getCurrency(ModelCurrency model) throws IOException {
        URL url = new URL("http://www.nbrb.by/API/ExRates/Rates?Periodicity=0");

        Scanner input = new Scanner((InputStream) url.getContent());
        String result = "";
        String currency_last = "";
        while (input.hasNext()) {
            result += input.nextLine();
        }

        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            model.setCur_Abbreviation((String) obj.get("Cur_Abbreviation"));
            model.setCur_Name((String) obj.get("Cur_Name"));
            model.setCur_Scale(obj.getInt("Cur_Scale"));
            model.setCur_OfficialRate((Double) obj.get("Cur_OfficialRate"));
            currency_last += model.getCur_Scale() + " " + model.getCur_Abbreviation() + " ( " + model.getCur_Name()
                    + " ) " + " = " + model.getCur_OfficialRate() + " BYN\n";
        }

        return currency_last;
    }

    public static String getCurrency() throws IOException {
        ModelCurrency model = new ModelCurrency();
        URL url = new URL("http://www.nbrb.by/API/ExRates/Rates?Periodicity=0");

        Scanner input = new Scanner((InputStream) url.getContent());
        String result = "";
        String currency_last = "";
        while (input.hasNext()) {
            result += input.nextLine();
        }

        int[] arr = {2, 4, 5, 6};
        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0; i < 4; i++){
            JSONObject obj = jsonArray.getJSONObject(arr[i]);
            model.setCur_Abbreviation((String) obj.get("Cur_Abbreviation"));
            model.setCur_Name((String) obj.get("Cur_Name"));
            model.setCur_Scale(obj.getInt("Cur_Scale"));
            model.setCur_OfficialRate((Double) obj.get("Cur_OfficialRate"));
            currency_last += model.getCur_Scale() + " " + model.getCur_Abbreviation() + " ( " + model.getCur_Name()
                    + " ) " + " = " + model.getCur_OfficialRate() + " BYN\n";
        }

        return currency_last;
    }
}
