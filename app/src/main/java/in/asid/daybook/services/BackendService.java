package in.asid.daybook.services;


import java.util.List;

import in.asid.daybook.dto.TagDto;
import in.asid.daybook.models.Tag;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface BackendService {
    @POST("/tags")
    Call<TagDto> createTag(@Body TagDto tag);

    @GET("/tags")
    Call<List<TagDto>> getTags();
}
