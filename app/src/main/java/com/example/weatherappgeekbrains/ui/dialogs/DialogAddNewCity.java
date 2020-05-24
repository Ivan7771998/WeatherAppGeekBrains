package com.example.weatherappgeekbrains.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.weatherappgeekbrains.R;
import com.example.weatherappgeekbrains.adaters.AdapterListNameCity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class DialogAddNewCity extends DialogFragment {

    private AdapterListNameCity adapterListNameCity;

    public static DialogAddNewCity newInstance(AdapterListNameCity adapterListNameCity) {
        return new DialogAddNewCity(adapterListNameCity);
    }

    public DialogAddNewCity() {
    }

    private DialogAddNewCity(AdapterListNameCity adapterListNameCity) {
        this.adapterListNameCity = adapterListNameCity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.alert_add_city, null);
        MaterialButton btnAdd = view.findViewById(R.id.btn_add);
        MaterialButton btnCancel = view.findViewById(R.id.btn_cancel);
        TextInputEditText textCity = view.findViewById(R.id.textCity);
        builder.setView(view);
        btnAdd.setOnClickListener(v -> {
            if (!Objects.requireNonNull(textCity.getText()).toString().isEmpty()) {
                adapterListNameCity.addCity(textCity.getText().toString());
                dismiss();
            }
        });
        btnCancel.setOnClickListener(v -> {
            dismiss();
        });
        return builder.create();
    }
}
