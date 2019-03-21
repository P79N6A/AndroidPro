package com.ogmallpad.constant;

import com.ogmallpad.R;
import com.ogmallpad.ui.fragment.BaginFragment;
import com.ogmallpad.ui.fragment.BrandlookTabFragment;
import com.ogmallpad.ui.fragment.HomeOneFragment;
import com.ogmallpad.ui.fragment.MineFragment;
import com.ogmallpad.ui.fragment.PlanFragment;
import com.ogmallpad.ui.fragment.PlanTabFragment;
import com.ogmallpad.ui.fragment.ProductlookFragment;

/**
 * Tab常量值
 * Created by chenjunshan on 2016/8/31.
 */

public class TabConstant {
    public static Class MAIN_FRAGMENT[] = {HomeOneFragment.class, BrandlookTabFragment.class, ProductlookFragment.class, PlanTabFragment.class, BaginFragment.class, MineFragment.class};
    public static int MAIN_IAMGEVIEW[] = {R.drawable.main_tab1_selector, R.drawable.main_tab2_selector, R.drawable.main_tab3_selector, R.drawable.main_tab4_selector, R.drawable.main_tab5_selector, R.drawable.main_tab6_selector};
    public static int MAIN_TEXTVIEW[] = {R.string.str_sy, R.string.str_pp, R.string.str_sp, R.string.str_fa, R.string.str_lbrz, R.string.str_wd};
    /*public static Class MAIN_FRAGMENT[] = {HomeFragment.class, BrandFragment.class, ProductFragment.class, PlanFragment.class, MineFragment.class};
    public static int MAIN_IAMGEVIEW[] = {R.drawable.main_tab1_selector, R.drawable.main_tab2_selector, R.drawable.main_tab3_selector, R.drawable.main_tab4_selector, R.drawable.main_tab6_selector};
    public static int MAIN_TEXTVIEW[] = {R.string.str_sy, R.string.str_pp, R.string.str_sp, R.string.str_fa, R.string.str_wd};*/
}
