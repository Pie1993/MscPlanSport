package gui.fragment;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.test.R;

public class FPALoginRegister extends FragmentPagerAdapter {

    private int NUM_ITEMS = 2;
    private Context context;

    private FragmentLogin fragmentLogin;
    private FragmentRegister fragmentRegister;
    private ProgressDialog dialog;


    public FPALoginRegister(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        fragmentLogin = new FragmentLogin();
        fragmentRegister = new FragmentRegister();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return fragmentLogin;
            case 1:
                return fragmentRegister;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return context.getResources().getString(R.string.login);
            default:
                return context.getResources().getString(R.string.register);
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    public String getUsername() {
        return fragmentLogin.getUsername();
    }

    public String getPassword() {
        return  fragmentLogin.getPassword();
    }

    public String getRegisterAsCompany() {
        return  fragmentRegister.getRegisterAsCompany();
    }

    public String getRegisterName() {
        return fragmentRegister.getRegisterName();
    }

    public String getRegisterSurname() {
        return fragmentRegister.getRegisterSurname();
    }

    public String getRegisterEmail() {
        return fragmentRegister.getRegisterEmail();
    }

    public String getRegisterPassword() {
        return fragmentRegister.getRegisterPassword();
    }

    public String getRegisterUsername() {
        return fragmentRegister.getRegisterUsername();
    }

    public void startProgressBar() {
        dialog = ProgressDialog.show(fragmentLogin.getActivity(), "Wait",
                              "Loading. Please wait...", true);
    }

    public void stopProgressBar()
    {
        dialog.dismiss();
    }
}
