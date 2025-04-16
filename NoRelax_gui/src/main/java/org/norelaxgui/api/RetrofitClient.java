package org.norelaxgui.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
  private static final String BASE_URL = "http://localhost:3000/";
  private static Retrofit retrofit;

  public static Retrofit getClient() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit;
  }
}
