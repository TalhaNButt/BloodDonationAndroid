package com.example.sikandar.blooddonation;

import com.example.sikandar.blooddonation.Notification.MyResponse;
import com.example.sikandar.blooddonation.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAuQsZyLM:APA91bHSGQM2CbROW7VtdrW8YlA-LJi26VAiHiUmbM_UEUkqe2Nq12VaA-5cJY2MtuXHU4_TZluB_7HChAo2plfIA_db-dLMsLG3iYP2h43Z_YNUuXFjrK9CZ5Bj9FBTu0cHO5wI4Nrt"

            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}