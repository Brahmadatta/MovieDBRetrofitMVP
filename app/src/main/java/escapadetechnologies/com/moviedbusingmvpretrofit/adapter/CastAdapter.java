package escapadetechnologies.com.moviedbusingmvpretrofit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import escapadetechnologies.com.moviedbusingmvpretrofit.R;
import escapadetechnologies.com.moviedbusingmvpretrofit.model.Cast;
import escapadetechnologies.com.moviedbusingmvpretrofit.network.APIClient;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>{

    private Context mContext;
    private List<Cast> castList;

    public CastAdapter(Context mContext, List<Cast> castList) {
        this.mContext = mContext;
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_card, parent, false);

        return new CastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastViewHolder castViewHolder, int i) {

        Cast cast = castList.get(i);

        castViewHolder.tvCharacter.setText(cast.getCharacter());
        castViewHolder.tvName.setText(cast.getName());

        // loading cast profile pic using Glide library
        Glide.with(mContext)
                .load(APIClient.IMAGE_BASE_URL + cast.getProfilePath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        castViewHolder.pbLoadProfile.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        castViewHolder.pbLoadProfile.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                .into(castViewHolder.ivProfilePic);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder{

        public TextView tvCharacter;

        public TextView tvName;

        public ImageView ivProfilePic;

        public ProgressBar pbLoadProfile;


        public CastViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCharacter = itemView.findViewById(R.id.tv_character);
            tvName = itemView.findViewById(R.id.tv_name);
            ivProfilePic = itemView.findViewById(R.id.iv_profile_pic);
            pbLoadProfile = itemView.findViewById(R.id.pb_load_profile);

        }
    }
}
