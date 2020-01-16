package com.example.a247news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.a247news.R;
import com.example.a247news.adapter.HeadlinesAdapter;
import com.example.a247news.interfaces.AdapterOnItemClickListener;
import com.example.a247news.interfaces.AdapterOnItemClickListenerEvent;
import com.example.a247news.object.Article;
import com.example.a247news.object.User;
import com.example.a247news.ui.main.PageViewModel;
import com.example.a247news.util.SessionManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseFragment implements AdapterOnItemClickListener {

    private static final String TAG = ProfileFragment.class.getName();

    @BindView(R.id.userViewSV)
    ScrollView mUserView;
    @BindView(R.id.firstNameET)
    EditText mFirstName;
    @BindView(R.id.lastNameET)
    EditText mLastName;
    @BindView(R.id.phoneNoET)
    EditText mPhoneNumber;
    @BindView(R.id.saveBTN)
    Button mSaveBtn;
    @BindView(R.id.registerBTN)
    Button mRegisterBTNn;
    @BindView(R.id.registerRl)
    RelativeLayout mNoUserView;
    @BindView(R.id.logoutIv)
    ImageView mLogout;


    private User mUser;
    private SessionManager mSessionManager;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ProfileFragment newInstance(int index) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: HeadlineNewsFragment");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        mSessionManager = SessionManager.getInstance();

        mRegisterBTNn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterView();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUser == null){
                    mUser = new User();
                }
                mUser.setFirstName(mFirstName.getText().toString());
                mUser.setLastName(mLastName.getText().toString());
                mUser.setPhoneNumber(mPhoneNumber.getText().toString());

                mSessionManager.saveUerInfo(mUser);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSessionManager.logOut();
                showLogoutView();
            }
        });
    }

    @Override
    public void onItemClick(AdapterOnItemClickListenerEvent event) {

    }

    public void setDataToFragment(Object data) {
        mUser = (User) data;
        if (mUser == null){
            showLogoutView();
        }else {
            showRegisterView();

            mFirstName.setText(mUser.getFirstName());
            mLastName.setText(mUser.getLastName());
            mPhoneNumber.setText(mUser.getPhoneNumber());
        }
    }

    private void showLogoutView(){
        mNoUserView.setVisibility(View.VISIBLE);
        mUserView.setVisibility(View.GONE);
    }

    private void showRegisterView(){
        mNoUserView.setVisibility(View.GONE);
        mUserView.setVisibility(View.VISIBLE);

        mFirstName.setText("");
        mLastName.setText("");
        mPhoneNumber.setText("");
    }

}