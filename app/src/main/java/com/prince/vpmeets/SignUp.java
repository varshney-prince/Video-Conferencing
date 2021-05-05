package com.prince.vpmeets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    FirebaseFirestore database;
    FirebaseAuth auth;
    EditText name,email,password;
    Button create,already;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.name);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        create=findViewById(R.id.login);
        database=FirebaseFirestore.getInstance();
        already = findViewById(R.id.signup);
        auth=FirebaseAuth.getInstance();
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,Login.class));
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names,emails,passwords;
                names=name.getText().toString();
                emails=email.getText().toString();
                passwords=password.getText().toString();
                User user=new User();
                user.setUname(names);
                user.setUemail(emails);
                user.setUpassword(passwords);
                auth.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("User").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignUp.this,Login.class));
                                }
                            });
                            Toast.makeText(SignUp.this,"Account is created",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SignUp.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}