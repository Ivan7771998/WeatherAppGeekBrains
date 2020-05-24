package com.example.weatherappgeekbrains.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.weatherappgeekbrains.R;

public class DialogAboutApp extends DialogFragment {

    public static DialogAboutApp newInstance(){
        return new DialogAboutApp();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(getString(R.string.title_about_app))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    dismiss();
                })
                .setMessage(getString(R.string.text_about_app));
        return builder.create();
    }
}
