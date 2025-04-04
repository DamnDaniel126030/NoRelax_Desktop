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

  /*@Headers({
      "Content-Type: application/json",
      "Accept: application/json",
      "Authorization: Bearer " +
  })*/
  @GET("auth/reservation")
  Call<List<Reservation>> getReservations(@Header("Authorization")String token);

  @GET("orders")
  Call<List<Order>> getOrders();
}
