package ufjf.heros;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 19/11/2016.
 */

@IgnoreExtraProperties
public class Hero implements Serializable {

    private String emailCreator;
    private String name;
    private String secret_id;
    private String powers;
    private String description;
    private String sex;
    private String age;

    public Hero() {
        // Default constructor required for calls to DataSnapshot.getValue(Hero.class)
    }

    public Hero(String emailCreator, String name, String secret_id, String powers, String description, String sex, String age) {
        this.name = name;
        this.secret_id = secret_id;
        this.powers = powers;
        this.description = description;
        this.sex = sex;
        this.age = age;
        this.emailCreator = emailCreator;
    }

    @Exclude
    public Map<String,Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("emailCreator",emailCreator);
        result.put("name",name);
        result.put("secret_id",secret_id);
        result.put("powers",powers);
        result.put("description",description);
        result.put("sex",sex);
        result.put("age",age);

        return result;
    }

    public String getEmailCreator() {
        return emailCreator;
    }

    public void setEmailCreator(String emailCreator) {
        this.emailCreator = emailCreator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret_id() {
        return secret_id;
    }

    public void setSecret_id(String secret_id) {
        this.secret_id = secret_id;
    }

    public String getPowers() {
        return powers;
    }

    public void setPowers(String powers) {
        this.powers = powers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
