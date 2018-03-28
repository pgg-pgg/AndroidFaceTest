package pgg.androidfacetest.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by PDD on 2018/2/28.
 */

public interface Api {

    @GET("repos/{owner}/{repo}/contributors")
    Call<ResponseBody> contributorBySimpleGetCall(@Path("owner") String owner,@Path("repo") String repo);

}
