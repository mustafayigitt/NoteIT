package com.mustafayigit.noteit.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.mustafayigit.noteit.R;
import com.mustafayigit.noteit.db.entity.Note;
import com.mustafayigit.noteit.viewmodel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNoteFragment extends Fragment {

    private NoteViewModel noteViewModel;
    private Note newNote;
    Calendar myCalendar;

    public AddNoteFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root =  inflater.inflate(R.layout.fragment_add_note, container, false);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        final EditText txtNewNoteHeader = root.findViewById(R.id.txtNewNoteHeader);
        final EditText txtNewNoteContent = root.findViewById(R.id.txtNewNoteContent);
        final RatingBar ratingBar = root.findViewById(R.id.ratingBar);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }

        };

        Switch sw = root.findViewById(R.id.switchRemember);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                    dialog.show();


                }
            }
        });

        Button btnAdd = root.findViewById(R.id.btnNewNoteSave);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(txtNewNoteHeader.getText().toString().trim()) && !TextUtils.isEmpty(txtNewNoteContent.getText().toString().trim())) {
                    newNote = new Note(txtNewNoteHeader.getText().toString(), txtNewNoteContent.getText().toString(), (int) (ratingBar.getRating() * 2), getDateByFormat(myCalendar.getTime()), true);
                    noteViewModel.insert(newNote);
                    Snackbar snackbar = Snackbar.make(getView(), R.string.toast_message_saved, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    NavController navController = Navigation.findNavController(getView());
                    navController.navigate(R.id.action_navigation_add_note_to_navigation_home);
                } else {
                    Toast.makeText(v.getContext(),R.string.toast_message_warning , Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private String getDateByFormat(Date notFormattedDate) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(notFormattedDate);
    }

}
