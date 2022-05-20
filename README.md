# FunMobSDK (InHouse Ads)

[![](https://jitpack.io/v/Funsol-DevOps/FunMob.svg)](https://jitpack.io/#Funsol-DevOps/FunMob)

FunMob is an "In-House Ads Management" Platform for improved monetization of Funsol's in-house apps. 

## Getting Started

### Step 1

Add maven repository in project level build.gradle or in latest project setting.gradle file
```
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
```  


### Step 2

Add FunMobSDK dependencies in App level build.gradle.
```
    dependencies {
           implementation 'com.github.Funsol-DevOps:FunMob:x.x'
    }
```  


### Step 3

Finally intialize the SDK in onCreate() funtion of your "MainActivity"

```
    val funAds = FunAds(this@MainActivity, "auth_token_here")
```

##### Now Show Ads as per your needs
#### If you want to preload the ads, just call
```
    funAds.loadAds()
```

#### Listen to Ad Load or Fail callbacks by implementing this interface.
```
  class MainActivity : AppCompatActivity(), FunAdsCallbacks {
    ...
  }
```

### Interstitial Ads
#### Show Interstitial Ad by calling this function.
```
  funAds.showInterstitial()
```
#### Listen to Interstitial Ad callbacks by implementing this interface.
```
  class MainActivity : AppCompatActivity(), FunInterstitialCallbacks {
    ...
  }
```

### Native / Native Banner Ads
#### Show Native or Native Banner Ads by calling this function.
```
  funAds.requestNativeAd()
```
##### Note: funAds.requestNativeAds will send AdAppData object in callback function.
##### Note: you will get complete code to show native ads in Sample App.


## License

MIT License

Copyright (c) [2022] [Osama Mumtaz](https://osamamumtaz.com/)


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

