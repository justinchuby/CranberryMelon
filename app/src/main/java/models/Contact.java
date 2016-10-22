package models;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;

/**
 * Created by chuby on 2016/10/22.
 */

public class Contact {

    private String name;
    private long id;
    private String systemId;
    private boolean favorited;
    private ArrayList<PhoneNumber> numbers;
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

    public ArrayList<PhoneNumber> getNumbers() {
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
            ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();
            JSONArray phoneNumbersJSON = jsonObject.getJSONArray("numbers");
            for (int i = 0 ; i < phoneNumbersJSON.length(); i++) {
                JSONObject obj = phoneNumbersJSON.getJSONObject(i);
                String number = obj.getString("number");
                String type = obj.getString("type");
                phoneNumbers.add(new PhoneNumber(number, type));
            }
            contact.numbers = phoneNumbers;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return contact;
    }
}

class PhoneNumber {
    String number;
    String type;

    public PhoneNumber(String number, String type) {
        this.number = number;
        this.type = type;
    }
}

