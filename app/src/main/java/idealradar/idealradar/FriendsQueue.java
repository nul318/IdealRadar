package idealradar.idealradar;

public class FriendsQueue {
    private String user_id,nickname;

    private double similar_rate;

    public void set_User_id(String user_id)
    {
        this.user_id=user_id;
    }
    public void set_Similar_rate(double similar_rate)
    {
        this.similar_rate=similar_rate;
    }
    public void set_NickName(String nickname)
    {
        this.nickname=nickname;
    }
    FriendsQueue(String uid,double srate,String nickname)
    {
        set_User_id(uid);
        set_Similar_rate(srate);
        set_NickName(nickname);
    }
    public String get_User_id()
    {
        return user_id;
    }
    public String get_NickName()
    {
        return nickname;
    }
    public double get_Similar_rate()
    {
        return similar_rate;
    }
}
