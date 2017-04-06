
package eng.marwatalaat.popularmovies.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailersResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quicktime")
    @Expose
    private List<Object> quicktime = null;
    @SerializedName("youtube")
    @Expose
    private List<MovieTrailers> youtube = null;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The quicktime
     */
    public List<Object> getQuicktime() {
        return quicktime;
    }

    /**
     * 
     * @param quicktime
     *     The quicktime
     */
    public void setQuicktime(List<Object> quicktime) {
        this.quicktime = quicktime;
    }

    /**
     * 
     * @return
     *     The youtube
     */
    public List<MovieTrailers> getYoutube() {
        return youtube;
    }

    /**
     * 
     * @param youtube
     *     The youtube
     */
    public void setYoutube(List<MovieTrailers> youtube) {
        this.youtube = youtube;
    }

}
