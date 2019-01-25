package com.railway.ydhdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.railway.ydhdemo.App;
import com.railway.ydhdemo.R;
import com.railway.ydhdemo.callback.YuDialogInterface;
import com.railway.ydhdemo.utils.StringUtil;

public class YuDialog {
    /**
     * 正常情况
     */
    public final static int DIALOG_NORMAL = 1;
    /**
     * 内容是个编辑框
     */
    public final static int DIALOG_EDITE = 2;
    /**
     * 内容是个编辑框.只允许输入数字和小数点
     */
    public final static int DIALOG_EDITE_NUM = 3;

    public Dialog mHintDialog;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private TextView mMessage;
    private EditText mEditeText;
    private EditText mEditeNumber;
    private TextView mTitle;
    private View mLine;

    public YuDialog(Context mContext, Builder builder) {
        mHintDialog = new Dialog(mContext, R.style.HintDialog);
        mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog_hint, null);
        mTitle = (TextView) view.findViewById(R.id.title);
        mLine = view.findViewById(R.id.viewline);
        mMessage = (TextView) view.findViewById(R.id.message);
        mEditeText = view.findViewById(R.id.ettext);
        mEditeNumber = view.findViewById(R.id.etnumber);
        mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        mTvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        setView(builder);
        mHintDialog.setContentView(view);
        mHintDialog.show();
    }

    private void setView(final Builder builder) {
        mHintDialog.setCancelable(builder.isCancel);
        /**
         *标题
         */
        if (StringUtil.isNullOrEmpty(builder.title)) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(builder.title);
        }
        /**
         *根据不中间显示的内容进行划分
         * 正常显示文本
         * 编辑框
         * 编辑框只能输入数字和小数点
         */
        if (builder.type == DIALOG_NORMAL) {
            if (StringUtil.isNullOrEmpty(builder.message)) {
                mMessage.setVisibility(View.GONE);
            } else {
                mMessage.setVisibility(View.VISIBLE);
                mMessage.setText(builder.message);
            }
        } else if (builder.type == DIALOG_EDITE) {
            mEditeText.setVisibility(View.VISIBLE);
            mEditeText.setHint(builder.hint);
        } else if (builder.type == DIALOG_EDITE_NUM) {
            mEditeNumber.setVisibility(View.VISIBLE);
            mEditeNumber.setHint(builder.hint);
        }
        /**
         *确定按钮
         */
        if (StringUtil.isNullOrEmpty(builder.confirm)) {
            mTvConfirm.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
        } else {
            mTvConfirm.setVisibility(View.VISIBLE);
            mLine.setVisibility(View.VISIBLE);
            mTvConfirm.setText(builder.confirm);
        }
        /**
         *取消按钮
         */
        if (StringUtil.isNullOrEmpty(builder.cancel)) {
            mTvCancel.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
        } else {
            mTvCancel.setVisibility(View.VISIBLE);
            mLine.setVisibility(View.VISIBLE);
            mTvCancel.setText(builder.cancel);
        }
        /**
         *相关点击事件
         */
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mCancelListener != null)
                    builder.mCancelListener.callBack(v);
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mConfirmListener != null)
                    builder.mConfirmListener.callBack(v);
            }
        });
    }

    public static class Builder {
        private int type = DIALOG_NORMAL;//内容的类型
        private String title;
        /**
         * 正常中间是显示文本
         */
        private String message;
        /**
         * type是DIALOG_EDITE的时候中间是个编辑框，
         * 这个时候可以设置一个提示语
         */
        private String hint = App.getContext().getResources().getString(R.string.hint_default);
        /**
         * 是否可以取消dialog。false是不可取消.默认可以取消
         */
        private boolean isCancel = true;
        private String confirm;
        private String cancel;
        private YuDialogInterface mConfirmListener;
        private YuDialogInterface mCancelListener;

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder isCancel(boolean isCancel) {
            this.isCancel = isCancel;
            return this;
        }

        public Builder confirm(String confirm, YuDialogInterface mListener) {
            this.confirm = confirm;
            this.mConfirmListener = mListener;
            return this;
        }

        public Builder cancel(String cancel, YuDialogInterface mListener) {
            this.cancel = cancel;
            this.mCancelListener = mListener;
            return this;
        }

        public Builder confirm(String confirm) {
            this.confirm = confirm;
            return this;
        }

        public Builder cancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public YuDialog build(Context mContext) {
            return new YuDialog(mContext, this);
        }
    }
}
