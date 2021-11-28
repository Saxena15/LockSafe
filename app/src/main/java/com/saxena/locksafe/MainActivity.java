package com.saxena.locksafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ImageButton moreOpts;
    TextView title, username, password , snap;
    EditText title_et, username_et, password_et ;
    ImageView deco;Button submit_btn;CircleImageView profile_pic;
    RecyclerView socials, tags;RecyclerAdapter2 recyclerAdapter2;
    ArrayList<String> text_head = new ArrayList<>();
    ArrayList<String> text_subhead = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.purplishViolet));
        }


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
        load_profile();

        socials = findViewById(R.id.socials);
        tags = findViewById(R.id.tags);
        socials.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter2 = new RecyclerAdapter2(this, text_head, text_subhead);
        socials.setAdapter(recyclerAdapter2);
        snap = findViewById(R.id.snap);
        /////////////////////////////////////////////////////////////////////////////////////////
        moreOpts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_lay);

        deco = dialog.findViewById(R.id.deco);
        title = dialog.findViewById(R.id.title);
        title_et = dialog.findViewById(R.id.title_et);
        username = dialog.findViewById(R.id.username);
        username_et = dialog.findViewById(R.id.username_et);
        password = dialog.findViewById(R.id.password);
        password_et = dialog.findViewById(R.id.password_et);
        submit_btn = dialog.findViewById(R.id.submit_btn);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                if (!TextUtils.isEmpty(title_et.getText().toString()) && !TextUtils.isEmpty(username_et.getText().toString())
                        && !TextUtils.isEmpty(password_et.getText().toString())) {
                    text_head.add(title_et.getText().toString());
                    text_subhead.add(username_et.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "One or more field(s) missing :) ", Toast.LENGTH_SHORT).show();
                }

                HashMap<String , Object> user = new HashMap<>();
                user.put("Title" , title_et.getText().toString());
                user.put("Username/Email" , username_et.getText().toString());
                user.put("Password" , password_et.getText().toString());
                Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Added to Database", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Cannot be added", Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.dismiss();

                if (text_subhead.size() != 0 || text_head.size() != 0){
                    snap.setVisibility(View.GONE);
                }

            }
        });
    }

    public void load_profile(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();String personEmail = acct.getEmail();
            String personId = acct.getId();Uri personPhoto = acct.getPhotoUrl();
            Toast.makeText(getApplicationContext(), "Hello " + personName, Toast.LENGTH_SHORT).show();
        }
        profile_pic = findViewById(R.id.profile_pic);
        moreOpts = findViewById(R.id.moreOpts);
        Glide.with(MainActivity.this).load(acct.getPhotoUrl()).into(profile_pic);

    }
}