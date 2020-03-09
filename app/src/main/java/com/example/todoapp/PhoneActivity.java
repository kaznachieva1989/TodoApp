package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    EditText editPhone, editCode;
    Button btn_confirm, btn_startVerification;

    FirebaseAuth mAuth;
    String codeSent;

    Timer timer;
    String phone;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    TextView timerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mAuth = FirebaseAuth.getInstance();

        editPhone = findViewById(R.id.editPhone);
        editCode = findViewById(R.id.editCode);

        timerText = findViewById(R.id.timertv);
        timerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editPhone.getText().toString().trim().isEmpty()) {
                    sendVerificationCode();
                    startTimer();
                }
            }
        });

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });

        btn_startVerification = findViewById(R.id.btn_startVerification);
        btn_startVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhone.setVisibility(View.INVISIBLE);
                btn_startVerification.setVisibility(View.INVISIBLE);

                sendVerificationCode();
                startTimer();
                editCode.setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.VISIBLE);
                timerText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void verifySignInCode() {
        String code = editCode.getText().toString().trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PhoneActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(PhoneActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void sendVerificationCode() {
        phone = editPhone.getText().toString().trim();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                40,
                TimeUnit.SECONDS,
                this,
                callbacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editCode.setText(code);
                //verifying the code
                verifySignInCode();
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            codeSent = s;
            mResendToken = token;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);

        }
    };

    public void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            int second = 40;

            @Override
            public void run() {
                if (second <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerText.setText("RESEND CODE");
                            timer.cancel();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerText.setText("00:" + second--);
                        }
                    });
                }

            }
        }, 0, 1000);
    }

    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token) {
        phone = editPhone.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                40,
                TimeUnit.SECONDS,
                this,
                callbacks,
                token);
    }
}


