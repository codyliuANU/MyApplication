package com.example.codyliu.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Calculator extends AppCompatActivity {
    TextView textView;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18;
    private StringBuffer str_show = new StringBuffer("");
    private boolean flag_dot = true;
    private boolean flag_minus = false;
    private String str_result = null;
    private boolean flag_num1 = false;
    private int scale = 2;
    private String str_oper = null;
    private BigDecimal num1, num2;


    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener((View.OnClickListener) this); //+
        findViewById(R.id.button2).setOnClickListener((View.OnClickListener) this);//-
        findViewById(R.id.button3).setOnClickListener((View.OnClickListener) this); //Clear
        findViewById(R.id.button4).setOnClickListener((View.OnClickListener) this);//*
        findViewById(R.id.button5).setOnClickListener((View.OnClickListener) this);//div
        findViewById(R.id.button6).setOnClickListener((View.OnClickListener) this);//sqrt
        findViewById(R.id.button7).setOnClickListener((View.OnClickListener) this);//7
        findViewById(R.id.button8).setOnClickListener((View.OnClickListener) this);//8
        findViewById(R.id.button9).setOnClickListener((View.OnClickListener) this);//9
        findViewById(R.id.button10).setOnClickListener((View.OnClickListener) this);//4
        findViewById(R.id.button11).setOnClickListener((View.OnClickListener) this);//5
        findViewById(R.id.button12).setOnClickListener((View.OnClickListener) this);//6
        findViewById(R.id.button13).setOnClickListener((View.OnClickListener) this);//1
        findViewById(R.id.button14).setOnClickListener((View.OnClickListener) this);//2
        findViewById(R.id.button15).setOnClickListener((View.OnClickListener) this);//3
        findViewById(R.id.button16).setOnClickListener((View.OnClickListener) this);//=
        findViewById(R.id.button17).setOnClickListener((View.OnClickListener) this);//0
        findViewById(R.id.button18).setOnClickListener((View.OnClickListener) this);//decimal


    }

    public void onClick(View v) {
        Button btn = (Button) v;
        switch (v.getId()) {
            case R.id.button18: //decimal button
                if (str_show.toString() == "") {
                    break;
                } else if (flag_dot) {
                    str_show.append(".");
                    showInTextView(str_show.toString());
                    flag_dot = false;
                }
                break;
            case R.id.button3: //Clear button
                if (!(str_show.toString() == "")) {
                    if (!flag_dot) {
                        String lastStr = String.valueOf(str_show.charAt(str_show
                                .length() - 1));
                        if (lastStr.equals(".")) {
                            flag_dot = true;
                        }
                    }
                    str_show.deleteCharAt(str_show.length() - 1);
                    if(str_show.toString().equals("")){
                        flag_minus = false;
                    }
                    showInTextView(str_show.toString());
                } else {
                    showInTextView("");
                    str_result = null;
                    str_show = new StringBuffer("");
                    flag_dot = true;
                    flag_minus = false;
                }
                flag_num1 = false;
                break;
            case R.id.button:
                setNum1(btn.getText().toString());
                break;
        case R.id.button2:
        if (!flag_minus) {
            if (str_show.toString().equals("")) {
                str_show.append("-");
                showInTextView(str_show.toString());
                flag_minus = true;
                break;
            }
        }
        setNum1(btn.getText().toString());
        break;
        case R.id.button4://button multiply
        setNum1(btn.getText().toString());
        break;
        case R.id.button5://button div
        setNum1(btn.getText().toString());
        break;
        case R.id.button16: //button equal
        if (str_oper == null || str_show.toString().equals("")
                || !flag_num1)
            break;
        calculate();
        break;
        default:
        str_show.append(btn.getText().toString());
        showInTextView(str_show.toString());
        break;
    }
}


    }

    private void showInTextView(String str){
        textView.setText(str);
    }
    private void setNum1(String oper) {
        if (str_oper != null && !str_show.toString().equals("") && flag_num1) {
            calculate();
        }
        str_oper = oper;
        if (!(str_show.toString() == "") && !str_show.toString().equals("-")) {
            num1 = new BigDecimal(str_show.toString());
            showInTextView(str_show.toString());
            str_show = new StringBuffer("");
            str_result = null;
            flag_num1 = true;
            flag_minus = false;
        } else if (str_result != null) {
            num1 = new BigDecimal(str_result);
            showInTextView(str_result);
            str_result = null;
            flag_num1 = true;
            flag_minus = false;
        }
        flag_dot = true;
    }

    void calculate(){
        if(str_show.toString().equals("-")) return;
        double result = 0;
        num2 = new BigDecimal(str_show.toString());
        if (str_oper.equals("+")) {
            result = Calculate.add(num1, num2);
        }
        if (str_oper.equals("-")) {
            result = Calculate.sub(num1, num2);
        }
        if (str_oper.equals("*")) {
            result = Calculate.mul(num1, num2);
        }
        if (str_oper.equals("/")) {
            if (!num2.equals(BigDecimal.ZERO)) {
                result = Calculate.div(num1, num2, scale);
            } else {
                Toast.makeText(Calculator.this, "The denominator cannot be 0！", Toast.LENGTH_LONG)
                        .show();
                showInTextView("");
                str_show = new StringBuffer("");
                str_oper = null;
                flag_num1 = false;
                flag_dot = true;
                return;
            }
        }
        str_result = String.valueOf(Calculate.round(result, scale));
        String[] resultStrings = str_result.split("\\.");
        if (resultStrings[1].equals("0")) {
            str_result = resultStrings[0];
        }
        showInTextView(str_result);
        str_show = new StringBuffer("");
        flag_dot = true;
        flag_num1 = false;
        str_oper = null;
        flag_minus = true;
    }

}
