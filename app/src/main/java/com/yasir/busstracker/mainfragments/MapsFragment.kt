package com.yasir.busstracker.mainfragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.yasir.busstracker.R
import com.yasir.busstracker.databinding.FragmentMapsBinding
import java.util.Objects

class MapsFragment : Fragment(), OnMapReadyCallback {
    private   var _binding: FragmentMapsBinding?=null
    private val binding get() = _binding!!
    private var mGoogleMap:GoogleMap ?= null
    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private val callback = OnMapReadyCallback { googleMap ->

        val iust = LatLng(33.921660, 75.012202)
        googleMap.addMarker(MarkerOptions().position(iust).title("IUST Awantipora"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(iust))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        autocompleteSuggestions()
        val mapOptionMenu = binding.mapOptionMenu
        val popupMenu = PopupMenu(requireContext(),mapOptionMenu)
        popupMenu.menuInflater.inflate(R.menu.map_options,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItems ->
            changeMap(menuItems.itemId)
            true
        }
        binding.mapOptionMenu.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun autocompleteSuggestions() {
        Places.initialize(requireContext(),getString(R.string.API_KEY))
        autocompleteFragment =childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,Place.Field.ADDRESS,Place.Field.LAT_LNG))
        autocompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onError(p0: Status) {
                Log.d("Yasir", "onError: $p0")
                Toast.makeText(requireContext(), "Some Error In Search ", Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceSelected(place: Place) {
                // val add = place.address
                //val id = place.id
                val latLng = place.latLng!!
                zoomOnMap(latLng)
            }
        })

    }
    //to zoom at any place when search fun
   private fun zoomOnMap(latLng: LatLng){
       val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(latLng,12f)  //12F->Amount of zoom level
        mGoogleMap?.animateCamera(newLatLngZoom)
   }

    private fun changeMap(itemId: Any) {
        when(itemId){
            R.id.normal_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.hybrid_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.satellite_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain_map ->  mGoogleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }

}