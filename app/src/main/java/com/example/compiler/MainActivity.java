package com.example.compiler;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.resources.MaterialResources;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.AppCompatEditText;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.Switch;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements AccessoryView.IAccessoryView {

    Context cn;
    CodeEditText editText;
    AccessoryView accessoryView;
    Spinner dropdown;
    final String[] items = new String[]{"C", "C++", "Java", "Python"};
    Switch input;


    String language = "C", language_id;
    String strOutput = "";
    boolean outputFlag = false;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cn = this;
        Init();
        SetDropDown();
        SetUpEditText();


    }


    public void Init() {
        pd = new ProgressDialog(cn);
        input = findViewById(R.id.input);
        editText = findViewById(R.id.text_input_code);
        accessoryView = findViewById(R.id.accessoryView);
        accessoryView.setInterface(this);
        editText.setReadOnly(false);
        dropdown = findViewById(R.id.spinner);


        findViewById(R.id.clearall)
                .setOnClickListener(v -> Toast.makeText(cn, "" + editText.getCleanText(), Toast.LENGTH_SHORT).show());


        input.setOnCheckedChangeListener((view, checked) -> {
            if (checked) {
                OpenInputDialog();
            } else {
                outputFlag = false;
                strOutput = "";
            }
        });


        findViewById(R.id.btnRun).setOnClickListener(v -> {
            pd.setMessage("Please wait we are fetching your data");
            pd.show();
            NetworkCall();
        });


    }

    private void OpenInputDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(cn);

        final View dialogView = getLayoutInflater().inflate(R.layout.compiler_input, null);
        TextInputLayout output = dialogView.findViewById(R.id.output);


        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.ok).setOnClickListener(v -> {
            strOutput = output.getEditText().getText().toString();
            outputFlag = true;
            dialog.dismiss();
        });
        dialogView.findViewById(R.id.cancel).setOnClickListener(v -> {
            dialog.dismiss();
            outputFlag = false;
            strOutput = "";
        });
        dialog.setOnDismissListener(dialog1 -> {
            if (strOutput.equals("") && !outputFlag) {
                input.setChecked(false);
                strOutput = "";
            }

        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();

    }


    public void ShowOutPut(String str) {
        runOnUiThread(() -> {
            pd.dismiss();
            BottomSheetDialog mDialog = new BottomSheetDialog(cn);
            final View vv = getLayoutInflater().inflate(R.layout.compiler_output, null);
            TextView output = vv.findViewById(R.id.output);
            output.setText(str);
            mDialog.contentView(vv).heightParam(ViewGroup.LayoutParams.MATCH_PARENT)
                    .inDuration(500).cancelable(true).show();
        });
    }


    private void SetDropDown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        dropdown.setOnItemSelectedListener((parent, view, position, id) -> {
            SpannableString ss = new HighLightCode().setHighLight(CompilerConstant.GetLanguage(items[position]), items[position], cn);
            editText.setText(ss);
            Toast.makeText(cn, "Selected", Toast.LENGTH_SHORT).show();
            editText.setSelection(editText.getCleanText().length());
            language = items[position];
        });

    }

    private void NetworkCall() {
        switch (language) {
            case "C":
                language_id = "4";
                break;
            case "C++":
                language_id = "10";
                break;
            case "Java":
                language_id = "28";
                break;
            default:
                language_id = "34";
                break;
        }

        Thread thread = new Thread(() -> {
            try {


                String code = editText.getCleanText();
                String url = "https://api.judge0.com/submissions?wait=true";
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
                httppost.addHeader("Accept", "*/*");
                httppost.addHeader("X-Auth-Token", "f6583e60-b13b-4228-b554-2eb332ca64e7");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("source_code", code));
                nameValuePairs.add(new BasicNameValuePair("language_id", language_id));
                nameValuePairs.add(new BasicNameValuePair("stdin", strOutput));

                try {
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    try {
                        HttpResponse response = httpclient.execute(httppost);
                        String responseBody = EntityUtils.toString(response.getEntity());

                        Log.d("myapp", "response " + responseBody);
                        Log.d("myapp", "language_id " + language_id);


                        JSONObject jdata = new JSONObject(responseBody);


                        if (!language_id.equals("4") && !language_id.equals("10")) {
                            Log.d("myapp", "response 1");

                            if (!jdata.get("stdout").equals("") && !jdata.get("stdout").equals("null") && !jdata.get("stdout").equals(null)) {
                                ShowOutPut(jdata.get("stdout").toString());
                            } else if (!jdata.get("stderr").equals("") && !jdata.get("stderr").equals("null") && !jdata.get("stderr").equals(null)) {
                                SpannableString spannableString = new SpannableString(jdata.get("stderr").toString());
                                spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, (jdata.get("stderr").toString().length() - 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                ShowOutPut(spannableString.toString());
                            } else {
                                ShowOutPut(responseBody.toString());
                            }

                        } else {
                            Log.d("myapp", "response 2");
                            if (!jdata.get("stdout").equals("") && !jdata.get("stdout").equals("null") && !jdata.get("stdout").equals(null)) {
                                ShowOutPut(jdata.get("stdout").toString());
                            } else if (!jdata.get("compile_output").equals("") && !jdata.get("compile_output").equals("null") && !jdata.get("compile_output").equals(null)) {
                                Log.d("myapp", "response 3");
                                SpannableString spannableString = new SpannableString(jdata.get("compile_output").toString());
                                spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, (jdata.get("compile_output").toString().length() - 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                ShowOutPut(spannableString.toString());
                            } else {
                                ShowOutPut(responseBody.toString());
                            }

                        }


                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        ShowOutPut(e.toString());
                        ;
                    } catch (IOException e) {
                        e.printStackTrace();
                        ;
                        ShowOutPut(e.toString());
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    ShowOutPut(e.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
                ShowOutPut(e.toString());
            }
        });
        thread.start();
    }

    private void SetUpEditText() {

        SpannableString ss = new HighLightCode().setHighLight(CompilerConstant.GetLanguage("C"), "C", cn);
        editText.setText(ss);


        editText.setSelection(editText.getCleanText().length());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int start, int count, int after) {


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.d("DBG", "afterTextChanged");
                editText.removeTextChangedListener(this);
                String str = editText.getText().toString();


                Log.d("IDE", "afterTextChanged: " + language);
                int po = editText.getSelectionStart();
                SpannableString ss = new HighLightCode().setHighLight(str, language, cn);
                editText.setText(ss);

                //get cursor
                editText.setSelection(po);//set cursor
                editText.addTextChangedListener(this);


            }

        });
    }

    @Override
    public void onButtonAccessoryViewClicked(String str) {
        editText.getText().insert(editText.getSelectionStart(), str);
    }


}