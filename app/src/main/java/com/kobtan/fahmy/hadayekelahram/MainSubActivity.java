package com.kobtan.fahmy.hadayekelahram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class MainSubActivity extends AppCompatActivity {

    private TextView text_e3ln ;
    private GridView gridview;
    private ArrayList<String> osNameList = new ArrayList<>() ;
    private ArrayList<Integer> osImages = new ArrayList<>() ;


    private ArrayList<String> imagesIds = new ArrayList<>() ;

    private TextView title ;
    private Typeface typeface ;
    private CustomAdabterFinal customAdabter ;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<String> imagesView  = new ArrayList<>();
    private String newString ;
    private int newStringTwo ;
    final Handler handler = new Handler();
    public Timer swipeTimer ;
    private DatabaseReference mDatabase ;

    private ArrayList<String> kind = new ArrayList<>();
    private int test_modules ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub);
       // getWindow().getAttributes().windowAnimations = R.style.Fade;
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setTitle(" ");

        /* AlertDialog.Builder builder1 = new AlertDialog.Builder(MainSubActivity.this);
        builder1.setMessage("انت غير متصل بالانترنت");
        builder1.setIcon(R.drawable.logo);
        builder1.setCancelable(false);

       builder1.setPositiveButton(
                "اعد المحاولة",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "خروج",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
*/

        // zogag w mrayat
        //

        backky();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newStringTwo= 0;
            } else {
                newStringTwo= extras.getInt("STRING_I_NEED_TWO");
            }
        } else {
            newStringTwo= (int) savedInstanceState.getSerializable("STRING_I_NEED_TWO");
        }

        typeface = Typeface.createFromAsset(getAssets() , "font.ttf");

        title = (TextView) findViewById(R.id.titlee) ;
        title.setTypeface(typeface);
        if (newString != null) {
            title.setText(newString);
        }


        init("all");

        getData("all");


        gridview = (GridView) findViewById(R.id.customgrid);



        getItemsAll();

        text_e3ln = findViewById(R.id.text_e3ln) ;
        text_e3ln.setSelected(true);


    }




    private void  getItemsAll()   {

        if (newString != null) {
            if (newString.equals("تسوق")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("سوبر ماركت");
                osNameList.add("خضار و فاكهة");
                osNameList.add("حلويات");
                osNameList.add("محمصات و عصائر");
                osNameList.add("جزار");
                osNameList.add("دواجن و طيور");
                osNameList.add("عطاره");
                osNameList.add("دراي كلين");
                osNameList.add("ادوات منزلية");
                osNameList.add("منظفات"); // جيم و رياضة

                osNameList.add("محلات 2,5") ;
                osNameList.add("مكتبات");
                //
                osImages.add(R.drawable.tswk_op);
                osImages.add(R.drawable.khodar_op);
                osImages.add(R.drawable.hlwyat_frr);
                osImages.add(R.drawable.mohmsat);
                osImages.add(R.drawable.lhoom_frr);
                osImages.add(R.drawable.frakh_op);
                osImages.add(R.drawable.etara_op);
                osImages.add(R.drawable.drayclean_fr) ;
                osImages.add(R.drawable.adwat_fr);
                osImages.add(R.drawable.monzfat_fr);

                osImages.add(R.drawable.mhlat_fr) ;
                osImages.add(R.drawable.mktba_op);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

                //
            }
            else if (newString.equals("مطاعم")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("مشويات");
                osNameList.add("اسماك");
                osNameList.add("وجبات سريعة");
                osNameList.add("بيتزا");
                osNameList.add("كريب");
                osNameList.add("كبدة وسجق");
                osNameList.add("مطاعم سورية");
                osNameList.add("كشري");
                osNameList.add("اكل بيتي");
                //
                osImages.add(R.drawable.mshawy_fr);
                osImages.add(R.drawable.asmak_fr);
                osImages.add(R.drawable.wgbatsr_fr);
                osImages.add(R.drawable.pizza_fr);
                osImages.add(R.drawable.crep_fr);
                osImages.add(R.drawable.kbda_fr);
                osImages.add(R.drawable.sory_fr) ;
                osImages.add(R.drawable.koshry_fr);
                osImages.add(R.drawable.akl_fr);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else if (newString.equals("مستلزمات اطفال")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("ملابس اطفال");
                osNameList.add("لعب اطفال");

                //
                osImages.add(R.drawable.mlabsatfal);
                osImages.add(R.drawable.leaab);

                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            else if (newString.equals("مستشفيات")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("مستشفيات");
                osNameList.add("عيادات و مراكز طبية");
                osNameList.add("مركز تخاطب");
                osNameList.add("دار رعاية");
                osNameList.add("مستشفي نفسي");
                osNameList.add("مستشفي بيطري");
                //
                osImages.add(R.drawable.mostshfa_fr);
                osImages.add(R.drawable.khasa);
                osImages.add(R.drawable.enotk);
                osImages.add(R.drawable.dar);
                osImages.add(R.drawable.nfsy);
                osImages.add(R.drawable.animalhos);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else if (newString.equals("ترفيهي")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("العاب اطفال");
                osNameList.add("Playstation");
                osNameList.add("ملاعب كرة");


                //

                osImages.add(R.drawable.football);
                osImages.add(R.drawable.playstationvv);
                osImages.add(R.drawable.football);

                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            else if (newString.equals("دكتور خاص")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("اسنان");
                osNameList.add("عيون");
                osNameList.add("جلدية");
                osNameList.add("باطنة");
                osNameList.add("اطفال حديث الولادة");
                osNameList.add("مخ و أعصاب");
                osNameList.add("انف وأذن");
                osNameList.add("عظام");
                osNameList.add("نساء و توليد");
                osNameList.add("قلب و أوعية دموية");
                osNameList.add("اورام");
                osNameList.add("تجميل");
                osNameList.add("جهاز هضمي و مناظير");
                osNameList.add("ذكورة وعقم");
                osNameList.add("روماتيزم");
                osNameList.add("سكر وغدد صماء");
                osNameList.add("صدر و جهاز تنفس");
                osNameList.add("علاج طبيعي");
                osNameList.add("سمنة ومناظير");
                osNameList.add("حقن مجهري");
                osNameList.add("نطق وتخاطب");
                osNameList.add("نفسي");
                osNameList.add("مسالك بولية");
                //
                osImages.add(R.drawable.asnan);
                osImages.add(R.drawable.ayoon);
                osImages.add(R.drawable.gldya);
                osImages.add(R.drawable.batnaa);
                osImages.add(R.drawable.hdeswlada);
                osImages.add(R.drawable.mokhwaaasab);
                osImages.add(R.drawable.anfwozonwhangra);
                osImages.add(R.drawable.ezzam);
                osImages.add(R.drawable.nsawtwleed);
                osImages.add(R.drawable.qalbyy);
                osImages.add(R.drawable.awram);
                osImages.add(R.drawable.tgmel);
                osImages.add(R.drawable.ghazhdmy);
                osImages.add(R.drawable.zkora);
                osImages.add(R.drawable.romatizm);
                osImages.add(R.drawable.sokar);
                osImages.add(R.drawable.sedry);
                osImages.add(R.drawable.elag_tabeii);
                osImages.add(R.drawable.smana);
                osImages.add(R.drawable.tghseb);
                osImages.add(R.drawable.enotk);
                osImages.add(R.drawable.nfsyyyy);
                osImages.add(R.drawable.msalek);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else if (newString.equals("معامل")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("مراكز اشعة");
                osNameList.add("معامل تحليل");
                //
                osImages.add(R.drawable.ashaa);
                osImages.add(R.drawable.thalel);
                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            else if (newString.equals("موضة و ملابس")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                //osNameList.add("بيوتي سنتر حريمي");
                //osNameList.add("كوافير رجالي");
                osNameList.add("ملابس حريمي");
                osNameList.add("ملابس رجالي");
                osNameList.add("لانجيري");
                osNameList.add("اكسسوارات و هدايا");
                osNameList.add("مجوهرات");
                osNameList.add("نظارات و عدسات");
                osNameList.add("ملابس رياضية");
                //osNameList.add("تصوير");
                //osNameList.add("مساج وسبا");
                osNameList.add("فراشة وحفلات");
                //osNameList.add("زهور");
                osNameList.add("مستحضرات تجميل");
                osNameList.add("خياطه و تفصيل");
                //osNameList.add("دعاية و اعلان");
                osNameList.add("شنط و احذية");

                //
               // osImages.add(R.drawable.byoty_frr);
                // osImages.add(R.drawable.kwaferrg_fr);
                osImages.add(R.drawable.mlabshremy_frr);
                osImages.add(R.drawable.mlabsrgaly_frr);
                osImages.add(R.drawable.langry_fr);
                osImages.add(R.drawable.hdaya_frr);
                osImages.add(R.drawable.dahab_frr);
                osImages.add(R.drawable.bsryat_fr);
                osImages.add(R.drawable.mlabsryady_fr);
                //osImages.add(R.drawable.tswer_fr);
                //osImages.add(R.drawable.msag_frr);
                osImages.add(R.drawable.frasha_fr);
               // osImages.add(R.drawable.wardy);
                osImages.add(R.drawable.mosthdrat_fr);
                osImages.add(R.drawable.khyat_frr);
                //osImages.add(R.drawable.daaya); //
                osImages.add(R.drawable.shonat);

                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            // 8888888888 //
            else if (newString.equals("اجهزة الكترونية")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("تكيفات");
                osNameList.add("اجهزة منزلية");
                osNameList.add("راديو شاك");
                osNameList.add("كمبيوتر");
                //
                osImages.add(R.drawable.tkyfat_frr);
                osImages.add(R.drawable.aghzamnzlya_frr);
                osImages.add(R.drawable.radioshakgded);
                osImages.add(R.drawable.computer_sy_frr);
                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            else if (newString.equals("سيارات")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("مراكز صيانة");
                osNameList.add("مغسلة");
                osNameList.add("معارض سيارات");
                osNameList.add("كماليات سيارات");
                osNameList.add("زجاج سيارات");
                osNameList.add("ايجار سيارات");
                osNameList.add("قطع غيار");
                osNameList.add("اطارات و بطاريات");
                osNameList.add("نقل اثاث");
                osNameList.add("ونش انقاذ");
                //
                osImages.add(R.drawable.syanacars_frrr);
                osImages.add(R.drawable.mghsla_fr);
                osImages.add(R.drawable.cars_frr);
                osImages.add(R.drawable.kmalyat_frr);
                osImages.add(R.drawable.zogag_fr);
                osImages.add(R.drawable.rent_car);
                osImages.add(R.drawable.ketaaa);
                osImages.add(R.drawable.etarat);
                osImages.add(R.drawable.nkl_afsh);
                osImages.add(R.drawable.wensh);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else if (newString.equals("تعليمي")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("مدارس");
                osNameList.add("حضانات");
                osNameList.add("سنتر تعليمي");
                osNameList.add("دروس خصوصية");

                //
                osImages.add(R.drawable.school_fr);
                osImages.add(R.drawable.hdana_frr);
                osImages.add(R.drawable.center_frr);
                osImages.add(R.drawable.dros_khsosya);
                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            else if (newString.equals("حفلات ومناسبات")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("بيوتي سنتر حريمي");
                osNameList.add("كوافير رجالي");
                osNameList.add("تصوير");
                osNameList.add("مساج وسبا");
                osNameList.add("زهور");
                osNameList.add("دعاية و اعلان");
                osNameList.add("تنظيم حفلات");
                //
                osImages.add(R.drawable.byoty_frr);
                osImages.add(R.drawable.kwaferrg_fr);
                osImages.add(R.drawable.tswer_fr);
                osImages.add(R.drawable.msag_frr);
                osImages.add(R.drawable.wardy);
                osImages.add(R.drawable.daaya); //
                osImages.add(R.drawable.tanzeem);

                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else if (newString.equals("جيم و رياضة")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("Gym");
                osNameList.add("انشطة اخري");
                //
                osImages.add(R.drawable.gymm);
                osImages.add(R.drawable.anshta_ryady);

                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            // 8888888 //
            else if (newString.equals("ماكينات الصرافة")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("فوري");
                osNameList.add("امان");
                osNameList.add("البنك الاهلي");
                osNameList.add("بنك مصر");
                osNameList.add("بنك CIB");
                osNameList.add("بنك QNB");
                osNameList.add("البنك العربي الافريقي");
                osNameList.add("بنك فيصل الاسلامي");
                osNameList.add("بنك الاسكندرية");
                //
                osImages.add(R.drawable.fawry_frr);
                osImages.add(R.drawable.aman_frr);
                osImages.add(R.drawable.ahly_frr);
                osImages.add(R.drawable.masr_frr);
                osImages.add(R.drawable.cib_frr);
                osImages.add(R.drawable.qnb_frr);
                osImages.add(R.drawable.arbyafreky_fr);
                osImages.add(R.drawable.fesal);
                osImages.add(R.drawable.alexbank);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            // 88888888 //
            else if (newString.equals("عقارات")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("شركات عقارية");
                osNameList.add("عقارات افراد");
                osNameList.add("تشطيبات وديكور");
                //
                osImages.add(R.drawable.akarya);
                osImages.add(R.drawable.afrad_akarat_ch);
                osImages.add(R.drawable.decor);
                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            else if (newString.equals("خدمات حكومية")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("مقر الجمعية");
                osNameList.add("شهر العقاري");
                osNameList.add("شركة المياه");
                osNameList.add("ديني");
                osNameList.add("شركة الغاز");// 4
                osNameList.add("سنترال الرماية");// 5
                osNameList.add("مكتب البريد");//
                osNameList.add("مطافي");//
                osNameList.add("اسعاف");//
                osNameList.add("نقطة شرطة");// 9
                osNameList.add("الوحدة الصحية");// 10
                osNameList.add("طوارئ كهرباء");// 11

                osNameList.add("حي الهرم");//
                osNameList.add("شركة الكهرباء");// 13
                osNameList.add("طوارئ المياه");//
                osNameList.add("شركة تي داتا");//15
                osNameList.add("مأذون");//
                osNameList.add("مرور");//
                osNameList.add("نادي حدائق الاهرام");//17
                //
                osImages.add(R.drawable.mkr_fr);
                osImages.add(R.drawable.shahr_fr);
                osImages.add(R.drawable.myah_fr);
                osImages.add(R.drawable.deny); //
                osImages.add(R.drawable.ghaz_fr);
                osImages.add(R.drawable.sntral_fr);
                osImages.add(R.drawable.mktbbared_fr);
                osImages.add(R.drawable.mtafy_fr);
                osImages.add(R.drawable.esaaf_fr);
                osImages.add(R.drawable.shorta_fr);
                osImages.add(R.drawable.whda_fr);
                osImages.add(R.drawable.khraba_fr);

                osImages.add(R.drawable.hayharam);
                osImages.add(R.drawable.khraba_fr);
                osImages.add(R.drawable.myah_fr);
                osImages.add(R.drawable.te_data_frr);
                osImages.add(R.drawable.maazoom);
                osImages.add(R.drawable.mror);
                osImages.add(R.drawable.nady_fr);

                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else if (newString.equals("صيانة منزلية")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("صيانة الاجهزة");
                osNameList.add("سباكة");
                osNameList.add("الموتال");
                osNameList.add("حدادة");
                osNameList.add("بويات");
                osNameList.add("نقل عفش");
                osNameList.add("رخام و جرانيت");
                osNameList.add("توريدات كهرباء");

                //
                osImages.add(R.drawable.syana_pr);
                osImages.add(R.drawable.sbaka_fr);
                osImages.add(R.drawable.almotal_fr);
                osImages.add(R.drawable.hdada);
                osImages.add(R.drawable.dhanat_fr);
                osImages.add(R.drawable.nkl_fr);
                osImages.add(R.drawable.rokham);
                osImages.add(R.drawable.twredat);

                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            // 888 ///
            else if (newString.equals("حيوانات اليفة")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("كل الحيوانات");
                osNameList.add("مستشفي بيطري");
                //
                osImages.add(R.drawable.dog);
                osImages.add(R.drawable.dog);
                //
                CustomAdabterFinalWidth customAdabter_f =  new CustomAdabterFinalWidth(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setNumColumns(1); ;
                gridview.setAdapter(customAdabter_f);

            }
            else if (newString.equals("اثاث منزلي")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("اثاث");
                osNameList.add("نجف و تحف");
                osNameList.add("مراتب و مفروشات");
                osNameList.add("ستائر  و اقمشة");
                osNameList.add("معارض سجاد");
                osNameList.add("ابواب مصفحة");
                osNameList.add("زجاج و مرايا");
                //
                osImages.add(R.drawable.asas_fr);
                osImages.add(R.drawable.ngaf_fr);
                osImages.add(R.drawable.mratb_fr);
                osImages.add(R.drawable.stayr_fr);
                osImages.add(R.drawable.sgad_fr);
                osImages.add(R.drawable.abwab);
                osImages.add(R.drawable.zogag);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else if (newString.equals("شركات الاتصالات")) {
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add("Vodafone");
                osNameList.add("Etisalat");
                osNameList.add("WE");
                osNameList.add("Orange");
                //
                osImages.add(R.drawable.vodafone_frr);
                osImages.add(R.drawable.etistalat_frr);
                osImages.add(R.drawable.we_frr);
                osImages.add(R.drawable.orange_frr);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);

            }
            else{
                //
                osNameList = new ArrayList<>() ;
                osImages = new ArrayList<>() ;
                //
                osNameList.add(newString);
                osNameList.add(newString);
                osNameList.add(newString);
                osNameList.add(newString);
                osNameList.add(newString);
                //
                osImages.add(R.drawable.portfolio);
                osImages.add(R.drawable.portfolio);
                osImages.add(R.drawable.portfolio);
                osImages.add(R.drawable.portfolio);
                osImages.add(R.drawable.portfolio);
                osImages.add(R.drawable.portfolio);
                //
                customAdabter =  new CustomAdabterFinal(this, osNameList, osImages , newStringTwo , newString) ;
                gridview.setAdapter(customAdabter);
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
        finish();
    }




    @Override
    protected void onResume() {
        super.onResume();
        swipeTimer = new Timer();
    }



    private void backky () {
        ImageView back  = (ImageView) findViewById(R.id.backyy) ;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , MainActivity.class));
            }
        });

    }


    private void init(String s) {


        if (imagesView.size() == 0) {
            imagesView.add("gif");
            kind.add("gif") ;
        }


        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new ViewPagerAbaterDownload(MainSubActivity.this, imagesView , s , kind ));
        mPager.setPageTransformer(true, new RotateUpTransformer());


        //CirclePageIndicator indicator = (CirclePageIndicator)
        //findViewById(R.id.indicator);

        // indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        // indicator.setRadius(5 * density);

        NUM_PAGES =imagesView.size();
        //Toast.makeText(this, String.valueOf(NUM_PAGES), Toast.LENGTH_SHORT).show();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        setTimer(mPager , 4);



        // Pager listener over indicator
       /* indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        }); */

    }
    public void removeOpj (int s) {

        final String[] list = new String[]{"مسح الاعلان", "الغاء"};
        new AlertDialog.Builder(MainSubActivity.this)
                .setTitle("Language")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainSubActivity.this, list[which], Toast.LENGTH_SHORT).show();
                        if (list[which].equals("مسح الاعلان")) {
                            mDatabase.child(imagesIds.get(s)).setValue(null);

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }else if (list[which].equals("الغاء")) {

                        }
                        else {

                        }
                    }
                })
                .show();

    }

    private void getData(String data_op) {

        imagesView = new ArrayList<>() ;
        kind = new ArrayList<>() ;


        mDatabase = FirebaseDatabase.getInstance().getReference().child("sponser").child(newString).child("elements").child("main");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    if (childSnapShot.getKey().equals("name")) {


                        //imagesView.add("logo");
                        if (dataSnapshot.hasChild("profile_img")) {
                            imagesView.add(dataSnapshot.child("profile_img").getValue().toString()); //
                            imagesIds.add(dataSnapshot.getKey().toString());
                            kind.add("pic");
                            test_modules++;
                        }
                        else {
                            imagesView.add("waiting") ;
                            imagesIds.add("no");
                            kind.add("pic");
                            test_modules++;
                        }

                       /* if (imagesView.size() >= 3 && imagesView.size() % 3 == 0 ) {
                            imagesView.add("gif");
                            images_ids.add("no");
                            kind.add("gif");
                        }*/

                        // final new
                        if (test_modules >= 3 && test_modules % 3 == 0 ) {
                            imagesView.add("gif");
                            imagesIds.add("no");
                            kind.add("gif");
                        }

                        //kind.add("adv");


                    }

                }
                Collections.reverse(imagesView);
                Collections.reverse(imagesIds);
                Collections.reverse(kind);
                init(data_op);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;
    }

    public void setTimer(final ViewPager myPager, int time){



        final Runnable Update = new Runnable() {

            int currentPage = 0 ;
            public void run() {
                if (currentPage == NUM_PAGES ) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 250, time*1000);
    }


}



