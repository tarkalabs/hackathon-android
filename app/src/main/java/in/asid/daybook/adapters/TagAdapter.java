package in.asid.daybook.adapters;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.asid.daybook.R;
import in.asid.daybook.TagDetailsActivity;
import in.asid.daybook.models.Tag;
import in.asid.daybook.services.SyncService;
import io.realm.Realm;
import io.realm.RealmResults;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private final Realm realm;
    private Context context;
    private RealmResults<Tag> tags;

    public TagAdapter(Context context) {
        this.context = context;

        this.realm = Realm.getInstance(context);
        tags = realm.allObjects(Tag.class);

        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tags = realm.allObjects(Tag.class);
                notifyDataSetChanged();
            }
        }, new IntentFilter(SyncService.TAGS_SYNCED));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tagCardView = LayoutInflater.from(context).inflate(R.layout.tag_card, parent, false);
        return new ViewHolder(tagCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(tags.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void addTag(String tagName) {
        realm.beginTransaction();
        Tag newTag = realm.createObject(Tag.class);
        newTag.setName(tagName);
        realm.commitTransaction();
        tags = realm.allObjects(Tag.class);
        Intent serviceIntent = new Intent(context, SyncService.class);
        context.startService(serviceIntent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tag_title)
        TextView txtTagTitle;
        private String tagTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("VIEWHOLDER" , new Integer(event.getAction()).toString());
                    if(event.getAction()!=MotionEvent.ACTION_UP)
                        return true;
                    Log.i("VIEWHOLDER", event.toString());
                    Intent intent = new Intent(v.getContext(), TagDetailsActivity.class);
                    intent.putExtra("tagName", tagTitle);

                    v.getContext().startActivity(intent);
                    return true;
                }
            });
        }

        public void bindView(String tagTitle) {
            this.tagTitle = tagTitle;
            txtTagTitle.setText(this.tagTitle);
        }
    }
}
