package idealradar.idealradar;

public class FriendsQueue {
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
    FriendsQueue(String uid,double srate)
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
