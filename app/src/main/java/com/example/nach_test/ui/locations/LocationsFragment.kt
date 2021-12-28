package com.example.nach_test.ui.locations

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.domain.locations.model.Location
import com.example.domain.locations.model.LocationFB
import com.example.nach_test.R
import com.example.nach_test.databinding.FragmentLocationsBinding
import com.example.nach_test.presenation.locations.LocationsContract
import com.example.nach_test.presenation.locations.LocationsPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class LocationsFragment : Fragment(), LocationsContract.View, HasSupportFragmentInjector,
    OnMapReadyCallback {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    internal
    lateinit var locationsPresenter: LocationsContract.Presenter

    private lateinit var bindig: FragmentLocationsBinding

    val db = FirebaseFirestore.getInstance()

    val TPC: LatLng = LatLng(21.5126934,-104.9032173)
    val ZOOM_LEVEL = 13f
    private var googleMap: GoogleMap? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        bindig = DataBindingUtil.inflate(inflater, R.layout.fragment_locations, container, false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindig.map.onCreate(savedInstanceState)
        bindig.map.onResume()
        bindig.map.getMapAsync(this)
    }

    override fun setPresenter(presenter: LocationsPresenter) {
        this.locationsPresenter = presenter
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =  fragmentInjector

    override fun onGetLocations(locations: List<Location>) {
        db.collection("locations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result.documents) {
                    val location = document.toObject(LocationFB::class.java)
                    Log.e("bd", "${document.id} => ${location?.lat}")
                    val marker = LatLng(location?.lat ?: 0.0, location?.lon ?: 0.0)
                    this.googleMap?.addMarker(
                        MarkerOptions().position(marker).title(location?.date)
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.e("bd", "Error getting documents: ", exception)
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        with(this.googleMap){
            this?.moveCamera(CameraUpdateFactory.newLatLngZoom(TPC, ZOOM_LEVEL))
        }
        this.locationsPresenter.getLocations()
    }
}