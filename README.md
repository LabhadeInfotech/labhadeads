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
                   implementation 'com.github.LabhadeInfotech:labhadeads:2.0.0'
                  }       

 ============= Themes ==============
 
       colorPrimary -----> progress color
       tabRippleColor ------> button color
       
       
============== Activity Implementation ================== 

        public class MainActivity extends AppCompatActivity {
            RelativeLayout rlBanner,rlNative;
            View tvSpace;
            Button next;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                LabhadeAds.InitializeAds(this); ---> APPLICATION class

                rlBanner = findViewById(com.labhade.adsdk.R.id.rlBanner);
                rlNative = findViewById(com.labhade.adsdk.R.id.rlNative);
                tvSpace = findViewById(com.labhade.adsdk.R.id.tvSpace);
                next = findViewById(R.id.next);

                LabhadeAds.setDefault(); ---> SPLASH ONLY
                LabhadeAds.setTestMode(this); ---> TEST MODE
                LabhadeAds.showBanner(this,rlBanner);
                LabhadeAds.showNative(this,rlNative,tvSpace, LabhadeAds.AdTemplate.NATIVE_60);


                next.setOnClickListener(v -> LabhadeAds.showInterstitial(this, isFail -> {}));
            }
        }
        

