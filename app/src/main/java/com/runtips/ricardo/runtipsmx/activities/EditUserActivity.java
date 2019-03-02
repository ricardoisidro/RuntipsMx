package com.runtips.ricardo.runtipsmx.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.runtips.ricardo.runtipsmx.R;
import com.runtips.ricardo.runtipsmx.app.RuntipsMXApp;
import com.runtips.ricardo.runtipsmx.models.UserModel;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class EditUserActivity extends AppCompatActivity {

    private Realm realm;

    private String selectedState;
    private String selectedSex;

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextBirth;
    private EditText editTextPhone;
    private EditText editTextMail;
    private EditText editTextPassword;
    private EditText editTextConfirm;

    private Button btnSave;

    private MaterialBetterSpinner materialDesignSpinner;
    private RadioGroup radioGroupSex;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        realm = Realm.getDefaultInstance();

        editTextName = findViewById(R.id.txtEditName);
        editTextName.setEnabled(false);
        editTextSurname = findViewById(R.id.txtEditSurname);
        editTextSurname.setEnabled(false);
        editTextBirth = findViewById(R.id.textEditBirth);
        editTextPhone = findViewById(R.id.txtEditPhone);
        editTextMail = findViewById(R.id.txtEditMail);
        editTextMail.setEnabled(false);
        editTextPassword = findViewById(R.id.txtEditPassword);
        editTextConfirm = findViewById(R.id.txtEditConfirmPassword);

        btnSave = findViewById(R.id.btnEditOK);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
                Intent intent = new Intent(EditUserActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        editTextBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog();
            }
        });

        dateSetListener = (datePicker, year, month, dayOfMonth) -> {

            int realMonth = month + 1;
            String longMonth, longDay;
            if(String.valueOf(realMonth).length() == 1)
                longMonth = "0"+String.valueOf(realMonth);
            else
                longMonth=String.valueOf(realMonth);
            if(String.valueOf(dayOfMonth).length() == 1)
                longDay = "0"+String.valueOf(dayOfMonth);
            else
                longDay = String.valueOf(dayOfMonth);
            String realDate = year+"-"+longMonth+"-"+longDay;
            editTextBirth.setText(realDate);
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.spinnerRegisterStates));
        materialDesignSpinner = findViewById(R.id.spinnerEditState);
        materialDesignSpinner.setAdapter(arrayAdapter);
        materialDesignSpinner.setOnItemClickListener((adapterView, view, pos, l) -> selectedState = adapterView.getItemAtPosition(pos).toString());

        radioGroupSex = findViewById(R.id.radioGroupEditGender);


        readUser();

    }

    private void updateUser() {
        //final UserModel usr = new UserModel();
        final UserModel usr = realm.where(UserModel.class).equalTo("id", 1).findFirst();
        realm.executeTransaction(realm -> {
            if(usr != null) {
                usr.setName(editTextName.getText().toString());
                usr.setSurname(editTextSurname.getText().toString());
                usr.setBirthday(editTextBirth.getText().toString());
                usr.setSex(selectedSex);
                usr.setState(selectedState);
                usr.setMail(editTextMail.getText().toString());
                usr.setPhone(editTextPhone.getText().toString());
                usr.setPassword(editTextPassword.getText().toString());

                usr.setFacebook("000000");
            }

        });
    }

    private void readUser() {

        RealmQuery<UserModel> query = realm.where(UserModel.class);
        query.equalTo("id", 1);
        RealmResults<UserModel> result = query.findAll();
        result.load();

        editTextName.setText(result.get(0).getName());
        editTextSurname.setText(result.get(0).getSurname());
        editTextBirth.setText(result.get(0).getBirthday());
        editTextPhone.setText(result.get(0).getPhone());
        editTextMail.setText(result.get(0).getMail());
        editTextPassword.setText(result.get(0).getPassword());
        editTextConfirm.setText(result.get(0).getPassword());

        selectedState = result.get(0).getState();
        materialDesignSpinner.setText(selectedState);

        selectedSex = result.get(0).getSex();
        switch(selectedSex) {
            case "F":
                radioGroupSex.check(R.id.radioEditWoman);
                break;
            case "M":
                radioGroupSex.check(R.id.radioEditMan);
                break;

        }

    }

    private void openDateDialog(){

        try{
            String date_value = editTextBirth.getText().toString();
            String[] data = date_value.split("-");
            DatePickerDialog dialog = new DatePickerDialog(EditUserActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener,
                    Integer.parseInt(data[0]),
                    Integer.parseInt(data[1])-1,
                    Integer.parseInt(data[2]));
            dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        catch (Exception ex){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(EditUserActivity.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    dateSetListener,
                    year,
                    month,
                    day);
            dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}
