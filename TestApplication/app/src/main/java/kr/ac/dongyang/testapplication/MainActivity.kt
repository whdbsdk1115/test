package kr.ac.dongyang.testapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.location.Location
import android.location.LocationManager
import android.security.identity.AccessControlProfileId
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.internal.service.Common.API
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.URI.create
import java.util.*

// '현재 위치 지도에 보여주기' 관련 코드: https://navermaps.github.io/android-map-sdk/guide-ko/4-2.html
// '네이버지도API는 좌표제공 안해요ㅜ': https://developers.naver.com/forum/posts/9593

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    val TAG: String = "로그그그"
    private var naverMap: NaverMap? = null
    // FusedLocationSource: 지자기, 가속도 센서를 활용해 최적의 위치를 반환하는 구현체
    private var locationSource: FusedLocationSource? = null
//    var IstLatLng: List<LatLng> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 네이버API 현재 위치 찍기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
        if (mapFragment == null) {
            fm.beginTransaction().add(R.id.map, mapFragment!!).commit()
        }
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        Log.d(TAG, "" + locationSource)

        button.setOnClickListener{
            val intent = Intent(this, RetrofitActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource // 현재위치 표시
        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource!!.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource!!.isActivated) {
                Log.d(TAG,"권한 허용 거부")
                Toast.makeText(this, "설정에서 권한을 허용해 주세요.", Toast.LENGTH_SHORT).show()
                naverMap!!.locationTrackingMode = LocationTrackingMode.None // 사용자의 위치를 추적하지 않음
                return
            } else {
                Log.d(TAG,"권한 허용")
                naverMap!!.locationTrackingMode = LocationTrackingMode.Follow // 사용자의 위치를 추적.
                // NoFollow(추적은 하는데 지도는 움직이지 않음. API나 제스처를 사용해 임의로 카메라를 움직일 경우 모드가 NoFollow로 바뀝니다.)
                // Face(추적됨. 방향따라 움직임. API나 제스처를 사용해 임의로 카메라를 움직일 경우 모드가 NoFollow로 바뀝니다.)

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val PERMISSIONS = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }




}