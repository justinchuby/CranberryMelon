package models;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;

/**
 * Created by chuby on 2016/10/22.
 */

public class Contact {

    private String name;
    private long id;
    private boolean favorited;
    private PhoneNumber[] numbers;
    private Map<String, String> instantMessages;
    private Date birthday;
    private String location;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public PhoneNumber[] getNumbers() {
        return numbers;
    }

    public Map<String, String> getInstantMessages() {
        return instantMessages;
    }

    public String getInstantMessages(String name) {
        return instantMessages.get(name);
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return location;
    }


    public static Contact fromJSON(JSONObject jsonObject) {
        Contact contact = new Contact();
        try {
            contact.name = jsonObject.getString("name");
            contact.id = jsonObject.getLong("id");
            contact.favorited = jsonObject.getBoolean("favorited");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            contact.birthday = formatter.parse(jsonObject.getString("birthday"));
            contact.location = jsonObject.getString("location");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}

class PhoneNumber {
    String number;
    String type;
}

