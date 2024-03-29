package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import kr.ac.jbnu.se.mm2019Group1.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private EditText etNickName;
    private FirebaseAuth firebaseAuth;
    private Button btFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = LoginActivity.firebaseAuth;


        etEmail = findViewById(R.id.emailTextRg);
        etPassword = findViewById(R.id.passwordTextRg);
        etNickName = findViewById(R.id.nicknameTextRg);
        btFinish = findViewById(R.id.registerButton);

        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(etEmail.getText().toString(), etPassword.getText().toString());

            }
        });


    }

    public void setDocument() {
        FirebaseUser firebaseUseruser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userUID = firebaseUseruser.getUid();
        Map<String, Object> user = new HashMap<>();

        user.put("NickName", etNickName.getText().toString());

        db.collection("User").document(userUID)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("login","실패"+e);
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    private void createAccount(String email, String password) {

        // [START create_user_with_email]
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            setDocument();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

}
