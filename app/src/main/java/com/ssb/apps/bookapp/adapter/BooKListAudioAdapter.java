package com.ssb.apps.bookapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ssb.apps.bookapp.R;
import com.ssb.apps.bookapp.apps.BookApp;
import com.ssb.apps.bookapp.databinding.ItemBookAudioBinding;
import com.ssb.apps.bookapp.model.BookInfoModel;
import com.ssb.apps.bookapp.utils.Constant;
import com.ssb.apps.bookapp.utils.IOUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import static com.ssb.apps.bookapp.fragments.FragmentBookDetails.*;
import static com.ssb.apps.bookapp.fragments.FragmentBookDetails.mediaPlayer;
import static com.ssb.apps.bookapp.fragments.FragmentBookDetails.millinsecondsToTimer;
import static com.ssb.apps.bookapp.fragments.FragmentBookDetails.updateSeekBar;

public class BooKListAudioAdapter extends RecyclerView.Adapter<BooKListAudioAdapter.MyViewHolder> {

    public static final String TAG = BookListAdapter.class.getName();
    private LayoutInflater layoutInflater;
    private Context mcontext;
    List<BookInfoModel.ChapterDatum> list;
    String path = "";
    OnItemClick onItemClick;
    boolean hasUserPaid;
    int pos = -1;
    private boolean initialStage = true;



    public BooKListAudioAdapter(Context activity, List<BookInfoModel.ChapterDatum> chapterData, String chapterAudioPath, Boolean hasUserPaid) {
        this.mcontext = activity;
        this.list = chapterData;
        this.path = chapterAudioPath;
        this.hasUserPaid = hasUserPaid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemBookAudioBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_book_audio, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (list.size() > 0) {
            holder.itemBookAudioBinding.setAudio(list.get(position));
            holder.itemBookAudioBinding.chapter.setText(list.get(position).getChapterName());
            holder.itemBookAudioBinding.chapterName.setText("Chapter " + (position + 1));
            Log.e(TAG, "onBindViewHolder: status "+hasUserPaid );

            if (!hasUserPaid)
                holder.itemBookAudioBinding.tvChapPaidStatus.setText(list.get(position).getChapterPayStatus().equalsIgnoreCase("free") ? "Free" : "");

            if (list.get(position).isStart())
                holder.itemBookAudioBinding.ivButton.setBackgroundResource(R.drawable.play);
            else
                holder.itemBookAudioBinding.ivButton.setBackgroundResource(R.drawable.pause);

            holder.itemBookAudioBinding.ivButton.setOnClickListener(v -> {
                if (hasUserPaid) {
                    if (IOUtils.isConnected(mcontext)) {
                        playOrPause(holder.itemBookAudioBinding,list.get(position),position);
                       /* if (list.get(position).isStart()) {
                            holder.itemBookAudioBinding.ivButton.setBackgroundResource(R.drawable.ic_pause);
                            list.get(position).setStart(false);
                            notifyDataSetChanged();
                            onItemClick.approve(list.get(position), position, false);
                            playAudio(list.get(position).isStart(), position);
                        } else {
                            holder.itemBookAudioBinding.ivButton.setBackgroundResource(R.drawable.ic_played);
                            setNonEditableOthers(position);
                            onItemClick.approve(list.get(position), position, true);
                            notifyDataSetChanged();
                            playAudio(list.get(position).isStart(), position);
                        }*/
                    } else {
                        IOUtils.errorShowSnackBar((Activity) mcontext, mcontext.getString(R.string.msg_internet_connection));
                    }
                }else if(list.get(position).getChapterPayStatus().equalsIgnoreCase("free")){
                    playOrPause(holder.itemBookAudioBinding,list.get(position),position);
                }else{
                    onItemClick.paymentpaid(list.get(position),position,list.get(position).isStart());
                }

            });

        }
    }

    private void playOrPause(ItemBookAudioBinding itemBookAudioBinding, BookInfoModel.ChapterDatum datum, int position) {
        if (datum.isStart()) {
            itemBookAudioBinding.ivButton.setBackgroundResource(R.drawable.pause);
            datum.setStart(false);
            notifyDataSetChanged();
            onItemClick.approve(datum, position, false);
            playAudio(list.get(position).isStart(), position);
        } else {
            itemBookAudioBinding.ivButton.setBackgroundResource(R.drawable.play);
            setNonEditableOthers(position);
            onItemClick.approve(list.get(position), position, true);
            notifyDataSetChanged();
            playAudio(list.get(position).isStart(), position);
        }
        lastPosition = position;
    }


    private void setNonEditableOthers(int position) {
        for (BookInfoModel.ChapterDatum data : list) {
            if (list.indexOf(data) != position)
                data.setStart(false);
            else
                data.setStart(true);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemBookAudioBinding itemBookAudioBinding;

        public MyViewHolder(@NonNull ItemBookAudioBinding itemView) {
            super(itemView.getRoot());
            this.itemBookAudioBinding = itemView;
        }
    }

    public void setCallback(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void approve(BookInfoModel.ChapterDatum data, int position, boolean isPlayed);
        void paymentpaid(BookInfoModel.ChapterDatum data, int position, boolean isPlayed);
    }

    private void playAudio(boolean start, int position) {
        Log.e(TAG, "playAudio: state " + start);
        if (start) {

            String url = BookApp.cache.readString(mcontext, Constant.URL, "") + path + "/"
                    + list.get(position).getChapterFile();
            Log.e(TAG, "approve: url " + url);
            try {
                //  if()
                if (pos != position) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        binding.ivMusicBtn.setImageResource(R.drawable.ic_pause);
                    }
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setLooping(true);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    new Player().execute(url);
                    Log.e(TAG, "playAudio: inside ");
                    pos = position;

                } else {
                    binding.tvTotalTime.setText(millinsecondsToTimer(mediaPlayer.getDuration()));
                    mediaPlayer.start();
                    updateSeekBar();
                    binding.ivMusicBtn.setImageResource(R.drawable.ic_played);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else {
            mediaPlayer.pause();
            binding.ivMusicBtn.setImageResource(R.drawable.ic_pause);
        }
    }


    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                //Log.e("MyAudioStreamingApp", e.getMessage());
                e.printStackTrace();
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            IOUtils.stopLoading();

            mediaPlayer.start();
            binding.tvTotalTime.setText(millinsecondsToTimer(mediaPlayer.getDuration()));
            binding.ivMusicBtn.setImageResource(R.drawable.ic_played);
            updateSeekBar();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            IOUtils.startLoadingView(mcontext);
        }
    }
}


