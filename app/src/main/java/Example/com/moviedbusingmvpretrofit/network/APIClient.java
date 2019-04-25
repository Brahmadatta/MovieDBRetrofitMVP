package Example.com.moviedbusingmvpretrofit.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit retrofit = null;

    public static final String API_KEY = "b7ae4931443ae44ae879c87b191bb8e5";

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200/";

    public static final String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780/";


    public static Retrofit getClient(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
