package elsaghier.example.com.ntlbaking.ApiWork;

import java.util.List;

import elsaghier.example.com.ntlbaking.Models.ResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ELSaghier on 12/2/2017.
 */

public interface ResponseInterFace {
    @GET("baking.json")
    Call<List<ResponseModel>> getRecipes();


}
