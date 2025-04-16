package org.norelaxgui.api;

import org.norelaxgui.api.model.Order;
import org.norelaxgui.api.model.OrderItem;
import org.norelaxgui.api.model.Product;
import org.norelaxgui.api.model.Reservation;
import org.norelaxgui.api.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {
  @GET("foodDrinkProducts")
  Call<List<Product>> getProducts();

  @GET("user/{id}")
  Call<User> getUserById(@Header("Authorization") String authToken, @Path("id") String id);

  @GET("reservation")
  Call<List<Reservation>> getReservations(@Header("Authorization") String authToken);

  @GET("reservation/{id}")
  Call<List<Reservation>> getReservationById(@Header("Authorization") String authToken, @Path("id") String id);

  @GET("orders")
  Call<List<Order>> getOrders(@Header("Authorization") String authToken);

  @GET("order-item/{id}")
  Call<List<OrderItem>> getOrderItems(@Header("Authorization") String authToken, @Path("id") String id);

  @PATCH("foodDrinkProducts/{id}")
  Call<Void> updateProduct(@Header("Authorization") String authToken, @Path("id") String id, @Body Product product);

  @DELETE("foodDrinkProducts/{id}")
  Call<Void> deleteProduct(@Header("Authorization") String authToken, @Path("id") String id);

  @PATCH("orders/{id}")
  Call<Void> updateOrder(@Header("Authorization") String authToken, @Path("id") String id, @Body Order order);
}
