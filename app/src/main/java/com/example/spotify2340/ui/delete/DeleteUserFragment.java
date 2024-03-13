package com.example.spotify2340.ui.delete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotify2340.R;
import com.example.spotify2340.model.AppDatabase;
import com.example.spotify2340.model.dao.UserDao;
import com.example.spotify2340.model.obj.User;


public class DeleteUserFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button deleteUserButton;

    private User u;
   // public Button deleteUserButton() {
   //     return isDeleted;
   // }

    // references userDao
    private UserDao udao;

    // current database
    static private AppDatabase db;

    // db irrelevant
    // static private AppDatabase dbirr;
    public DeleteUserFragment(AppDatabase db) {
        // Required empty public constructor
        // instantiate current user
        u = db.userDao().getCurrUser();
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DeleteUserFragment.
//     */
//    public static DeleteUserFragment newInstance(String param1, String param2) {
//        DeleteUserFragment fragment = new DeleteUserFragment(dbirr);
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // created deleteUserButton
        deleteUserButton = getView().findViewById(R.id.deleteUserButton);

        deleteUserButton.setOnClickListener(view1 -> {
            //Users.deleteUser(); database call to actually set the user
            udao.Delete(u);
            // Navigate to the Home page / login screen
            NavHostFragment.findNavController(DeleteUserFragment.this).navigate(R.id.nav_home);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_user, container, false);
    }
}