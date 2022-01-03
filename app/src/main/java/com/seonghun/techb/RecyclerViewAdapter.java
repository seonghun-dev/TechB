package com.seonghun.techb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Blog> contents = new ArrayList<>();
    static ArrayList<Integer> marked = new ArrayList<>();
    private String sharedmark;
    final String sharedName = sharedmark;
    SharedPreferences Pref;
    SharedPreferences.Editor editor;

    public RecyclerViewAdapter(int child_case, Context context) {
        Pref = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        editor = Pref.edit();
        marked = getStringArrayPref(context, "marked");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contents.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    contents.add(item.getValue(Blog.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException()); // Getting Post failed, log a message
            }
        });
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
        return new MessageVieHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageVieHolder messageVieHolder = ((MessageVieHolder) holder);
        Blog content = contents.get(position);
        messageVieHolder.setTitle(content.getTitle());
        messageVieHolder.setCompany(content.getCompany());
        messageVieHolder.setContent(content.getContent());
        messageVieHolder.setImg(content.getImg(), position);
        if (marked.contains(position)) {
            messageVieHolder.setBook_mark_vis();
        } else {
            messageVieHolder.setBook_mark_invis();
        }


        final String list_url = content.getUrl();
        holder.itemView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);

                intent.putExtra("url", list_url);
                v.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new android.view.View.OnLongClickListener() {
            @Override
            public boolean onLongClick(android.view.View v) {
                int num = holder.getAdapterPosition();
                if (messageVieHolder.setBook_mark()) {
                    marked.add(num);
                    Toast.makeText(v.getContext(), "저장했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    marked.remove((Integer) num);
                    Toast.makeText(v.getContext(), "제했습니다.", Toast.LENGTH_SHORT).show();
                }
                setStringArrayPref(v.getContext(), "marked", marked);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    //Shared 비우는 메소드
    public void clearShared() {
        editor.clear();
        editor.commit();
    }


    //매개변수로 shared에 저장할 key값과 ArrayList<String> 타입의 List를 넣어주면 shared에 저장하는 메소드
    private void setStringArrayPref(Context context, String key, ArrayList<Integer> values) {
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }


    //매개변수로 저장한 Key값을 입력해주고 받을 ArrayList<String>에 담아내는 메소드
    private ArrayList<Integer> getStringArrayPref(Context context, String key) {
        String json = Pref.getString(key, null);
        ArrayList<Integer> urls = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(Integer.parseInt(url));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }


    private class MessageVieHolder extends RecyclerView.ViewHolder {
        public TextView list_title;
        public TextView list_content;
        public TextView list_company;
        public ImageButton book_mark;
        public ImageView list_img;

        public MessageVieHolder(android.view.View view) {
            super(view);
            list_title = view.findViewById(R.id.list_title);
            list_content = view.findViewById(R.id.list_content);
            list_company = view.findViewById(R.id.list_company);
            book_mark = view.findViewById(R.id.book_mark);
            list_img = view.findViewById(R.id.list_img);
        }

        public void setTitle(String title) {
            this.list_title.setText(title);
        }

        public void setContent(String content) {
            this.list_content.setText(content);
        }

        public void setCompany(String company) {
            this.list_company.setText(company);
        }

        public boolean setBook_mark() {
            if (book_mark.getVisibility() == android.view.View.VISIBLE) {
                book_mark.setVisibility(android.view.View.INVISIBLE);
                return false;
            } else {
                book_mark.setVisibility(View.VISIBLE);
                return true;
            }
        }

        public void setBook_mark_vis() {
            book_mark.setVisibility(View.VISIBLE);
        }

        public void setBook_mark_invis() {
            book_mark.setVisibility(View.INVISIBLE);
        }

        public void setImg(String imgUrl, int num) {
            if (!(imgUrl.equals(""))) {
                new DownloadFilesTask().execute(imgUrl);
            } else {
                    list_img.setVisibility(View.GONE);
            }

        }

        private class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... strings) {
                Bitmap bmp = null;
                try {
                    String img_url = strings[0]; //url of the image
                    URL url = new URL(img_url);
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                // doInBackground 에서 받아온 total 값 사용 장소
                list_img.setImageBitmap(result);
            }
        }
    }


}