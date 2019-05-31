package kr.ac.jbnu.se.mm2019Group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import kr.ac.jbnu.se.mm2019Group1.R;

public class ProfileEditActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etName;
    private FirebaseUser user;
    private Button btnchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);


        etPassword = (EditText) findViewById(R.id.passwordTextC);
        etName = (EditText) findViewById(R.id.nicknameTextC);
        btnchange = (Button)findViewById(R.id.chButton);

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = LoginActivity.firebaseAuth.getCurrentUser();
//                user.updateEmail(etEmail.getText().toString())
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Log.d("TAG", "User email address updated.");
//                                }
//                                else{
//                                    Log.d("TAG", "User email address not updated.");
//                                }
//                            }
//                        });

                user.updatePassword(etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("TAG", "User password updated.");
                                }
                            }
                        });
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference washingtonRef = db.collection("User").document(user.getUid());

// Set the "isCapital" field of the city 'DC'
                washingtonRef
                        .update("NickName", etName.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error updating document", e);
                            }
                        });

                Intent intent = new Intent(ProfileEditActivity.this,ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });


    }
}
