package com.rideshare.api;



import com.rideshare.models.CarDetailsPojo;
import com.rideshare.models.EditProfilePojo;
import com.rideshare.models.InboxPojo;
import com.rideshare.models.MyPostRidesPojo;
import com.rideshare.models.MyRideHistoryPojo;
import com.rideshare.models.ResponseData;
import com.rideshare.models.ReviewsPojo;
import com.rideshare.models.RidesListPojo;
import com.rideshare.models.SearchDetailsPojo;
import com.rideshare.models.ToppersPojo;
import com.rideshare.models.msgs;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {


    @Multipart
    @POST("rideshare/register.php")
    Call<ResponseData> userRegistration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );

    @Multipart
    @POST("rideshare/addcar.php")
    Call<ResponseData> addcar(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );

    @GET("rideshare/getmycars.php?")
    Call<List<CarDetailsPojo>> getmycars(@Query("email") String email);


    @GET("rideshare/myrideshistory.php?")
    Call<List<MyRideHistoryPojo>> myrideshistory(@Query("email") String email);

    @GET("rideshare/driverRideHistory.php?")
    Call<List<MyRideHistoryPojo>> driverRideHistory(@Query("email") String email);

    @GET("rideshare/getmypostrides.php?")
    Call<List<MyPostRidesPojo>> getmypostrides(@Query("email") String email);


    @GET("/rideshare/login.php?")
    Call<ResponseData> userLogin(
            @Query("email") String email,
            @Query("password") String password);

    @GET("/rideshare/myprofile.php?")
    Call<List<EditProfilePojo>> editProfile(
            @Query("email") String email);

    @GET("rideshare/updateprofile.php")
    Call<ResponseData> updateProfile(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("gender") String gender,
            @Query("password") String password);


    @GET("rideshare/postride.php")
    Call<ResponseData> postaride(
            @Query("slat") String slat,
            @Query("slog") String slog,
            @Query("dlat") String dlat,
            @Query("dlog") String dlog,
            @Query("source") String source,
            @Query("destination") String destination,
            @Query("cid") String cid,
            @Query("seats") String seats,
            @Query("dat") String dat,
            @Query("tim") String tim,
            @Query("amount") String amount,
            @Query("email") String email);

    @GET("rideshare/editcar.php")
    Call<ResponseData> updateCarDetails(
            @Query("cname") String cname,
            @Query("email") String email,
            @Query("seats") String seats,
            @Query("color") String color,
            @Query("cplate") String cplate);

    @GET("rideshare/editmypostride.php")
    Call<ResponseData> editmypostride(
            @Query("source") String source,
            @Query("destination") String destination,
            @Query("seats") String seats,
            @Query("dat") String dat,
            @Query("amount") String amount,
            @Query("tim") String tim,
            @Query("rid") String rid
    );


    @GET("rideshare/book.php")
    Call<ResponseData> bookaRide(
            @Query("rid") String rid,
            @Query("frmuser") String frmuser,
            @Query("touser") String touser,
            @Query("msg") String msg,
            @Query("status") String status);


    @GET("rideshare/addRating.php")
    Call<ResponseData> addRating(
            @Query("rid") String rid,
            @Query("demail") String demail,
            @Query("name") String name,
            @Query("email") String email,
            @Query("msg") String msg,
            @Query("rating") String rating);



    @GET("rideshare/forgotPassword.php")
    Call<ResponseData> forgotPassword(@Query("emailid") String emailid);

    @GET("rideshare/deletecar.php")
    Call<ResponseData> deleteCar(@Query("cid") String cid);


    @GET("rideshare/getride.php")
    Call<List<RidesListPojo>> getAvailableRides(@Query("email") String email);

    @GET("rideshare/getdriver.php")
    Call<List<EditProfilePojo>> getdriver(@Query("email") String email);


    @GET("rideshare/getmyrequests.php")
    Call<List<InboxPojo>> getmyrequests(@Query("email") String email);

    @GET("rideshare/getDriverInboxRiders.php")
    Call<List<InboxPojo>> getDriverInboxRiders(@Query("email") String email);

    @GET("rideshare/getUserInboxRider.php")
    Call<List<InboxPojo>> getUserInboxRider(@Query("email") String email);

    @GET("rideshare/deletemypostride.php")
    Call<ResponseData> deletemypostride(
            @Query("id") String id);

    @GET("rideshare/updaterequest.php")
    Call<ResponseData> updaterequest(
            @Query("bid") String bid,
            @Query("status") String status);





    @GET("rideshare/search.php?")
    Call<List<SearchDetailsPojo>> searchrides(
            @Query("email") String email,
            @Query("source") String source,
            @Query("destination") String destination,
            @Query("dat") String dat);

    @GET("/rideshare/chat.php")
    Call<ResponseData> msglist(@Query("frm") String frm,
                               @Query("eto") String eto,
                               @Query("rid") String rid,
                               @Query("message") String message);

    @GET("/rideshare/getchat.php")
    Call<List<msgs>> getchat(@Query("frm") String from,
                             @Query("eto") String to,
                             @Query("rid") String rid);


    @GET("/rideshare/studentrequests.php?")
    Call<List<msgs>> studentchats(@Query("email") String email);

    @GET("/rideshare/driverrequests.php?")
    Call<List<msgs>> myrequests(@Query("email") String email);


    @GET("/rideshare/topriders.php?")
    Call<List<ToppersPojo>> topriders();

    @GET("/rideshare/getreviews.php?")
    Call<List<ReviewsPojo>> getreviews(
            @Query("email") String email
    );



}
