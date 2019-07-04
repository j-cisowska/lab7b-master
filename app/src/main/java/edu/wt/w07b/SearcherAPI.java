package edu.wt.w07b;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearcherAPI {
    String BASE_URL = "https://fathomless-ravine-81378.herokuapp.com/";

    @GET("movie/search/{title}/")
    Call<List<Question>> getQuestions(@Path("title") String title);
}