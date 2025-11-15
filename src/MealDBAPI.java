import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MealDBAPI {

    // Fungsi untuk mengambil data JSON dari API
    private static String getJsonResponse(String apiUrl) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    // Mendapatkan daftar Categories
    public static void getCategories() {
        String categoryApiUrl = "https://www.themealdb.com/api/json/v1/1/list.php?c=list";
        String categoryJson = getJsonResponse(categoryApiUrl);
        System.out.println("Categories: " + categoryJson);
    }

    // Mendapatkan daftar Area
    public static void getArea() {
        String areaApiUrl = "https://www.themealdb.com/api/json/v1/1/list.php?a=list";
        String areaJson = getJsonResponse(areaApiUrl);
        System.out.println("Areas: " + areaJson);
    }

    // Mendapatkan daftar Ingredients
    public static void getIngredients() {
        String ingredientApiUrl = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
        String ingredientJson = getJsonResponse(ingredientApiUrl);
        System.out.println("Ingredients: " + ingredientJson);
    }

    public static void main(String[] args) {
        // Ambil dan tampilkan Categories, Areas, dan Ingredients
        getCategories();
        getArea();
        getIngredients();
    }
}
