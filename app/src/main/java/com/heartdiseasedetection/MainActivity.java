package com.heartdiseasedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.heartdiseasedetection.application.Data;
import com.heartdiseasedetection.application.HeartAPI;
import com.heartdiseasedetection.application.HeartData;
import com.heartdiseasedetection.application.RetrofitClient;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView result;
    Spinner genderSelection,cpSelection,restecgSelection,exangSelection;
    int gender,cp,restecg,exang;
    EditText name,age,chol,trestbps,fbs,max_heart_rate,oldpeak,ca,thal,slope;
    MaterialCardView nextBtn, submitBtn;

    RelativeLayout rl1,rl2,rl3;
    String nameText,ageText,cholText,trestbpsText,fbsText,max_heart_rateText,oldpeakText,caText,thalText,slopeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl1 = findViewById(R.id.rl1);
        rl2 = findViewById(R.id.rl2);
        rl3 = findViewById(R.id.rl3);
        chol = findViewById(R.id.chol);
        trestbps = findViewById(R.id.trestbps);
        fbs = findViewById(R.id.fbs);
        max_heart_rate = findViewById(R.id.max_heart_rate);
        oldpeak = findViewById(R.id.old_peak);
        ca = findViewById(R.id.ca);
        thal = findViewById(R.id.thal);
        slope = findViewById(R.id.slope);
        nextBtn = findViewById(R.id.nextBtn);
        submitBtn = findViewById(R.id.submitBtn);
        result = findViewById(R.id.heading);
        genderSelection = findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSelection.setAdapter(adapter);
        genderSelection.setOnItemSelectedListener(this);
        cpSelection = findViewById(R.id.cp);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.cp, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cpSelection.setAdapter(adapter2);
        cpSelection.setOnItemSelectedListener(this);
        restecgSelection = findViewById(R.id.restecg);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.ecg, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restecgSelection.setAdapter(adapter3);
        restecgSelection.setOnItemSelectedListener(this);
        exangSelection = findViewById(R.id.exang);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.exang, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exangSelection.setAdapter(adapter4);
        exangSelection.setOnItemSelectedListener(this);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        nextBtn.setOnClickListener(v -> {
            if (rl1.getVisibility() == View.VISIBLE){
                if (name.getText().toString().isEmpty()||age.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "We need all these values to proceed.", Toast.LENGTH_SHORT).show();
                }else if (gender == 0){
                    Toast.makeText(MainActivity.this,"Please select gender",Toast.LENGTH_SHORT).show();
                }else {
                    nameText = name.getText().toString().trim();
                    ageText = age.getText().toString().trim();
                    gender = genderSelection.getSelectedItemPosition();
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);
                }
            }else if (rl2.getVisibility() == View.VISIBLE){
                if (chol.getText().toString().isEmpty()||trestbps.getText().toString().isEmpty()||fbs.getText().toString().isEmpty()||max_heart_rate.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "We need all these values to proceed.", Toast.LENGTH_SHORT).show();
                }else if (cp == 0){
                    Toast.makeText(MainActivity.this,"Please select chest pain type",Toast.LENGTH_SHORT).show();
                }else {
                    cp = cpSelection.getSelectedItemPosition();
                    cholText = chol.getText().toString().trim();
                    trestbpsText = trestbps.getText().toString().trim();
                    fbsText = fbs.getText().toString().trim();
                    max_heart_rateText = max_heart_rate.getText().toString().trim();
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    nextBtn.setVisibility(View.GONE);
                    submitBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        submitBtn.setOnClickListener(v -> {
            if (oldpeak.getText().toString().isEmpty()||ca.getText().toString().isEmpty()||thal.getText().toString().isEmpty()||slope.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "We need all these values to proceed.", Toast.LENGTH_SHORT).show();
            }else if (restecg == 0){
                Toast.makeText(MainActivity.this,"Please select resting ecg",Toast.LENGTH_SHORT).show();
            }else if (exang == 0){
                Toast.makeText(MainActivity.this,"Please select exercise induced angina",Toast.LENGTH_SHORT).show();
            }else {
                restecg = restecgSelection.getSelectedItemPosition();
                exang = exangSelection.getSelectedItemPosition();
                oldpeakText = oldpeak.getText().toString().trim();
                caText = ca.getText().toString().trim();
                thalText = thal.getText().toString().trim();
                slopeText = slope.getText().toString().trim();
                Intent resultIntent = new Intent(MainActivity.this,ResultActivity.class);
                resultIntent.putExtra("name",nameText);
                resultIntent.putExtra("age",ageText);
                resultIntent.putExtra("gender",gender-1+"");
                resultIntent.putExtra("cp",cp-1+"");
                resultIntent.putExtra("chol",cholText);
                resultIntent.putExtra("trestbps",trestbpsText);
                resultIntent.putExtra("fbs",fbsText);
                resultIntent.putExtra("restecg",restecg-1+"");
                resultIntent.putExtra("max_heart_rate",max_heart_rateText+"");
                resultIntent.putExtra("exang",exang-1+"");
                resultIntent.putExtra("oldpeak",oldpeakText);
                resultIntent.putExtra("ca",caText);
                resultIntent.putExtra("thal",thalText);
                resultIntent.putExtra("slope",slopeText);
                startActivity(resultIntent);
                finish();
            }
        });
    }

    public void send(View view) {
        HeartAPI heartAPI = RetrofitClient.getRetrofitInstance().create(HeartAPI.class);
        Call<Data> call = heartAPI.predict("65", "1", "0", "135", "254", "0", "0", "127", "0", "2.8", "1", "1", "3");
        call.enqueue(new retrofit2.Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                //  result.setText(response.body());
                //   Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onResponse: " + response.code());
                Log.e("TAG", "onResponse: " + response.body().getHeart_disease());
                if (response.body().getHeart_disease().equals("[0]")){
                    result.setText("No Heart Disease");
                }

                else{
                    result.setText("Heart Disease");
                }
                //Toast.makeText(MainActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // gender = position;
        switch(parent.getId()){
            case R.id.gender :
                gender = position;
                break;
            case R.id.cp :
                cp = position;
                break;
            case R.id.restecg :
                restecg = position;
                break;
            case R.id.exang :
                exang = position;
                break;
        }
        //Toast.makeText(MainActivity.this,"Gender "+gender+"\nCP "+cp+"\nECG "+restecg+"\nExang "+exang,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}