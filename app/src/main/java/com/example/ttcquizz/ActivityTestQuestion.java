package com.example.ttcquizz;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcquizz.adapter.AdapterQuestionBottomSheet;
import com.example.ttcquizz.model.ItemBottomSheet;
import com.example.ttcquizz.modeltest.Answer;
import com.example.ttcquizz.modeltest.Example;
import com.example.ttcquizz.modeltest.Question;
import com.example.ttcquizz.modeltest.QuestionTestEntityList;
import com.example.ttcquizz.modeltest.Exam;
import com.example.ttcquizz.remote.RetrofitClientInstance;
import com.example.ttcquizz.response.BaseResponse;
import com.example.ttcquizz.response.ResultResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class ActivityTestQuestion extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<ItemBottomSheet> listBottomSheet = new ArrayList<>();
    private ArrayList<Question> listQuestions = new ArrayList<>();
    private ArrayList<Exam> listExam = new ArrayList<>();
    private List<QuestionTestEntityList> questionTestEntityList;
    private List<Answer> listAnswer = new ArrayList<>();
    private AdapterQuestionBottomSheet adapterQuestionBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private ToggleButton tbUpDown;
    private ConstraintLayout constraintLayoutSheet, consQuestionEssay;
    private RecyclerView rcvListQuestion;
    private RadioGroup radioGroup;
    private RadioButton raAnswer1, raAnswer2, raAnswer3, raAnswer4;
    private TextView tvQuestion, tvTime, tvSoThuTu;
    private EditText edtAnswer;
    private ImageView ivNext, ivPre, ivImageCamera, ivCamera;
    private Button btnClose, btnSubmit;
    private HashMap<Integer, String> hashMapRadioButton = new HashMap<>();
    private HashMap<Integer, String> hashMapEdittextAnswer = new HashMap<>();
    private HashMap<Integer, String> hashMapImageCamera = new HashMap<>();
    private HashMap<Integer, String> hashMap = new HashMap<>();
    private CountDownTimer countDownTimer;
    private Integer currentQuestion = 0;
    private String contentEdittext;
    private Dialog dialogSubmitSuccess;
    private static final String TAG = "TAG";
    private int idExam;
    private boolean isEdittextDone = false;
    private boolean checkIsEdittext = false;
    private boolean checkTakeCaputrue = false;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initGetId();
        callApiExam();
        initView();
        ExpanseCollapseBottomSheet();
        UpDownBottomSheet();
        initEvent();

    }

    private void initGetId() {
        Intent intent = getIntent();
        idExam = intent.getIntExtra(Constants.QUIZ_ID, -1);
    }

    private void initView() {
        consQuestionEssay = findViewById(R.id.cons_question_essay);
        rcvListQuestion = findViewById(R.id.rcv_list_question);
        constraintLayoutSheet = findViewById(R.id.bottomSheet);
        ivCamera = findViewById(R.id.iv_camera);
        ivImageCamera = findViewById(R.id.iv_image_camera);
        ivPre = findViewById(R.id.iv_pre);
        ivNext = findViewById(R.id.iv_next);
        tbUpDown = findViewById(R.id.toggleButton);
        tvQuestion = findViewById(R.id.tv_question);
        tvSoThuTu = findViewById(R.id.tv_thu_tu_cau);
        tvTime = findViewById(R.id.tv_time);
        edtAnswer = findViewById(R.id.edt_answer_text);
        radioGroup = findViewById(R.id.radio_group);
        raAnswer1 = findViewById(R.id.ra_answer1);
        raAnswer2 = findViewById(R.id.ra_answer2);
        raAnswer3 = findViewById(R.id.ra_answer3);
        raAnswer4 = findViewById(R.id.ra_answer4);
        btnSubmit = findViewById(R.id.btn_submit_anwser);
        bottomSheetBehavior = BottomSheetBehavior.from(constraintLayoutSheet);
    }

    private void initEvent() {
        raAnswer1.setOnClickListener(this);
        raAnswer2.setOnClickListener(this);
        raAnswer3.setOnClickListener(this);
        raAnswer4.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        ivPre.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String answer = null;
        boolean ischeckbutton = false;
        boolean checkEdiitexInHashmap = false;
        int id = listQuestions.get(currentQuestion).getId();
        switch (v.getId()) {
            case R.id.ra_answer1:
                answer = "a";
                ischeckbutton = true;
                raAnswer1.setBackgroundResource(R.drawable.radio_selected);
                hashMap.put(listQuestions.get(currentQuestion).getId(), "a");
                break;
            case R.id.ra_answer2:
                answer = "b";
                ischeckbutton = true;
                raAnswer2.setBackgroundResource(R.drawable.radio_selected);
                hashMap.put(listQuestions.get(currentQuestion).getId(), "b");
                break;
            case R.id.ra_answer3:
                answer = "c";
                ischeckbutton = true;
                raAnswer3.setBackgroundResource(R.drawable.radio_selected);
                hashMap.put(listQuestions.get(currentQuestion).getId(), "c");
                break;
            case R.id.ra_answer4:
                answer = "d";
                ischeckbutton = true;
                raAnswer4.setBackgroundResource(R.drawable.radio_selected);
                hashMap.put(listQuestions.get(currentQuestion).getId(), "d");
                break;
            case R.id.iv_camera:
                checkCamera();
                checkIsEdittext = false;
                break;
            case R.id.btn_submit_anwser:
                showConfirmSubmitDialog("Are you sure to submit test?");
                break;
            case R.id.iv_next:
                if (isEdittextDone && checkIsEdittext && !contentEdittext.isEmpty()) {
                    checkEdiitexInHashmap = true;
                    hashMap.put(listQuestions.get(currentQuestion).getId(), contentEdittext);
                }
                if (ischeckbutton || checkEdiitexInHashmap || checkTakeCaputrue) {
                    adapterQuestionBottomSheet.setPositionAnswer(currentQuestion);
                }
                nextQuestion();
                break;
            case R.id.iv_pre:
                if (isEdittextDone && checkIsEdittext && !contentEdittext.isEmpty()) {
                    checkEdiitexInHashmap = true;
                    hashMap.put(listQuestions.get(currentQuestion).getId(), contentEdittext);
                }
                if (ischeckbutton || checkEdiitexInHashmap || checkTakeCaputrue) {
                    adapterQuestionBottomSheet.setPositionAnswer(currentQuestion);
                }
                preQuestion();
                break;
        }
        if (ischeckbutton) {
            hashMap.put(id, answer);
            adapterQuestionBottomSheet.setPositionAnswer(currentQuestion);
        }
    }

    private void nextQuestion() {
        if (currentQuestion < listQuestions.size() - 1) {
            currentQuestion++;
            initSetData(listQuestions.get(currentQuestion), currentQuestion);
        }
    }

    private void preQuestion() {
        if (currentQuestion > 0) {
            currentQuestion--;
            initSetData(listQuestions.get(currentQuestion), currentQuestion);
        }
    }

    private void initSetData(Question question, int index) {
        int id = listQuestions.get(currentQuestion).getId();
        tvSoThuTu.setText((index + 1) + "/" + listQuestions.size());
        tvQuestion.setText((question.getQuestion()));

        if (question.getQuestionType().equals("TN")) {
            hideSoftKeyboard();
            checkIsEdittext = false;
            radioGroup.setVisibility(View.VISIBLE);

            // clear check để không bị check đồng loạt tất cả các câu
            radioGroup.clearCheck();
            raAnswer1.setText(question.getA());
            raAnswer2.setText(question.getB());
            raAnswer3.setText(question.getC());
            raAnswer4.setText(question.getD());

            // Phần tự luận ẩn
            consQuestionEssay.setVisibility(GONE);

            // xử lý lưu lại check đã chọn sau khi next/lùi câu
            saveButtonRadio(id);
        }
        if (question.getQuestionType().equals("TL")) {
            checkIsEdittext = true;

            //phần tự luận hiện
            consQuestionEssay.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(GONE);

            //clear ảnh để không bị hiện ảnh đồng loạt tất cả các bài thi
            ivImageCamera.setImageResource(0);
            saveImageCamera(id);
            textChangeListener();
            saveContentEdittextAnswer(id);
        }
    }

    private void saveContentEdittextAnswer(int id) {
        if (hashMap.size() != 0) {
            if (hashMap.get(id) != null) {
                edtAnswer.setText(hashMap.get(id));
            }
        }
    }

    private void textChangeListener() {
        edtAnswer.setText("");
        edtAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                contentEdittext = s.toString();
                isEdittextDone = true;
            }
        });
    }

    private void saveButtonRadio(int id) {
        if (hashMap.size() != 0) {
            String answer = "";
            if (hashMap.get(id) != null) {
                answer = hashMap.get(id);
            }
            switch (answer) {
                case "a":
                    raAnswer1.setChecked(true);
                    break;
                case "b":
                    raAnswer2.setChecked(true);
                    break;
                case "c":
                    raAnswer3.setChecked(true);
                    break;
                case "d":
                    raAnswer4.setChecked(true);
                    break;
            }
        }
    }

    private void saveImageCamera(int id) {
        String base64Image;
        if (hashMap.size() != 0) {
            if (hashMap.get(id) != null) {
                base64Image = hashMap.get(id);
                ivImageCamera.setImageBitmap(ImageUtil.convert(base64Image));
            }
        }
    }

    private void checkCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, Constants.PERMISSION_CODE);
            } else {
                openCamera();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera();
                } else {
                    // permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void openCamera() {
        //camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constants.IMAGE_CAPTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //called when image was captured from camera
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            int id = listQuestions.get(currentQuestion).getId();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivImageCamera.setImageBitmap(imageBitmap);
            hashMap.put(id, ImageUtil.convert(imageBitmap));
            Log.e(TAG, "hashmap" + hashMap);
            checkTakeCaputrue = true;

        }
    }

    private void UpDownBottomSheet() {
        tbUpDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    tbUpDown.setBackgroundDrawable(getDrawable(R.drawable.bottomsheetdown));
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    tbUpDown.setBackgroundDrawable(getDrawable(R.drawable.bottomsheet));
                }
            }
        });
    }

    private void ExpanseCollapseBottomSheet() {
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // mở rộng bottom sheet
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tbUpDown.setChecked(true);
                    // thu hẹp bottom sheet
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tbUpDown.setChecked(false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void callApiExam() {
        RetrofitClientInstance.getMyService(this).getListExam().enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    List<Exam> exam = response.body().getExam();
                    listExam.addAll(exam);
                    getListQuestion();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    private void getListQuestion() {
        for (int i = 0; i < listExam.size(); i++) {
            Exam exam = listExam.get(i);
            if (exam.getId() == idExam) {
                tvTime.setText("" + exam.getTestTime());
                initTimeDown(exam.getTestTime() * 1000 * 60);
                questionTestEntityList = exam.getQuestionTestEntityList();
                for (int j = 0; j < questionTestEntityList.size(); j++) {
                    Question question = questionTestEntityList.get(j).getQuestion();
                    listQuestions.add(question);
                }
            }
        }
        initSetData(listQuestions.get(currentQuestion), currentQuestion);
        initDataBottomSheet(listQuestions.size());
    }

    private void postAnswer() {

        List<Integer> keyList = new ArrayList(hashMap.keySet());
        List<String> valueList = new ArrayList(hashMap.values());

        for (int i = 0; i < keyList.size(); i++) {
            listAnswer.add(new Answer(keyList.get(i), valueList.get(i)));
        }

        RetrofitClientInstance.getMyService(this).sendAnswer(idExam,     ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse res = response.body();
                    if (!res.isStatus()) {
                        RetrofitClientInstance.getMyService(ActivityTestQuestion.this).getResult(idExam).enqueue(new Callback<ResultResponse>() {
                            @Override
                            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                                if (response.isSuccessful()) {
                                    ResultResponse result = response.body();
                                    String message = result.getMessage();
                                    if (result.isStatus()) {
                                        String score = result.getData().getScoreMultipleChoice();
                                        showScoreDialog("true", message, score);
                                    } else {
                                        showScoreDialog("false", message, null);
                                    }
                                } else {
                                    showScoreDialog("false", "Something wrong", null);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultResponse> call, Throwable t) {
                                showScoreDialog("false", "Something wrong", null);
                            }
                        });
                    } else {
                        showScoreDialog("false", "Something wrong", null);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                showScoreDialog("false", "Something wrong", null);
            }
        });
    }

    private void initTimeDown(int timeDown) {
        countDownTimer = new CountDownTimer(timeDown, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tvTime.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                postAnswer();
            }
        }.start();
    }

    private void initDataBottomSheet(int numberQuestion) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ActivityTestQuestion.this, 5);
        rcvListQuestion.setLayoutManager(gridLayoutManager);
        List<ItemBottomSheet> listItemBottomSheet = new ArrayList<>();
        for (int i = 0; i < numberQuestion; i++) {
            listItemBottomSheet.add(new ItemBottomSheet(i + 1, false));
        }
        adapterQuestionBottomSheet = new AdapterQuestionBottomSheet(ActivityTestQuestion.this, listItemBottomSheet, new AdapterQuestionBottomSheet.onClickItemBottomSheet() {
            @Override
            public void onClickItemBottomSheet(int position) {
                currentQuestion = position;
                initSetData(listQuestions.get(position), position);
            }
        });
        rcvListQuestion.setAdapter(adapterQuestionBottomSheet);
    }

    private void showConfirmSubmitDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ConfirmDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, findViewById(R.id.dg_confirm));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        ((TextView) view.findViewById(R.id.txt_message)).setText(content);
        view.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                postAnswer();
            }
        });
        view.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showScoreDialog(String notify, String message, String score) {
        Button btn_score;
        TextView score_1, score_2;
        ImageView status;

        dialog = new Dialog(ActivityTestQuestion.this);
        dialog.setContentView(R.layout.dialog_score);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimationUpDown;

        btn_score = dialog.findViewById(R.id.btn_score);
        score_1 = dialog.findViewById(R.id.txt_score_1);
        score_2 = dialog.findViewById(R.id.txt_score_2);
        status = dialog.findViewById(R.id.ic_score);

        score_1.setText(message);
        score_2.setText(score);

        if (notify.equals("true")) {
            status.setImageResource(R.drawable.high_score);
            score_1.setTextColor(getColor(R.color.green));
            btn_score.setText("OK");
            btn_score.setBackground(getDrawable(R.drawable.button_custom_dialog_high));
            btn_score.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(ActivityTestQuestion.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (notify.equals("false")) {
            status.setImageResource(R.drawable.low_score);
            score_1.setTextColor(getColor(R.color.red));
            btn_score.setText("Try again!");
            btn_score.setBackground(getDrawable(R.drawable.button_custom_dialog_low));
            btn_score.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }


    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}