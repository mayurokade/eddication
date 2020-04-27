package com.ssb.apps.bookapp.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.ssb.apps.bookapp.R;
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
        apiService = ApiClient.getClient().create(ApiInterface.class);
        data = (BookInfoModel) getArguments().getSerializable("data");
        binding.rvListAudio.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.tvBookName.setText("" + data.getBookInfo().getBookInfoData().getBookName());
        binding.tvAutherName.setText("By : " + data.getBookInfo().getBookInfoData().getBookCatName());
        binding.tvCategory.setText("Category : " + data.getBookInfo().getBookInfoData().getBookCatName());
        binding.ratingBar.setCount(Integer.parseInt(data.getBookInfo().getBookInfoData().getAvgRating()));
        binding.seekBar.setMax(100);
        binding.tvPrise.setText("Rs. "+data.getBookInfo().getBookInfoData().getBookPrice());
        mediaPlayer = new MediaPlayer();
        IOUtils.loadImage(getActivity(), binding.ivBanner, data.getBookInfo().getBookImagePath() + "/" + data.getBookInfo().getBookInfoData().getBookBannerImage());
        binding.ratingBar.setCount(data.getBookInfo().getBookInfoData().getAvgRating() == null ? 0 : Integer.parseInt(data.getBookInfo().getBookInfoData().getAvgRating()));
        if (data.getChapterInfo().getChapterData() != null && data.getChapterInfo().getChapterData().size() > 0) {
            adapter = new BooKListAudioAdapter(getActivity(), data.getChapterInfo().getChapterData(), data.getChapterInfo().getChapterAudioPath());
            adapter.setCallback(this);
            binding.rvListAudio.setAdapter(adapter);
        } else {
            IOUtils.showAlertDialog(getActivity(), getActivity().getString(R.string.alert), getActivity().getString(R.string.show));
        }


        binding.ivMusicBtn.setOnClickListener(v -> {
            if (IOUtils.isConnected(getActivity()))
                processPlay();
            else
                IOUtils.errorShowSnackBar(getActivity(),getActivity().getString(R.string.msg_internet_connection));
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

        binding.ivBucket.setOnClickListener(v->{
            callAddBookBicket();
        });



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
                            IOUtils.showSnackBar(getActivity(),response.body().getMsg());
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
        if (mediaPlayer.isPlaying()) {
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            binding.ivMusicBtn.setImageResource(R.drawable.ic_pause);
        } else {
            mediaPlayer.start();
            binding.ivMusicBtn.setImageResource(R.drawable.ic_played);
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
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }


}
