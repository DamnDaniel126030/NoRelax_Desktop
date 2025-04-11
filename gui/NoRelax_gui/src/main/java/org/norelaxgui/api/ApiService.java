package org.norelaxgui.api;

import org.norelaxgui.api.model.Order;
import org.norelaxgui.api.model.Product;
import org.norelaxgui.api.model.Reservation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface ApiService {
  @GET("foodDrinkProducts")
  Call<List<Product>> getProducts();

  @GET("reservation")
  Call<List<Reservation>> getReservations(@Header("Authorization") String authToken);

  @GET("orders")
  Call<List<Order>> getOrders(@Header("Authorization") String authToken);
}
