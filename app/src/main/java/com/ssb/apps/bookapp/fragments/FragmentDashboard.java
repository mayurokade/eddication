package com.ssb.apps.bookapp.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.adapter.BookListAdapter;
import com.ssb.apps.bookapp.adapter.SearchBookAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentDashboardBinding;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDashboard extends Fragment {
    FragmentDashboardBinding binding;
    ApiInterface apiService;
    List<DashboardResModel.BookData> list = new ArrayList<>();
    SearchBookAdapter adapter;

    public FragmentDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadDashBorad();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void init() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.rvLatestBooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvListenersChoice.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvMiddle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvMoreBooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.tvAllCategory.setOnClickListener(v -> {
            FragmentCategory fragment = new FragmentCategory();
            ((MainActivity) getActivity()).loadFragment(fragment);
        });

        adapter = new SearchBookAdapter(getActivity(), R.layout.fragment_dashboard, R.id.lbl_name, list);
        binding.searchBox.setAdapter(adapter);

        binding.searchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //this is the way to find selected object/item
                DashboardResModel.BookData bookData = (DashboardResModel.BookData) adapterView.getItemAtPosition(pos);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",bookData );
                FragmentBookInfo fragment = new FragmentBookInfo();
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).loadFragment(fragment);
            }
        });

        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.searchBox.getText().toString().length() > 2) {
                    loadCategorylist(binding.searchBox.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadDashBorad() {

        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<DashboardResModel> call = apiService.getDashboardDetails(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1");
            call.enqueue(new Callback<DashboardResModel>() {
                @Override
                public void onResponse(Call<DashboardResModel> call, Response<DashboardResModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {

                            if (response.body().getLatestBook().getLatestData() == null) {
                                binding.rvLatestBooks.setVisibility(View.GONE);
                            } else {
                                binding.rvLatestBooks.setVisibility(View.VISIBLE);
                            }

                            if (response.body().getListnerChoice().getListnerData() == null) {
                                binding.rvListenersChoice.setVisibility(View.GONE);
                            } else {
                                binding.rvListenersChoice.setVisibility(View.VISIBLE);
                            }

                            if (response.body().getMiddleOfSomething().getMiddleData() == null) {
                                binding.rvMiddle.setVisibility(View.GONE);
                            } else {
                                binding.rvMiddle.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getMostRated().getMostRatedData() == null) {
                                binding.rvMoreBooks.setVisibility(View.GONE);
                            } else {
                                binding.rvMoreBooks.setVisibility(View.VISIBLE);
                            }
                            binding.rvLatestBooks.setAdapter(new BookListAdapter(getActivity(), response.body().getLatestBook().getLatestData(), 1, response.body().getLatestBook().getBookImagePath()));
                            binding.rvListenersChoice.setAdapter(new BookListAdapter(getActivity(), response.body().getListnerChoice().getListnerData(), 2, response.body().getListnerChoice().getBookImagePath()));
                            binding.rvMiddle.setAdapter(new BookListAdapter(getActivity(), response.body().getMiddleOfSomething().getMiddleData(), 3, response.body().getMiddleOfSomething().getBookImagePath()));
                            binding.rvMoreBooks.setAdapter(new BookListAdapter(getActivity(), response.body().getMostRated().getMostRatedData(), 4, response.body().getMostRated().getBookImagePath()));


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
                public void onFailure(Call<DashboardResModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitleText("Dashboard");
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
                            binding.searchBox.setAdapter(adapter);
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
