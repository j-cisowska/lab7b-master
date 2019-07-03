package edu.wt.w07b;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearcherAPI {
    String BASE_URL = "https://fathomless-ravine-81378.herokuapp.com/";

    @GET("movies/{id}/") //???tutaj nie wiem jak dokonczyc sciezke
    Call<List<Question>> getQuestions(@Path("id") String id);
}

//@GET("movies/{id}/") //
//Call<Movie> getMovies(@Path("id") String id);