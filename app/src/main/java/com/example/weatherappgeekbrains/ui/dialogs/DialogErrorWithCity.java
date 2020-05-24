package com.example.weatherappgeekbrains.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.ui.activities.MainActivity;

public class DialogErrorWithCity extends DialogFragment {

    public static DialogErrorWithCity newInstance(){
        return new DialogErrorWithCity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(getString(R.string.title_dialog_error))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    dismiss();
                    activity.callBackDialog();
                })
                .setMessage(getString(R.string.text_dialog_error));
        return builder.create();
    }
}
