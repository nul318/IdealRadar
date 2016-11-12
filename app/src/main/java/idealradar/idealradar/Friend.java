package idealradar.idealradar;

public class Friend{
    private String age;
    private String user_name;
    private String similar_rate;
    Friend(String age, String user_name, String similar_rate){
        this.age=age;
        this.user_name=user_name;
        this.similar_rate=similar_rate;
    }
    String getAge(){return age;}
    String getUserName(){return user_name;}
    String getSimilarRate(){return similar_rate;}
    void setAge(String age){this.age=age;}
    void setUserName(String user_name){this.user_name=user_name;}
    void setSimilarRate(String similar_rate){this.similar_rate=similar_rate;}
}
