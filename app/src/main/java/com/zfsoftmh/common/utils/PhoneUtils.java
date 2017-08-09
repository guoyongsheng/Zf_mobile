package com.zfsoftmh.common.utils;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import com.zfsoftmh.entity.ContactsItemInfo;

import java.util.ArrayList;

/**
 * @author wesley
 * @date: 2017/3/16
 * @Description: 手机相关的工具类 ----单例模式
 */

public class PhoneUtils {

    private static final String TAG = "PhoneUtils";

    private PhoneUtils() {
        try {
            throw new UnsupportedOperationException("u can't instantiate me...");
        } catch (Exception e) {
            LoggerHelper.e(TAG, " PhoneUtils " + e.getMessage());
        }
    }

    /**
     * 判断设备是否是手机
     *
     * @return true:是   false:不是手机
     */
    public static boolean isPhone(Context context) {
        if (context == null) {
            return false;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        if (context == null) {
            return null;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取IMSI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMSI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI(Context context) {
        if (context == null) {
            return null;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取 Line1Number
     *
     * @param context 上下文对象
     * @return Line1Number
     */
    @SuppressLint("HardwareIds")
    public static String getLine1Number(Context context) {
        if (context == null) {
            return null;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getLine1Number() : null;
    }

    /**
     * 获取手机版本号
     *
     * @return 手机的版本号
     */
    public static String getPhoneVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }


    /**
     * 获取手机联系人
     *
     * @return 手机联系人集合
     */
    public static ArrayList<ContactsItemInfo> getPhoneContacts(Context context) {
        ArrayList<ContactsItemInfo> list = new ArrayList<>();
        if (context == null) {
            return list;
        }
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");

        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                ContactsItemInfo info = new ContactsItemInfo();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    info.setId(id);
                    info.setName(name);
                    LoggerHelper.e(TAG, " name " + name + "  ID = " + id);

                    if (name != null) {
                        String character = CharacterParserUtil.getInstance().getSelling(name);
                        if (character != null && character.length() > 0) {
                            String firstLetter = character.substring(0, 1).toUpperCase();
                            if (!RegularUtils.isUpperCaseChar(firstLetter)) {
                                firstLetter = "#";
                            }
                            info.setFirstLetter(firstLetter);
                            LoggerHelper.e(TAG, " firstLetter " + firstLetter);
                        }
                    }

                    String image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    info.setPhotoUri(image_uri);
                    LoggerHelper.e(TAG, " image_uri " + image_uri);

                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    if (pCur != null) {
                        if (pCur.moveToFirst()) {
                            String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            info.setPhone(phone);
                            LoggerHelper.e(TAG, " phone " + phone);
                        }
                        pCur.close();
                    }

                    // get email and type
                    Cursor emailCur = cr.query(
                            android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            android.provider.ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    if (emailCur != null) {
                        if (emailCur.moveToFirst()) {
                            String email = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            String emailType = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                            info.setEmail(email);
                            LoggerHelper.e(TAG, " email " + email + "  emailType = " + emailType);
                        }
                        emailCur.close();
                    }

                    // Get note.......
                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] noteWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                    Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);

                    if (noteCur != null) {
                        if (noteCur.moveToFirst()) {
                            String note = noteCur.getString(noteCur.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Note.NOTE));
                            info.setNote(note);
                            LoggerHelper.e(TAG, " note " + note);
                        }
                        noteCur.close();
                    }


                    // Get Organizations.........
                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, orgWhere, orgWhereParams, null);

                    if (orgCur != null) {
                        if (orgCur.moveToFirst()) {
                            String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                            String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                            info.setDepartment(orgName);
                            LoggerHelper.e(TAG, " orgName " + orgName + " title" + title);
                        }
                        orgCur.close();
                    }
                    list.add(info);
                }
            }
            cur.close();
        }
        return list;
    }


    /**
     * 添加联系人
     *
     * @param info 联系人对象
     * @return true: 添加成功  false: 添加失敗
     */
    public static boolean addContacts(Context context, ContactsItemInfo info) {

        if (context == null || info == null) {
            LoggerHelper.e(TAG, " addContacts " + " context = " + context + " info = " + info);
            return false;
        }
        String DisplayName = info.getName();
        String MobileNumber = info.getPhone();
        String emailID = info.getEmail();
        String company = info.getDepartment();
        String note = info.getNote();

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        //Name
        if (DisplayName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //PhoneNumber
        if (MobileNumber != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        //Email
        if (emailID != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        //Organization
        if (company != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, "")
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }

        //Note
        if (note != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE, note)
                    .build());
        }


        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
            LoggerHelper.e(TAG, " addContacts " + e.getMessage());
            return false;
        }
        return true;
    }
}
