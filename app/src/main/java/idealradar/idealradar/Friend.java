package idealradar.idealradar;

/**
 * Created by Kim Gyu Hwan on 2016-11-12.
 */

public class Friend {
    private String user_id;
    private double similar_rate;

    public void set_User_id(String user_id)
    {
        this.user_id=user_id;
    }
    public void set_Similar_rate(double similar_rate)
    {
        this.similar_rate=similar_rate;
    }
    Friend(String uid,double srate)
    {
        set_User_id(uid);
        set_Similar_rate(srate);
    }
    public String get_User_id()
    {
        return user_id;
    }
    public double get_Similar_rate()
    {
        return similar_rate;
    }
}
