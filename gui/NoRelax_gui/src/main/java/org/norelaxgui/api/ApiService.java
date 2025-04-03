package org.norelaxgui.api;

import org.norelaxgui.api.model.Order;
import org.norelaxgui.api.model.Product;
import org.norelaxgui.api.model.Reservation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
  @POST("foodDrinkProducts")
  Call<List<Product>> getProducts(@Body String groupName);

  @GET("reservation")
  Call<List<Reservation>> getReservations();

  @GET("orders")
  Call<List<Order>> getOrders();
}
