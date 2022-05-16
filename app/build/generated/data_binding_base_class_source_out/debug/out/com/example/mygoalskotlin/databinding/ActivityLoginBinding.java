// Generated by view binder compiler. Do not edit!
package com.example.mygoalskotlin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mygoalskotlin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonLogin;

  @NonNull
  public final Button buttonRegister;

  @NonNull
  public final EditText editTextEmail;

  @NonNull
  public final EditText editTextPassword;

  @NonNull
  public final ProgressBar loginProgressBar;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final TextView textView2;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button buttonLogin,
      @NonNull Button buttonRegister, @NonNull EditText editTextEmail,
      @NonNull EditText editTextPassword, @NonNull ProgressBar loginProgressBar,
      @NonNull ImageView logo, @NonNull TextView textView2) {
    this.rootView = rootView;
    this.buttonLogin = buttonLogin;
    this.buttonRegister = buttonRegister;
    this.editTextEmail = editTextEmail;
    this.editTextPassword = editTextPassword;
    this.loginProgressBar = loginProgressBar;
    this.logo = logo;
    this.textView2 = textView2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonLogin;
      Button buttonLogin = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogin == null) {
        break missingId;
      }

      id = R.id.buttonRegister;
      Button buttonRegister = ViewBindings.findChildViewById(rootView, id);
      if (buttonRegister == null) {
        break missingId;
      }

      id = R.id.editTextEmail;
      EditText editTextEmail = ViewBindings.findChildViewById(rootView, id);
      if (editTextEmail == null) {
        break missingId;
      }

      id = R.id.editTextPassword;
      EditText editTextPassword = ViewBindings.findChildViewById(rootView, id);
      if (editTextPassword == null) {
        break missingId;
      }

      id = R.id.loginProgressBar;
      ProgressBar loginProgressBar = ViewBindings.findChildViewById(rootView, id);
      if (loginProgressBar == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, buttonLogin, buttonRegister,
          editTextEmail, editTextPassword, loginProgressBar, logo, textView2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
