package example.com.albumdisplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

/**
 * Created by admin on 2017/6/28.
 */

public class PhotoAdapter extends RecyclerView.Adapter {
    private List<String> mDatas;



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false);
        return new PhotoHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PhotoHolder)holder).setImageViewResource(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }
   public void refreshData(List<String> list){
       mDatas=list;
       notifyDataSetChanged();
   }
  class PhotoHolder extends RecyclerView.ViewHolder{
      private ImageView imageView;
      public PhotoHolder(View itemView) {
          super(itemView);
          imageView= (ImageView) itemView.findViewById(R.id.item_photo_iv);
      }

      public void  setImageViewResource(String path){
          Glide.with(imageView.getContext()).load(new File(path)).into(imageView);
      }

  }
}
