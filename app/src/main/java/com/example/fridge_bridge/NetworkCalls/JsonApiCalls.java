package com.example.fridge_bridge.NetworkCalls;

import com.example.fridge_bridge.Model.RecipeDetail;
import com.example.fridge_bridge.Model.RecipeList;
import com.example.fridge_bridge.Model.RecipeModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface JsonApiCalls {


    @GET("findByIngredients")
    Call<List<RecipeModel>> getRecipe(@Query("apiKey") String apikey, @Query("ingredients") String ingredient, @Query("number") int number);

    @GET("{recipeID}/information")
    Call<RecipeDetail> getRecipeDetail(@Path(value = "recipeID") int number,@Query("apiKey") String apikey);


 /*   @POST("followedArea/")
    Call<AddAreaModel> addArea(@Header("Authorization") String accessToken, @Body AddAreaModel addAreaModel);

    @DELETE("followedArea/{ID}")
    Call<Void> deleteArea(@Header("Authorization") String accessToken, @Path("ID") String id);
*/


}