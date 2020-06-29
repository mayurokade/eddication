package com.ssb.apps.bookapp.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.adapter.CategoryListAdapter;
import com.ssb.apps.bookapp.adapter.SearchBookAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentCategoryBinding;
import com.ssb.apps.bookapp.model.CategotryResModel;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.SearchResModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ssb.apps.bookapp.fragments.FragmentDashboard.isCateogryOpen;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment {
    FragmentCategoryBinding binding;
    ApiInterface apiService;
    SearchBookAdapter adapter;
    List<DashboardResModel.BookData> list = new ArrayList<>();

    public FragmentCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadCategory();
    }

    private void loadCategory() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<CategotryResModel> call = apiService.getCategoryDetails(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1");
            call.enqueue(new Callback<CategotryResModel>() {
                @Override
                public void onResponse(Call<CategotryResModel> call, Response<CategotryResModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            binding.rvCategory.setAdapter(new CategoryListAdapter(getActivity(), response.body().getCategoryData()));
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
                public void onFailure(Call<CategotryResModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));
    }

    private void init() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ((MainActivity) getActivity()).relSearch.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).tv_writer.setVisibility(View.GONE);

        adapter = new SearchBookAdapter(getActivity(), R.layout.fragment_category, R.id.lbl_name, list);
        if (isCateogryOpen) {
            ((MainActivity) getActivity()).searchBox.setAdapter(adapter);

            ((MainActivity) getActivity()).searchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                    //this is the way to find selected object/item
                    DashboardResModel.BookData bookData = (DashboardResModel.BookData) adapterView.getItemAtPosition(pos);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", bookData);
                    FragmentBookInfo fragment = new FragmentBookInfo();
                    fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).loadFragment(fragment);
                }
            });

            ((MainActivity) getActivity()).searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isCateogryOpen)
                        if (((MainActivity) getActivity()).searchBox.getText().toString().length() > 2) {
                            loadCategorylist(((MainActivity) getActivity()).searchBox.getText().toString());
                        }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitleText("Categories");
    }

    private void loadCategorylist(String searchText) {
        if (IOUtils.isConnected(getActivity())) {
            //     IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            //   IOUtils.startLoadingView(getActivity());
            //Here we are using serach type 1- serach and 2- Category
            Call<SearchResModel> call = apiService.getSearchDetails(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", "1", "", searchText);
            call.enqueue(new Callback<SearchResModel>() {
                @Override
                public void onResponse(Call<SearchResModel> call, Response<SearchResModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            list = new ArrayList<>();
                            list = response.body().getSearchData();
                            adapter = new SearchBookAdapter(getActivity(), R.layout.fragment_dashboard, R.id.lbl_name, list);
                            ((MainActivity) getActivity()).searchBox.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                // IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<SearchResModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));


    }
}
