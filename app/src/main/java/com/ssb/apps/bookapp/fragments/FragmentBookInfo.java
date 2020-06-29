package com.ssb.apps.bookapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.adapter.ReviewAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentBookInfoBinding;
import com.ssb.apps.bookapp.model.BookInfoModel;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.ReviewModel;
import com.ssb.apps.bookapp.model.StatusModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;
import com.whinc.widget.ratingbar.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBookInfo extends Fragment {
    FragmentBookInfoBinding binding;

    ApiInterface apiService;
    DashboardResModel.BookData data;
    BookInfoModel bookInfoModel = null;

    public FragmentBookInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_info, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //
    }


    private void init() {
        ((MainActivity) getActivity()).relSearch.setVisibility(View.INVISIBLE);
        data = (DashboardResModel.BookData) getArguments().getSerializable("data");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        binding.rvReview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.setBookDetails(data);
        binding.tvViewAll.setOnClickListener(v -> {
            if (bookInfoModel != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", bookInfoModel);
                FragmentBookDetails fragment = new FragmentBookDetails();
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).loadFragment(fragment);
            }
        });
        loadCategory();
        loadReview(false);

        binding.btnSend.setOnClickListener(v -> {
            if (!binding.messageText.getText().toString().isEmpty()) {
                openDialog();
                IOUtils.hideKeyBoard(getActivity());

            }
        });
        binding.ivBucket.setOnClickListener(v -> {
            if (bookInfoModel.getBookInfo().getBookInfoData().getHasBookInCart())
                deleteCall();
            else
                callAddBookInBuket();
        });
    }

    private void callAddBookInBuket() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            Call<StatusModel> call = apiService.addBookBucket(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", bookInfoModel.getBookInfo().getBookInfoData().getBookId());
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.showSnackBar(getActivity(), response.body().getMsg());
                            bookInfoModel.getBookInfo().getBookInfoData().setHasBookInCart(true);
                            binding.ivBucket.setImageResource(R.drawable.like_active);
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.errorShowSnackBar(getActivity(), response.body().getMsg());// show alert dialog if invalid credential
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

    private void deleteCall() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<StatusModel> call = apiService.getDeleteBookFromBucketList(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", bookInfoModel.getBookInfo().getBookInfoData().getBookId(), "23");
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.showSnackBar(getActivity(), response.body().getMsg());
                            bookInfoModel.getBookInfo().getBookInfoData().setHasBookInCart(false);
                            binding.ivBucket.setImageResource(R.drawable.like_deactive);

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
                public void onFailure(Call<StatusModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));
    }


    private void openDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.bottom_sheet);
        TextView tvDone = dialog.findViewById(R.id.tvDone);
        TextView tvSkip = dialog.findViewById(R.id.tvSkip);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        tvDone.setOnClickListener(v -> {
            callAddReview("" + ratingBar.getCount());
            dialog.dismiss();
        });
        tvSkip.setOnClickListener(v -> {
            callAddReview("0");
            dialog.dismiss();
        });

        dialog.show();
    }

    private void callAddReview(String starReview) {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            Call<StatusModel> call = apiService.addBookReview(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", data.getBookId(), starReview,
                    binding.messageText.getText().toString());
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            loadReview(true);
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
                public void onFailure(Call<StatusModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));
    }

    private void loadReview(boolean isMove) {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            Call<ReviewModel> call = apiService.getBookReview(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", data.getBookId());
            call.enqueue(new Callback<ReviewModel>() {
                @Override
                public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getBookReviewList().getStatus() == true) {
                            binding.messageText.setText("");
                            binding.rvReview.setAdapter(new ReviewAdapter(getActivity(), response.body().getBookReviewList().getBookReviewData(), 1, response.body().getBookReviewList().getUserProfilePath()));
                            if (isMove)
                                binding.rvReview.scrollToPosition(response.body().getBookReviewList().getBookReviewData().size() - 1);
                        }
                        if (response.body().getMsg() != null) {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));

    }

    private void loadCategory() {

        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            /*Call<BookInfoModel> call = apiService.getBookInfo(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", data.getBookId());
            */

            Call<BookInfoModel> call = apiService.getBookInfo(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", data.getBookId());
            call.enqueue(new Callback<BookInfoModel>() {
                @Override
                public void onResponse(Call<BookInfoModel> call, Response<BookInfoModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getBookInfo().getStatus() == true) {
                            binding.setBookInfo(response.body().getBookInfo().getBookInfoData());
                            bookInfoModel = response.body();
                            binding.tvBookName.setText("" + bookInfoModel.getBookInfo().getBookInfoData().getBookName());
                            binding.tvAutherName.setText("By : " + bookInfoModel.getBookInfo().getBookInfoData().getBookAutherName());
                            binding.tvCategory.setText("Category : " + bookInfoModel.getBookInfo().getBookInfoData().getBookCatName());
                            binding.ratingBar.setCount(Integer.parseInt(bookInfoModel.getBookInfo().getBookInfoData().getAvgRating()));
                            binding.seekBar.setMax(100);
                            binding.tvPrise.setText("Rs. " + bookInfoModel.getBookInfo().getBookInfoData().getBookPrice());
                            binding.tvOurPrice.setText("Rs. " + bookInfoModel.getBookInfo().getBookInfoData().getBookPrice() + "/-");
                            if (bookInfoModel.getBookInfo().getBookInfoData().getHasBookInCart()) {
                                binding.ivBucket.setImageResource(R.drawable.like_active);
                            } else {
                                binding.ivBucket.setImageResource(R.drawable.like_deactive);
                            }
                            IOUtils.loadImage(getActivity(), binding.ivBanner, bookInfoModel.getBookInfo().getBookImagePath() + "/" + bookInfoModel.getBookInfo().getBookInfoData().getBookBannerImage(),R.drawable.default_thumb_book_detail);
                            binding.ivBanner.setAlpha(0.4f);
                        }
                        if (response.body().getMsg() != null) {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            }
                        }


                    }

                }

                @Override
                public void onFailure(Call<BookInfoModel> call, Throwable t) {
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
        ((MainActivity) getActivity()).setTitleText(getActivity().getString(R.string.book_details));
    }
}
