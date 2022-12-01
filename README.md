# labhadeads

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

- Add it in your root build.gradle at the end of repositories:

        allprojects {
            repositories {
              maven { url 'https://jitpack.io' }
            }
          }
          
Step 2. Add the dependency

        dependencies {
                  implementation 'com.github.LabhadeInfotech:labhadeads:-SNAPSHOT'
          }       
          
          
 ============= Themes ==============
 
       colorPrimary -----> progress color
       colorAccent ------> ads space and button color
       
       
============== Activity Implementation ================== 

        public class MainActivity extends AppCompatActivity {
            RelativeLayout rlBanner,rlNative;
            View tvSpace;
            Button next;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                LabhadeAds.InitializeAds(this);

                rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
                rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
                tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
                next = findViewById(R.id.next);

                LabhadeAds.setDefault();
                LabhadeAds.setTestMode(this);
                LabhadeAds.showBanner(this,rlBanner);
                LabhadeAds.showNative(this,rlNative,tvSpace,false);

                next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {}));
            }
        }
        
=============  xml implementation ================

          - Native
            <include layout="@layout/include_native_view" />

          - Banner 
            <include layout="@layout/include_banner_view" />
    

