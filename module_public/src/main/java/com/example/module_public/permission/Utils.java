package com.example.module_public.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Utils {
    /**
     * 拨打指定电话
     */
    public static void makeCall(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 选择联系人
     */
    public static void chooseContact(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), requestCode);
    }


    public static void onGetChooseContactData(Context context, Intent data, @NonNull ReadContactListener listener) {
        Uri contactUri = data.getData();
        if (null == contactUri) {
            listener.onFailed();
            return;
        }

        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(contactUri, null, null, null, null);
        } catch (SecurityException e) {
            listener.onFailed();
            e.printStackTrace();
            return;
        }
        if (cursor == null || !cursor.moveToFirst()) {
            closeCursor(cursor);
            listener.onFailed();
            return;
        }
        String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        ContactInfo contactInfo = new ContactInfo(contactName);
        final String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        closeCursor(cursor);

        Cursor phoneCursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
        if (null == phoneCursor) {
            listener.onFailed();
            return;
        }

        while (phoneCursor.moveToNext()) {
            String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactInfo.addPhone(phone);
        }
        closeCursor(phoneCursor);
        listener.onSuccess(contactInfo);
    }

    public interface ReadContactListener {

        /**
         * 选择成功
         *
         * @param contactInfo 联系人实体类
         */
        void onSuccess(ContactInfo contactInfo);

        /**
         * 选择失败
         */
        void onFailed();
    }

    private static void closeCursor(Cursor c) {
        if (null != c && !c.isClosed()) {
            c.close();
        }
    }

    public static class ContactInfo {
        private String name;
        private List<String> phones;

        public ContactInfo(String name) {
            this.name = name;
            this.phones = new LinkedList<>();
        }

        public void addPhone(String phone) {
            phones.add(phone);
        }

        public String getName() {
            return name;
        }

        public List<String> getPhones() {
            return phones;
        }

        @Override
        public String toString() {
            JSONObject object = new JSONObject();
            //name
            try {
                object.put("name", name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //phones
            for (int i = 0; i < phones.size(); i++) {
                String phone = phones.get(i);
                StringBuilder builder = new StringBuilder("phone");
                if (i > 0) {
                    builder.append(i);
                }
                try {
                    object.put(builder.toString(), phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return object.toString();
        }
    }
}
