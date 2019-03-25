package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegistrationPage extends AppCompatActivity {
    private Button createAccount;
    private ProgressDialog loadingBar;
    private EditText InputName, InputPhoneNumer, InputPassword, InputMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        createAccount = (Button) findViewById(R.id.registration_button);
        InputName = (EditText) findViewById(R.id.fn);
        InputPhoneNumer = (EditText) findViewById(R.id.Registration_number);
        InputPassword = (EditText) findViewById(R.id.Registration_password);
        InputMail = (EditText) findViewById(R.id.email);
        loadingBar = new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAcc();
            }
        });
    }

    private void CreateAcc() {
        String name = InputName.getText().toString();
        String number = InputPhoneNumer.getText().toString();
        String password = InputPassword.getText().toString();
        String mail = InputMail.getText().toString();

        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Please enter your number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Please enter your mail", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, number, password, mail);
        }
    }

    private void ValidatephoneNumber(final String name, final String number, final String password, final String mail) {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();
        final String orders = "";
        final String cart = "";
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(number).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", number);
                    userdataMap.put("name", name);
                    userdataMap.put("password", password);
                    userdataMap.put("mail", mail);
                    userdataMap.put("cart", cart);
                    userdataMap.put("orders", orders);
                    Rootref.child("Users").child(number).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegistrationPage.this, "Congrats", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(RegistrationPage.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(RegistrationPage.this, "This "+ number + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegistrationPage.this, "This "+ number + " Please try another phone number", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationPage.this, MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
