package org.norelaxgui.api;

import org.norelaxgui.api.model.Order;
import org.norelaxgui.api.model.Product;
import org.norelaxgui.api.model.Reservation;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
  @GET("products")
  Call<List<Product>> getDrinks();

  @GET("reservations")
  Call<List<Reservation>> getReservations();

  @GET("orders")
  Call<List<Order>> getOrders();
}
