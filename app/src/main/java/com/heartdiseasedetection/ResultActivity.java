package com.heartdiseasedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.heartdiseasedetection.application.Data;
import com.heartdiseasedetection.application.HeartAPI;
import com.heartdiseasedetection.application.RetrofitClient;

import retrofit2.Call;

public class ResultActivity extends AppCompatActivity {
    TextView result,finalResult,healthResult;
    String name,age,gender,cp,trestbps,chol,fbs,restecg,thalach,exang,oldpeak,slope,ca,thal;
    int restingBp = 0, cholesterol = 0, blood_sugar = 0, max_heart_rate = 0,health = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = findViewById(R.id.result);
        finalResult = findViewById(R.id.finalResult);
        healthResult = findViewById(R.id.healthResult);
        name = getIntent().getStringExtra("name");
        age = getIntent().getStringExtra("age");
        gender = getIntent().getStringExtra("gender");
        cp = getIntent().getStringExtra("cp");
        trestbps = getIntent().getStringExtra("trestbps");
        chol = getIntent().getStringExtra("chol");
        fbs = getIntent().getStringExtra("fbs");
        restecg = getIntent().getStringExtra("restecg");
        thalach = getIntent().getStringExtra("max_heart_rate");
        exang = getIntent().getStringExtra("exang");
        oldpeak = getIntent().getStringExtra("oldpeak");
        slope = getIntent().getStringExtra("slope");
        ca = getIntent().getStringExtra("ca");
        thal = getIntent().getStringExtra("thal");
        result.setText("Name: "+name+"\nAge: "+age+"\nGender: "+gender+"\nChest Pain Type: "+cp+"\nResting Blood Pressure: "+trestbps+"\nSerum Cholestoral: "+chol+"\nFasting Blood Sugar: "+fbs+"\nResting Electrocardiographic Results: "+restecg+"\nMaximum Heart Rate Achieved: "+thalach+"\nExercise Induced Angina: "+exang+"\nST Depression Induced by Exercise Relative to Rest: "+oldpeak+"\nSlope of the Peak Exercise ST Segment: "+slope+"\nNumber of Major Vessels (0-3) Colored by Flourosopy: "+ca+"\nThal: "+thal);
        checkResult();
        checkParameters();
    }

    private void checkParameters() {
        if ((Integer.parseInt(age)>=19 && Integer.parseInt(age)<=40) && (Integer.parseInt(trestbps)>=95 && Integer.parseInt(trestbps)<=135)){
            restingBp = 0;
        }
        else if ((Integer.parseInt(age)>=41 && Integer.parseInt(age)<=60) && (Integer.parseInt(trestbps)>=110 && Integer.parseInt(trestbps)<=145)){
            restingBp = 0;
        }
        else if ((Integer.parseInt(age)>=61) && (Integer.parseInt(trestbps)>=95 && Integer.parseInt(trestbps)<=145)){
            restingBp = 0;
        }
        else{
            restingBp = 1;
        }
        if (Integer.parseInt(chol)>=125 && Integer.parseInt(chol)<=200){
            cholesterol = 0;
        }
        else{
            cholesterol = 1;
        }
        if (Integer.parseInt(fbs)<=100){
            blood_sugar = 0;
        }
        else{
            blood_sugar = 1;
        }
        if (Integer.parseInt(thalach)<=((220-Integer.parseInt(age))*0.64)){
            max_heart_rate = 0;
        }
        else{
            max_heart_rate = 1;
        }
    }

    private void checkResult(){
        HeartAPI heartAPI = RetrofitClient.getRetrofitInstance().create(HeartAPI.class);
        Call<Data> call = heartAPI.predict(age, gender, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal);
        call.enqueue(new retrofit2.Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                //  result.setText(response.body());
                //   Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onResponse: " + response.code());
                Log.e("TAG", "onResponse: " + response.body().getHeart_disease());
                if (response.body().getHeart_disease().equals("[0]")){
                    finalResult.setText(name+" you are safe");
                    health = 0;
                }

                else{
                    finalResult.setText(name+" you are in danger");
                    health = 1;
                }
                //Toast.makeText(ResultActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                showResults();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }

    private void showResults() {
        if (restingBp == 1){
            healthResult.setText(name+" you have high resting blood pressure");
            printRecommendations("bp");
        }
        else if (cholesterol == 1){
            healthResult.setText(name+" you have high cholesterol");
            printRecommendations("chol");
        }
        else if (blood_sugar == 1){
            healthResult.setText(name+" you have high blood sugar");
            printRecommendations("bs");
        }
        else if (max_heart_rate == 1){
            healthResult.setText(name+" you have high maximum heart rate");
            printRecommendations("mhr");
        }
        else{
            healthResult.setText(name+" you are healthy everything looks fine");
        }
    }

    private void printRecommendations(String s){

    }
}