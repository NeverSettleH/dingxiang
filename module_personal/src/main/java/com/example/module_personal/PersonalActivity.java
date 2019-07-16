package com.example.module_personal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_public.base.BaseActivity;

import com.example.module_public.permission.Utils;
import com.example.module_public.permission.UtilsWithPermission;
import com.example.module_public.routerUtils.Constance;
import com.example.module_public.routerUtils.RouterCenter;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.bean.Special;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.qw.soul.permission.callbcak.SpecialPermissionListener;


@Route(path = Constance.ROUTER_URL_PERSONAL)
public class PersonalActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        findViewById(R.id.alterView).setOnClickListener(this);
        findViewById(R.id.btn_permission_one).setOnClickListener(this);
        findViewById(R.id.btn_permission_more).setOnClickListener(this);
        findViewById(R.id.btn_permission_should).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
          if (v.getId()==R.id.btn_permission_one){
              UtilsWithPermission.makeCall(PersonalActivity.this,"100086");
          }
          if (v.getId()==R.id.btn_permission_more){
               UtilsWithPermission.chooseContact(PersonalActivity.this,REQUEST_CODE_CONTACT);
          }
          if(v.getId()==R.id.btn_permission_should){

          }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CONTACT:
                    Utils.onGetChooseContactData(PersonalActivity.this, data, new Utils.ReadContactListener() {
                        @Override
                        public void onSuccess(Utils.ContactInfo contactInfo) {
                            Toast.makeText(PersonalActivity.this, contactInfo.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed() {

                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }
    public void checkSinglePermission(View view) {
        //you can also use checkPermissions() for a series of permissions
        Permission checkResult = SoulPermission.getInstance().checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION);
        Toast.makeText(this, checkResult.toString(), Toast.LENGTH_SHORT).show();
    }

    public void requestSinglePermission(View view) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Toast.makeText(PersonalActivity.this, permission.toString() +
                                "\n is ok , you can do your operations", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        Toast.makeText(PersonalActivity.this, permission.toString() +
                                " \n is refused you can not do next things", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void requestPermissions(View view) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        Toast.makeText(PersonalActivity.this, allPermissions.length + "permissions is ok" +
                                " \n  you can do your operations", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        Toast.makeText(PersonalActivity.this, refusedPermissions[0].toString() +
                                " \n is refused you can not do next things", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void requestSinglePermissionWithRationale(View view) {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.READ_CONTACTS,
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        Toast.makeText(PersonalActivity.this, permission.toString() +
                                "\n is ok , you can do your operations", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        // see CheckPermissionWithRationaleAdapter
                        if (permission.shouldRationale()) {
                            Toast.makeText(PersonalActivity.this, permission.toString() +
                                    " \n you should show a explain for user then retry ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PersonalActivity.this, permission.toString() +
                                    " \n is refused you can not do next things", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void checkNotification(View view) {
        boolean checkResult = SoulPermission.getInstance().checkSpecialPermission(Special.NOTIFICATION);
        if (checkResult) {
            Toast.makeText(view.getContext(), "Notification is enable", Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(view.getContext())
                    .setMessage("Notification is disable \n you may invoke checkAndRequestPermission and enable notification")
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        }
    }

    public void checkAndRequestNotification(final View view) {
        //if you want do noting or no need all the callbacks you may use SimpleSpecialPermissionAdapter instead
        SoulPermission.getInstance().checkAndRequestPermission(Special.NOTIFICATION, new SpecialPermissionListener() {
            @Override
            public void onGranted(Special permission) {
                Toast.makeText(PersonalActivity.this, "Notification is enable now ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Special permission) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Notification is disable \n you may invoke checkAndRequestPermission and enable notification")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }
        });
    }

    public void checkAndRequestSystemAlert(View view) {
        //if you want do noting or no need all the callbacks you may use SimpleSpecialPermissionAdapter instead
        SoulPermission.getInstance().checkAndRequestPermission(Special.SYSTEM_ALERT, new SpecialPermissionListener() {
            @Override
            public void onGranted(Special permission) {
                Toast.makeText(PersonalActivity.this, "System Alert is enable now ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Special permission) {
                Toast.makeText(PersonalActivity.this, "System Alert is disable yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkAndRequestUnKnownSource(View view) {
        //if you want do noting or no need all the callbacks you may use SimpleSpecialPermissionAdapter instead
        SoulPermission.getInstance().checkAndRequestPermission(Special.UNKNOWN_APP_SOURCES, new SpecialPermissionListener() {
            @Override
            public void onGranted(Special permission) {
                Toast.makeText(PersonalActivity.this, "install unKnown app  is enable now", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(Special permission) {
                Toast.makeText(PersonalActivity.this, "install unKnown app  is disable yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goApplicationSettings(View view) {
        SoulPermission.getInstance().goPermissionSettings();
    }

    public void getTopActivity(View view) {
        Activity activity = SoulPermission.getInstance().getTopActivity();
        if (null != activity) {
            Toast.makeText(activity, activity.getClass().getSimpleName() + " " + activity.hashCode(), Toast.LENGTH_SHORT).show();
        }
    }
}
