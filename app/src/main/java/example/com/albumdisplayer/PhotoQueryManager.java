package example.com.albumdisplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class PhotoQueryManager {
    private ContentResolver mContentResolver;
    private Context mContext;


    public PhotoQueryManager(Context context) {
        this.mContext = context;
        mContentResolver = context.getContentResolver();

    }

    /**
     *
     * @return
     */
    public List<String> getAlbum() {
        List<String> mBucketIds = new ArrayList<>();
        ;

        String projects[] = new String[]{
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , projects
                , null
                , null
                , MediaStore.Images.Media.DATE_MODIFIED);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String buckedId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));

                if (mBucketIds.contains(buckedId)) continue;

                mBucketIds.add(buckedId);


            } while (cursor.moveToNext());

            cursor.close();
        }

        return mBucketIds;

    }

    /**
     *
     * @param buckedId
     * @return
     */
    public List<String> getPhoto(String buckedId) {
        List<String> photos = new ArrayList<>();

        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.DATE_MODIFIED}
                , MediaStore.Images.Media.BUCKET_ID + "=?"
                , new String[]{buckedId}
                , MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                photos.add(path);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return photos;
    }


    private String getFrontCoverData(String bucketId) {
        String path = "empty";
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA}, MediaStore.Images.Media.BUCKET_ID + "=?", new String[]{bucketId}, MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor != null && cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return path;
    }

}
