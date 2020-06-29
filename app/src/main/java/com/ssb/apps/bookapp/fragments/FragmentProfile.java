package com.ssb.apps.bookapp.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentProfileBinding;
import com.ssb.apps.bookapp.databinding.WriterDialogBinding;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.ProfileModel;
import com.ssb.apps.bookapp.model.StatusModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {
    public static final String TAG = FragmentProfile.class.getName();
    FragmentProfileBinding binding;
    ApiInterface apiService;
    ProfileModel profileModel = new ProfileModel();

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //
    }

    private void init() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        IOUtils.hideKeyBoard(getActivity());
        profileData();
        binding.btnBcmWriter.setOnClickListener(v->{
            dialoge();
        });
        binding.tvEditProfile.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", profileModel);
            FramgnetAutherProfile fragment = new FramgnetAutherProfile();
            fragment.setArguments(bundle);
            ((MainActivity) getActivity()).loadFragment(fragment);
        });
    }

    private void profileData() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            Call<ProfileModel> call = apiService.getUserProfile(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1");
            call.enqueue(new Callback<ProfileModel>() {
                @Override
                public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                    IOUtils.stopLoading();
                    Log.e(TAG, "onResponse: call " + call.request());
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        profileModel = response.body();
                        if (response.body().getStatus() == true) {
                            if (BookApp.cache.readBoolean(getActivity(), Constant.ISAUHTHER, false))
                                profileModel.getProfileData().get(0).setAuther(true);
                            else
                                profileModel.getProfileData().get(0).setAuther(false);

                            binding.setProfile(profileModel.getProfileData().get(0));
                            binding.setPaneldetail(profileModel.getPanelDetails());
                            binding.ratingBar.setCount(profileModel.getProfileData().get(0).getAvgRating() == null ? 0 :
                                    Integer.parseInt(profileModel.getProfileData().get(0).getAvgRating()));
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<ProfileModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));
    }

    public void dialoge() {
        Log.e(TAG, "dialoge: inside dailog ");
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        WriterDialogBinding writerDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.writer_dialog, null, false);
        alertDialog.setView(writerDialogBinding.getRoot());
        AlertDialog dialog = alertDialog.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        writerDialogBinding.btnRegister.setOnClickListener(v -> {
            if (!IOUtils.isEmailValid(writerDialogBinding.etEmail.getText().toString())) {
                writerDialogBinding.etEmail.setError(getActivity().getText(R.string.str_valid_enter));
                return;
            } else if (writerDialogBinding.etBio.getText().toString().trim().isEmpty()) {
                writerDialogBinding.etBio.setError(getActivity().getText(R.string.tr_plz_enter));
                return;
            } else if (writerDialogBinding.etPassword.getText().toString().trim().isEmpty()) {
                writerDialogBinding.etPassword.setError(getActivity().getText(R.string.tr_plz_enter));
                return;
            } else if (writerDialogBinding.etPassword.getText().toString().trim().length() < 5) {
                writerDialogBinding.etPassword.setError(getActivity().getText(R.string.str_pass_length));
                return;
            } else if (writerDialogBinding.etConfPassword.getText().toString().trim().length() < 5) {
                writerDialogBinding.etConfPassword.setError(getActivity().getText(R.string.str_pass_length));
                return;
            } else if (writerDialogBinding.etConfPassword.getText().toString().trim().isEmpty()) {
                writerDialogBinding.etConfPassword.setError(getActivity().getText(R.string.tr_plz_enter));
                return;
            } else if (!writerDialogBinding.etPassword.getText().toString().equals(writerDialogBinding.etConfPassword.getText().toString())) {
                Toast.makeText(getActivity(), getActivity().getText(R.string.str_pass_same), Toast.LENGTH_SHORT).show();
                return;
            } else {
                dialog.dismiss();
                writerRegister(dialog, writerDialogBinding);
            }

        });
        dialog.show();
    }

    private void writerRegister(AlertDialog dialog, WriterDialogBinding writerDialogBinding) {

        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            Log.e(TAG, "writerRegister: email " + writerDialogBinding.etEmail.getText().toString());
            Log.e(TAG, "writerRegister: pass " + writerDialogBinding.etPassword.getText().toString());
            Log.e(TAG, "writerRegister: bio " + writerDialogBinding.etBio.getText().toString());
            Log.e(TAG, "writerRegister: USERID " + BookApp.cache.readString(getActivity(), Constant.USERID, ""));
            Log.e(TAG, "writerRegister: bio " + BookApp.cache.readString(getActivity(), Constant.TOKEN, ""));
            Call<StatusModel> call = apiService.writerRegister(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", "" + writerDialogBinding.etEmail.getText().toString(),
                    "" + writerDialogBinding.etPassword.getText().toString(), "" + writerDialogBinding.etBio.getText().toString());
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    Log.e(TAG, "onResponse: call " + call.request());
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            BookApp.cache.writeBoolean(getActivity(), Constant.ISAUHTHER, true);
                            ((MainActivity) getActivity()).tv_writer.setVisibility(View.GONE);
                            dialog.dismiss();
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg() + "/n" + response.body().getErrorData().getPassword());// show alert dialog if invalid credential
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<StatusModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));
    }

}
