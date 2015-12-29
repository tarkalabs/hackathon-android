package in.asid.daybook.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import in.asid.daybook.dto.TagDto;
import in.asid.daybook.models.Tag;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SyncService extends IntentService {

    public static final String TAGS_SYNCED = "TAGS_SYNCED";
    private Realm realm;

    public SyncService() {
        super("services.SyncService");

    }
    public SyncService(String name) {
        super(name);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        realm = Realm.getInstance(this);
        RealmResults<Tag> unsyncedTags = realm.where(Tag.class).equalTo("synced", false).findAll();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://64392e0a.ngrok.com").addConverterFactory(GsonConverterFactory.create()).build();
        BackendService backendService = retrofit.create(BackendService.class);
        for(Tag tag: unsyncedTags) {
            try {
                Response<TagDto> response = backendService.createTag(new TagDto(tag)).execute();
                if(response.code() >= 200 && response.code() < 300) {
                    realm.beginTransaction();
                    tag.setSynced(true);
                    tag.setServerId(response.body().getId());
                    realm.commitTransaction();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        realm.beginTransaction();
        RealmResults<Tag> tags = realm.allObjects(Tag.class);
        List<Object> objects = Arrays.asList(tags.toArray());
        for(Object tag : objects) {
            ((Tag) tag).removeFromRealm();
        }
        realm.commitTransaction();

        // fetch new tags from the backend that have been created elsewhere
        try {
            Response<List<TagDto>> serverTags = backendService.getTags().execute();
            realm.beginTransaction();
            for(TagDto tag : serverTags.body()) {
                Tag localTag = realm.createObject(Tag.class);
                localTag.setName(tag.getName());
                localTag.setServerId(tag.getId());
                localTag.setSynced(true);
            }
            realm.commitTransaction();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent syncedIntent = new Intent(TAGS_SYNCED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(syncedIntent);

    }
}
