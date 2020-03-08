package gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

import core.manager.ManagerActivity;
import gui.activity.LoginRegisterActivity;

public class FragmentRegister extends Fragment {

  private Switch registerAsCompany;
  private TextView registerUsername;
  private TextView registerPassword;
  private TextView registerName;
  private TextView registerSurname;
  private TextView registerEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_layout, container, false);


        registerAsCompany=view.findViewById(R.id.registerAsCompany);
        registerUsername=view.findViewById(R.id.usernameRegister);
        registerPassword=view.findViewById(R.id.passwordRegister);
        registerName=view.findViewById(R.id.nameRegister);
        registerSurname=view.findViewById(R.id.surnameRegister);
        registerEmail=view.findViewById(R.id.emailRegister);

        View registerButton = view.findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerActivity.getInstance().closeKeyBoard(getActivity());
                ((LoginRegisterActivity) ManagerActivity.getInstance().getActivity(LoginRegisterActivity.class)).register();
            }
        });

        return view;

    }

    public String getRegisterAsCompany() {
        return registerAsCompany.isChecked() ? "true" : "false";
    }

    public String getRegisterName() {
        return registerName.getText().toString();
    }

    public String getRegisterSurname() {
        return registerSurname.getText().toString();
    }

    public String getRegisterEmail() {
        return registerEmail.getText().toString();
    }

    public String getRegisterPassword() {
        return registerPassword.getText().toString();
    }

    public String getRegisterUsername() {
        return registerUsername.getText().toString();
    }
}
