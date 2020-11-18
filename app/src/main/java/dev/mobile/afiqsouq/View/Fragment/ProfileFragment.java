package dev.mobile.afiqsouq.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularimageview.CircularImageView;

import dev.mobile.afiqsouq.Models.PrefUserModel;
import dev.mobile.afiqsouq.Models.deliverzoneResp;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.View.Activities.EditProfileActivity;
import dev.mobile.afiqsouq.View.Activities.MyWallet;
import dev.mobile.afiqsouq.View.Activities.OldTransactions;
import dev.mobile.afiqsouq.View.Activities.RewardActivity;
import dev.mobile.afiqsouq.View.Activities.SettingsActivity;
import dev.mobile.afiqsouq.View.Activities.Sign_in;
import dev.mobile.afiqsouq.View.Activities.VouchersList;
import dev.mobile.afiqsouq.View.Activities.oldOrderList;
import dev.mobile.afiqsouq.View.Home_Activity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    //toobar & content
    TextView cartnumber, name, email, profile, wishlist, order, payment, address, gift, setting, logout;
    ImageView back, cart;
    CircularImageView profileImage;
    Context context;
    LinearLayout transactionsList , wallerContainer , rewardContainer ,settingsContainer , voucherContainer  ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        //toolbar
        context = view.getContext();

        profileImage = view.findViewById(R.id.circularImageView_myacc_profile_image);

        //content
        name = view.findViewById(R.id.textview_myacc_name);
        email = view.findViewById(R.id.textview_myacc_email);
        profile = view.findViewById(R.id.textview_myacc_myProfile);
        order = view.findViewById(R.id.textview_myacc_myOrder);
        transactionsList = view.findViewById(R.id.transactionList) ;
        payment = view.findViewById(R.id.textview_myacc_payment);
        wallerContainer = view.findViewById(R.id.walletContainer);
        logout = view.findViewById(R.id.textview_myacc_logout);
        rewardContainer = view.findViewById(R.id.rewardContainer) ;
        settingsContainer =view.findViewById(R.id.settingsContainer) ;
        voucherContainer =view.findViewById(R.id.giftCardContainer) ;

        voucherContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p = new Intent(getContext(), VouchersList.class) ;
                startActivity(p);
            }
        });


        settingsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p = new Intent(getContext(), SettingsActivity.class) ;
                startActivity(p);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getContext(), EditProfileActivity.class) ;
                startActivity(p);
            }
        });

        rewardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getContext(), RewardActivity.class);
                startActivity(p);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getContext()).logout();
                Intent p = new Intent(getContext(), Sign_in.class);
                startActivity(p);
                getActivity().finish();
            }
        });

        wallerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getContext(), MyWallet.class) ;
                startActivity(p);
            }
        });
        transactionsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getContext(), OldTransactions.class) ;
                startActivity(p);

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getContext(), oldOrderList.class) ;
                startActivity(p);

            }
        });
        // set user data
        PrefUserModel userModel = SharedPrefManager.getInstance(getContext()).getUser();
        name.setText(userModel.getName());
        email.setText(userModel.getMail());


        return view;

    }
    @Override
    public void onResume() {
        super.onResume();

        try {
            ((Home_Activity) getActivity()).setHeaderTitle("Profile");
        }
        catch (Exception e ){

        }
}
}