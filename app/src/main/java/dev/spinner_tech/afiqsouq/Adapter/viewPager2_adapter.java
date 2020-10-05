package dev.spinner_tech.afiqsouq.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import dev.spinner_tech.afiqsouq.View.Fragment.HomeFragment;
import dev.spinner_tech.afiqsouq.View.Fragment.ProfileFragment;
import dev.spinner_tech.afiqsouq.View.Fragment.SearchFragment;

public class viewPager2_adapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"Home", "Search" ,"Profile"};

    public viewPager2_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new ProfileFragment();

        }

        return new HomeFragment();
    }



    @Override
    public int getItemCount() {
        return 3;
    }
}
