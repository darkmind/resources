package com.example.asm.resources;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;


public class full_char_list extends FragmentActivity {
    View gifts;
    View spheres;
    View disciplines;
    Spinner spinner_gd;
    ArrayAdapter<CharSequence> adapter_gd;

    private String[] discs = { "dis_animalism", "dis_auspex", "dis_celerity", "dis_chimerstry",
            "dis_daimoinon", "dis_dementation", "dis_dominate", "dis_fortitude", "dis_melpominee",
            "dis_obfuscate", "dis_obtenebration", "dis_potence", "dis_presence", "dis_protean",
            "dis_quietus", "dis_serpentis", "dis_temporis", "dis_thanatosis", "dis_vicissitude",
            "dis_alchemistry", "dis_conveyance", "dis_enchantment", "dis_healing", "dis_hellfire",
            "dis_weathercraft" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_swipe);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter());

        gifts       = findViewById(R.id.gifts);
        spheres     = findViewById(R.id.spheres);
        disciplines = findViewById(R.id.disciplines);

        spinner_gd = (Spinner) findViewById(R.id.charlist_gods);
        adapter_gd = ArrayAdapter.createFromResource(this,
                R.array.gods_array, android.R.layout.simple_spinner_item);
        adapter_gd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gd.setAdapter(adapter_gd);
        spinner_gd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String c_god = spinner_gd.getSelectedItem().toString();
                show_god_disciplines( c_god );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        final Spinner spinner_al = (Spinner) findViewById(R.id.charlist_alignment);
        final ArrayAdapter<CharSequence> adapter_al = ArrayAdapter.createFromResource(this,
                R.array.alignments_array, android.R.layout.simple_spinner_item);
        adapter_al.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_al.setAdapter(adapter_al);
        spinner_al.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                fix_gods_list( spinner_al.getSelectedItem().toString() );

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        final Spinner spinner_cl = (Spinner) findViewById(R.id.charlist_class);
        final ArrayAdapter<CharSequence> adapter_cl = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter_cl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cl.setAdapter(adapter_cl);
        spinner_cl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String c_class = spinner_cl.getSelectedItem().toString();
                if( c_class.equals("Воин") ) {
                    gifts.setVisibility(View.VISIBLE);
                    spheres.setVisibility(View.GONE);
                    disciplines.setVisibility(View.GONE);
                    spinner_gd.setVisibility(View.GONE);
                }
                else if( c_class.equals("Маг") ) {
                    gifts.setVisibility(View.GONE);
                    spheres.setVisibility(View.VISIBLE);
                    disciplines.setVisibility(View.GONE);
                    spinner_gd.setVisibility(View.GONE);
                }
                else if( c_class.equals("Жрец") ) {
                    gifts.setVisibility(View.GONE);
                    spheres.setVisibility(View.GONE);
                    disciplines.setVisibility(View.VISIBLE);
                    spinner_gd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }


    public void radio_button_clicked(View view) {
        String IdAsString = view.getResources().getResourceName(view.getId());
        Pattern p = Pattern.compile("_(\\p{Lower}+)_(\\p{Lower}+)(\\d+)$");
        Matcher m = p.matcher(IdAsString);
        String group;
        String name;
        Integer number;
        if( m.find() ){
            group = m.group(1);
            name = m.group(2);
            number = Integer.parseInt(m.group(3));
        }
        else {
            throw new RuntimeException( "Can't fine resource and value in " + IdAsString );
        }

        if( save_new_values( group, name, number )) {
            set_range( group, name, number );
            unset_upper_range( group, name, number );
        }
    }

    private boolean save_new_values( String group, String name, Integer number ) {
        return true;
    }

    private void set_range( String group, String name, Integer number ) {
        for ( Integer i = 1; i <= number; i++ ){
            mark_dots( group, name, i, R.drawable.ic_fiber_manual_record_black_24dp );
        }
    }

    private void unset_upper_range( String group, String name, Integer number ) {
        for ( Integer i = (number + 1); i <= 5; i++ ){
            mark_dots( group, name, i, R.drawable.ic_radio_button_unchecked_black_24dp );
        }
    }

    private void mark_dots( String group, String name, Integer number, int pic ) {
        String id_ref = String.format(Locale.getDefault(), "rb_%s_%s%d", group,  name, number);
        int resID = getResources().getIdentifier(id_ref, "id", getPackageName());
        ImageView img = (ImageView) findViewById(resID);
        assert img != null;
        img.setImageResource(pic);
    }

    private void fix_gods_list( String algmnt ) {
        if( algmnt.equals("Законопослушный-Добрый") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_lg_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Нейтральный-Добрый") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ng_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Хаотичный-Добрый") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_cg_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Законопослушный-Нейтральный") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ln_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Нейтральный") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_nn_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Хаотичный-Нейтральный") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_cn_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Законопослушный-Злой") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_le_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Нейтральный-Злой") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ne_array, android.R.layout.simple_spinner_item);
        }
        else if( algmnt.equals("Хаотичный-Злой") ) {
            adapter_gd = ArrayAdapter.createFromResource(this,
                    R.array.gods_ce_array, android.R.layout.simple_spinner_item);
        }
        adapter_gd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gd.setAdapter(adapter_gd);
    }

    private void show_god_disciplines( String god_name ) {
        for (String d: discs) {
            int resID = getResources().getIdentifier(d, "id", getPackageName());
            View disc = findViewById(resID);
            disc.setVisibility(View.GONE);
        }

        List<String> g_discs = new ArrayList<String>();
        if( god_name.equals("Акади") ) {
            g_discs.add( "dis_celerity" );
            g_discs.add( "dis_conveyance" );
            g_discs.add( "dis_weathercraft" );
        }
        else if( god_name.equals("Грумбар") ) {
            g_discs.add( "dis_fortitude" );
            g_discs.add( "dis_temporis" );
        }
        else if( god_name.equals("Истишия") ) {
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_alchemistry" );
            g_discs.add( "dis_melpominee" );
        }
        else if( god_name.equals("Келемвор") ) {
            g_discs.add( "dis_conveyance" );
            g_discs.add( "dis_auspex" );
            g_discs.add( "dis_temporis" );
        }
        else if( god_name.equals("Коссут") ) {
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_hellfire" );
            g_discs.add( "dis_dominate" );
        }
        else if( god_name.equals("Латандер") ) {
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_presence" );
        }
        else if( god_name.equals("Мистра") ) {
            g_discs.add( "dis_hellfire" );
            g_discs.add( "dis_alchemistry" );
            g_discs.add( "dis_enchantment" );
        }
        else if( god_name.equals("Огма") ) {
            g_discs.add( "dis_conveyance" );
            g_discs.add( "dis_alchemistry" );
            g_discs.add( "dis_enchantment" );
        }
        else if( god_name.equals("Сайрик") ) {
            g_discs.add( "dis_dominate" );
            g_discs.add( "dis_chimerstry" );
            g_discs.add( "dis_dementation" );
        }
        else if( god_name.equals("Суне") ) {
            g_discs.add( "dis_presence" );
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_celerity" );
        }
        else if( god_name.equals("Сильванус") ) {
            g_discs.add( "dis_animalism" );
            g_discs.add( "dis_protean" );
            g_discs.add( "dis_serpentis" );
        }
        else if( god_name.equals("Талос") ) {
            g_discs.add( "dis_hellfire" );
            g_discs.add( "dis_weathercraft" );
            g_discs.add( "dis_dominate" );
        }
        else if( god_name.equals("Темпус") ) {
            g_discs.add( "dis_potence" );
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_fortitude" );
        }
        else if( god_name.equals("Тир") ) {
            g_discs.add( "dis_auspex" );
            g_discs.add( "dis_fortitude" );
        }
        else if( god_name.equals("Чонти") ) {
            g_discs.add( "dis_weathercraft" );
            g_discs.add( "dis_healing" );
            g_discs.add( "dis_animalism" );
        }
        else if( god_name.equals("Шар") ) {
            g_discs.add( "dis_dominate" );
            g_discs.add( "dis_obtenebration" );
            g_discs.add( "dis_obfuscate" );
        }
        else if( god_name.equals("Бэйн") ) {
            g_discs.add( "dis_quietus" );
            g_discs.add( "dis_dominate" );
            g_discs.add( "dis_daimoinon" );
        }


        for( String d: g_discs ) {
            int resID = getResources().getIdentifier(d, "id", getPackageName());
            View disc = findViewById(resID);
            disc.setVisibility(View.VISIBLE);
        }
    }


    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;

        public SampleFragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.create(position + 1);
        }
    }


    public static class PageFragment extends Fragment {
        public static final String ARG_PAGE = "ARG_PAGE";

        private int mPage;

        public static PageFragment create(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            PageFragment fragment = new PageFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

}
