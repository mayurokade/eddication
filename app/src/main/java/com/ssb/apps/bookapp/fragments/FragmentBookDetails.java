package com.ssb.apps.bookapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.activities.MainActivity;
import com.ssb.apps.bookapp.adapter.BooKListAudioAdapter;
import com.ssb.apps.bookapp.api.ApiClient;
import com.ssb.apps.bookapp.api.ApiInterface;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.FragmentBookDetailBinding;
import com.ssb.apps.bookapp.model.BookInfoModel;
import com.ssb.apps.bookapp.model.StatusModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBookDetails extends Fragment implements BooKListAudioAdapter.OnItemClick {
    public static final String TAG = FragmentBookDetails.class.getName();
    public static FragmentBookDetailBinding binding;
    BookInfoModel data;
    BooKListAudioAdapter adapter;
    ApiInterface apiService;
    public static MediaPlayer mediaPlayer;
    static Handler handler = new Handler();
    public static int lastPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        ((MainActivity) getActivity()).relSearch.setVisibility(View.INVISIBLE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessagePurchaesReceiver, new IntentFilter("book_parchaes"));
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Log.e(TAG, "init: call " + BookApp.cache.readString(getActivity(), Constant.TOKEN, ""));
        Log.e(TAG, "init: User id " + BookApp.cache.readString(getActivity(), Constant.USERID, ""));
        data = (BookInfoModel) getArguments().getSerializable("data");
        binding.rvListAudio.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.tvBookName.setText("" + data.getBookInfo().getBookInfoData().getBookName());
        binding.tvAutherName.setText("By : " + data.getBookInfo().getBookInfoData().getBookAutherName());
        binding.tvCategory.setText("Category : " + data.getBookInfo().getBookInfoData().getBookCatName());
        binding.ratingBar.setCount(Integer.parseInt(data.getBookInfo().getBookInfoData().getAvgRating()));
        binding.seekBar.setMax(100);
        binding.tvOurPrice.setText("Rs. " + data.getBookInfo().getBookInfoData().getBookPrice() + "/-");
        if (data.getChapterInfo().getChapterData() != null)
            binding.chapterCount.setText("Chapter (" + data.getChapterInfo().getChapterData().size() + ")");
        mediaPlayer = new MediaPlayer();
        IOUtils.loadImage(getActivity(), binding.ivBanner, data.getBookInfo().getBookImagePath() + "/" + data.getBookInfo().getBookInfoData().getBookBannerImage(), R.drawable.default_thumb_book_detail);
        binding.ivBanner.setAlpha(0.4f);
        binding.ratingBar.setCount(data.getBookInfo().getBookInfoData().getAvgRating() == null ? 0 : Integer.parseInt(data.getBookInfo().getBookInfoData().getAvgRating()));
        if (data.getChapterInfo().getChapterData() != null && data.getChapterInfo().getChapterData().size() > 0) {
            callAdapter();
        } else {
            IOUtils.showAlertDialog(getActivity(), getActivity().getString(R.string.alert), getActivity().getString(R.string.show));
        }

        if (data.getBookInfo().getBookInfoData().getHasBookInCart())
            binding.ivBucket.setImageResource(R.drawable.like_active);
        else
            binding.ivBucket.setImageResource(R.drawable.like_deactive);

        binding.ivMusicBtn.setOnClickListener(v -> {
            if (IOUtils.isConnected(getActivity()))
                processPlay();
            else
                IOUtils.errorShowSnackBar(getActivity(), getActivity().getString(R.string.msg_internet_connection));
        });

        binding.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                binding.tvCompleted.setText(millinsecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                binding.seekBar.setSecondaryProgress(percent);
            }
        });

        binding.ivBucket.setOnClickListener(v -> {
            if (data.getBookInfo().getBookInfoData().getHasBookInCart())
                deleteCall();
            else
                callAddBookBicket();

        });


    }

    private void callAdapter() {
        adapter = new BooKListAudioAdapter(getActivity(), data.getChapterInfo().getChapterData(), data.getChapterInfo().getChapterAudioPath(), data.getBookInfo().getBookInfoData().getHasUserPaid());
        adapter.setCallback(this);
        binding.rvListAudio.setAdapter(adapter);
    }

    private void deleteCall() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<StatusModel> call = apiService.getDeleteBookFromBucketList(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", data.getBookInfo().getBookInfoData().getBookId(), "23");
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.errorShowSnackBar(getActivity(), response.body().getMsg());
                            data.getBookInfo().getBookInfoData().setHasBookInCart(false);
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

    private void callAddBookBicket() {
        if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());
            Call<StatusModel> call = apiService.addBookBucket(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1", data.getBookInfo().getBookInfoData().getBookId());
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            IOUtils.showSnackBar(getActivity(), response.body().getMsg());
                            binding.ivBucket.setImageResource(R.drawable.like_active);
                            data.getBookInfo().getBookInfoData().setHasBookInCart(true);
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

    private void processPlay() {
        if (lastPosition > -1)
            if (mediaPlayer.isPlaying()) {
                handler.removeCallbacks(updater);
                mediaPlayer.pause();
                data.getChapterInfo().getChapterData().get(lastPosition).setStart(false);
                adapter.notifyItemChanged(lastPosition);
                binding.ivMusicBtn.setImageResource(R.drawable.ic_pause);
            } else {
                mediaPlayer.start();
                binding.ivMusicBtn.setImageResource(R.drawable.ic_played);
                data.getChapterInfo().getChapterData().get(lastPosition).setStart(true);
                adapter.notifyItemChanged(lastPosition);
                updateSeekBar();
            }
    }


    private void perpareMediaPlayer() {

    }


    public static String millinsecondsToTimer(long milliSeconds) {
        String timerString = "";
        String secondsString;

        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        return (timerString + minutes + ":" + secondsString);
    }

    private static Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            binding.tvCompleted.setText(millinsecondsToTimer(currentDuration));
        }
    };

    public static void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            binding.seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    @Override
    public void approve(BookInfoModel.ChapterDatum datum, int position, boolean isPlayed) {
        /*try {
            Log.e(TAG, "approve: is played " + isPlayed);
            String url = BookApp.cache.readString(getActivity(), Constant.URL, "") + data.getChapterInfo().getChapterAudioPath() + "/"
                    + datum.getChapterFile();
            Log.e(TAG, "approve: url " + url);

            if (isPlayed) {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                binding.tvTotalTime.setText(millinsecondsToTimer(mediaPlayer.getDuration()));
                mediaPlayer.start();
                binding.ivMusicBtn.setImageResource(R.drawable.ic_played);
                updateSeekBar();
            } else {
                processPlay();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getActivity(), "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void paymentpaid(BookInfoModel.ChapterDatum d, int position, boolean isPlayed) {
        paymentKey();
        Intent intent = new Intent("razoer_pay_call");
        intent.putExtra("data",data);
        intent.putExtra("key","rzp_test_XuTHHGQRiVBQaO");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private BroadcastReceiver mMessagePurchaesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            data = (BookInfoModel) intent.getSerializableExtra("data");
            callAdapter();

        }
    };

    private void paymentKey() {
      /*  if (IOUtils.isConnected(getActivity())) {
            IOUtils.hideKeyBoard(getActivity());
            //show progress dialog
            IOUtils.startLoadingView(getActivity());

            Call<BucketModel> call = apiService.getBucketList(BookApp.cache.readString(getActivity(), Constant.USERID, ""),
                    BookApp.cache.readString(getActivity(), Constant.TOKEN, ""), "1");
            call.enqueue(new Callback<BucketModel>() {
                @Override
                public void onResponse(Call<BucketModel> call, Response<BucketModel> response) {
                    IOUtils.stopLoading();
                    if (response.code() == Constant.FLAG_SUCCESS) {
                        if (response.body().getStatus() == true) {
                            list = response.body().getCartData();
                            binding.rvBucket.setAdapter(new BucketAdapter(getActivity(), list, 1, response.body().getBookImagePath(), new BucketAdapter.OnItemClickListener() {

                                @Override
                                public void onItemClick(DashboardResModel.BookData item, int position) {
                                    deleteCall(position);
                                }
                            }));
                            binding.tvNoData.setVisibility(View.GONE);
                            binding.rvBucket.setVisibility(View.VISIBLE);
                        } else {
                            if (response.body().getMsg().contains("Invalid")) {
                                IOUtils.logout(getActivity());
                            } else {
                                IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), response.body().getMsg());// show alert dialog if invalid credential
                            }
                            binding.tvNoData.setVisibility(View.VISIBLE);
                            binding.rvBucket.setVisibility(View.GONE);
                        }
                    }

                }

                @Override
                public void onFailure(Call<BucketModel> call, Throwable t) {
                    IOUtils.stopLoadingView();
                    IOUtils.showAlertDialog(getActivity(), getString(R.string.error_message), getString(R.string.something_went));// show alert dialog if invalid credential
                }
            });

        } else
            IOUtils.showSnackBar(getActivity(), getString(R.string.err_internet));*/

    }



    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }


}
