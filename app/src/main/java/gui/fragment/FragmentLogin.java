package gui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

import core.manager.ManagerActivity;
import gui.activity.LoginRegisterActivity;


public class FragmentLogin extends Fragment {


    private TextView username;
    private TextView password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_layout, container, false);

        View loginButton = view.findViewById(R.id.login);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerActivity.getInstance().closeKeyBoard(getActivity());
                ((LoginRegisterActivity) ManagerActivity.getInstance().getActivity(LoginRegisterActivity.class)).login();
            }
        });

        return view;
    }


    public String getUsername() {
        return username.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }
}
